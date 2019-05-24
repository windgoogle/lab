package com.woo.base.datastruct.timewheel.netty;


import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 基于netty 的hashedwheeltimer改造的timer
 */

public class HashedWheelTimer implements Timer {
    public static final int WORKER_STATE_INIT = 0;
    public static final int WORKER_STATE_STARTED = 1;
    public static final int WORKER_STATE_SHUTDOWN = 2;
    private static final AtomicIntegerFieldUpdater<HashedWheelTimer> WORKER_STATE_UPDATER =
            AtomicIntegerFieldUpdater.newUpdater(HashedWheelTimer.class, "workerState");
    final int mask;
    private final ExecutorService executor;
    private final Thread workerThread;
    private final Worker worker;
    private final CountDownLatch startTimeInitialized = new CountDownLatch(1);
    private final HashedWheelBucket[] wheel;
    private final Queue<HashedWheelTimeout> timeouts = new LinkedBlockingQueue<>();
    private final AtomicLong pendingTimeouts = new AtomicLong(0);
    private final Queue<HashedWheelTimeout> cancelledTimeouts = new LinkedBlockingQueue<>();
    private long tickDuration; //每tick一次的时间间隔, 每tick一次就会到达下一个槽位
    private volatile long startTime;
    private volatile int workerState = WORKER_STATE_INIT; // 0 - init, 1 - started, 2 - shut down

    public HashedWheelTimer(ExecutorService executor,
                            long tickDuration, TimeUnit unit, int wheelSize) {
        if (executor == null) {
            throw new NullPointerException("executor");
        }
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        if (tickDuration <= 0) {
            throw new IllegalArgumentException("tickDuration must be greater than 0: " + tickDuration);
        }
        if (wheelSize <= 0) {
            throw new IllegalArgumentException("wheelSize must be greater than 0: " + wheelSize);
        }

        mask = wheelSize - 1;
        if ((wheelSize & mask) != 0) {
            throw new IllegalArgumentException("wheelSize must be power of 2");
        }

        this.executor = executor;
        this.tickDuration = unit.toNanos(tickDuration);
        //tickDuration 不能大于 Long.MAX_VALUE / wheel.length, 也就是一轮的时间不能大于Long.MAX_VALUE 纳秒
        if (this.tickDuration >= Long.MAX_VALUE / wheelSize) {
            throw new IllegalArgumentException(String.format(
                    "tickDuration: %d (expected: 0 < tickDuration in nanos < %d",
                    tickDuration, Long.MAX_VALUE / wheelSize));
        }

        //创建wheel数组
        wheel = createWheel(wheelSize);

        worker = new Worker();
        workerThread = new Thread(worker, "HashedWheelTimer Worker");
    }

    private static HashedWheelBucket[] createWheel(int wheelSize) {
        HashedWheelBucket[] wheel = new HashedWheelBucket[wheelSize];
        for (int i = 0; i < wheelSize; i++) {
            wheel[i] = new HashedWheelBucket();
        }
        return wheel;
    }

    //启动Timer, 不需要显示调用, 调用newTimeout时, 会自动调用该方法
    private void start() {
        switch (WORKER_STATE_UPDATER.get(this)) {
            case WORKER_STATE_INIT:
                if (WORKER_STATE_UPDATER.compareAndSet(this, WORKER_STATE_INIT, WORKER_STATE_STARTED)) {
                    workerThread.start();
                }
                break;
            case WORKER_STATE_STARTED:
                break;
            case WORKER_STATE_SHUTDOWN:
                throw new IllegalStateException("cannot be started once stopped");
            default:
                throw new Error("Invalid WorkerState");
        }

        //等待worker启动, 并初始化startTime完成
        while (startTime == 0) {
            try {
                startTimeInitialized.await();
            } catch (InterruptedException ignore) {
            }
        }
    }

    @Override
    public Timeout newTimeout(TimerTask task, long delay, TimeUnit unit) {
        if (task == null) {
            throw new NullPointerException("task");
        }
        if (unit == null) {
            throw new NullPointerException("unit");
        }

        start();

        //任务先添加到timeouts队列中, 等待下一个tick时, 再添加到对应的wheel中去.
        long deadline = System.nanoTime() + unit.toNanos(delay) - startTime;
        HashedWheelTimeout timeout = new HashedWheelTimeout(this, task, deadline);
        timeouts.add(timeout);
        return timeout;
    }

    @Override
    public Set<Timeout> stop() {
        //源码中对Thread.currentThread() == workerThread做了判断，
        //表示 worker线程不能调用stop方法, 也就是添加的Task中不能调用stop方法
        //由于改动的逻辑是executor来执行任务，所以暂时不做这样的限制，如果想要限制的话，可以根据ThreadName来判断

        //cas修改状态为shutdown, 如果修改失败, 则当前状态只可能是WORKER_STATE_INIT和WORKER_STATE_SHUTDOWN
        if (!WORKER_STATE_UPDATER.compareAndSet(this, WORKER_STATE_STARTED, WORKER_STATE_SHUTDOWN)) {
            WORKER_STATE_UPDATER.set(this, WORKER_STATE_SHUTDOWN);//总是设置为WORKER_STATE_SHUTDOWN
            return Collections.emptySet();//状态为0和2时, 是没有遗留任务的.
        }

        //中断worker线程, worker线程中会轮询Timer状态的.
        boolean interrupted = false;
        while (workerThread.isAlive()) {
            workerThread.interrupt();
            try {
                workerThread.join(100);
            } catch (InterruptedException ignored) {
                interrupted = true;
            }
        }

        executor.shutdown();
        //恢复中断标志
        if (interrupted) {
            Thread.currentThread().interrupt();
        }

        //返回未处理的任务
        return worker.unprocessedTimeouts();
    }

    //任务的包装类, 链表结构, 负责保存deadline, 轮数, 等
    private static final class HashedWheelTimeout implements Timeout {

        private static final int ST_INIT = 0;
        private static final int ST_CANCELLED = 1;
        private static final int ST_EXPIRED = 2;
        private static final AtomicIntegerFieldUpdater<HashedWheelTimeout> STATE_UPDATER =
                AtomicIntegerFieldUpdater.newUpdater(HashedWheelTimeout.class, "state");
        private final HashedWheelTimer timer;//timer引用
        private final TimerTask task;//要执行的任务引用
        private final long deadline;//Timer启动时间 - 任务执行时间(任务加入时间+任务延迟时间)
        HashedWheelBucket bucket; //HashedWheelTimeout 所在的 wheel
        //离任务执行还要等待的轮数, 当任务加入到wheel中时计算该值, 并在Worker中, 每过一轮, 该值减一.
        long remainingRounds;
        private volatile int state = ST_INIT;

        HashedWheelTimeout(HashedWheelTimer timer, TimerTask task, long deadline) {
            this.timer = timer;
            this.task = task;
            this.deadline = deadline;
        }

        @Override
        public Timer timer() {
            return timer;
        }

        @Override
        public TimerTask task() {
            return task;
        }

        @Override
        public boolean cancel() {
            //这里只修改状态从ST_INIT到ST_CANCELLED
            if (!STATE_UPDATER.compareAndSet(this, ST_INIT, ST_CANCELLED)) {
                return false;
            }

            // If a task should be canceled we put this to another queue which will be processed on each tick.
            // So this means that we will have a GC latency of max. 1 tick duration which is good enough. This way
            // we can make again use of our MpscLinkedQueue and so minimize the locking / overhead as much as possible.

            //如果状态修改成功, 则表示第一次调用cancel方法, 会由executor线程池中的线程执行
            //之前这里将cancel的任务放在timeous队列里, 然后统一处理，
            //但是有锁的问题, 因为timeous这个队列可能被多个线程操作(HashedWheelTimer.newTimeout())
            //于是, 将cancel任务另外存一个队列, 这样, 就不需要使用锁了
            timer.cancelledTimeouts.add(this);
            return true;
        }

        void remove() {
//            HashedWheelBucket bucket = this.bucket;
//            if (bucket != null) {
//                bucket.remove(this);
//            } else {
            timer.pendingTimeouts.decrementAndGet();

        }

        public int state() {
            return state;
        }

        @Override
        public boolean isCancelled() {
            return state() == ST_CANCELLED;
        }

        @Override
        public boolean isExpired() {
            return state() == ST_EXPIRED;
        }

        public void expire() {
            if (!STATE_UPDATER.compareAndSet(this, ST_INIT, ST_EXPIRED)) {
                return;
            }

            timer.executor.execute(() -> {
                try {
                    task.run(this);
                } catch (Throwable t) {


                }
            });
        }
    }

    private static final class HashedWheelBucket {
        private List<HashedWheelTimeout> timeoutList = new ArrayList<>();

        public void addTimeout(HashedWheelTimeout timeout) {
            assert timeout.bucket == null;
            timeout.bucket = this;
            timeoutList.add(timeout);
        }

        //当tick到该wheel的时候, Worker会调用这个方法, 根据deadline来判断任务是否过期(remainingRounds是否为0),
        //任务到期就执行, 没到期, 就timeout.remainingRounds--, 因为走到这里, 表示改wheel里的任务又过了一轮了.
        public void expireTimeouts(long deadline) {
            Iterator<HashedWheelTimeout> it = timeoutList.iterator();
            while (it.hasNext()) {
                HashedWheelTimeout timeout = it.next();
                if (timeout.remainingRounds <= 0) { //任务已到执行点
                    it.remove();
                    timeout.timer.pendingTimeouts.decrementAndGet();

                    if (timeout.deadline <= deadline) {
                        timeout.expire();
                    } else {
                        // timeout被放置在了错误的slot，这种事情应该不会发生
                        throw new IllegalStateException(String.format(
                                "timeout.deadline (%d) > deadline (%d)", timeout.deadline, deadline));
                    }
                } else if (timeout.isCancelled()) {
                    it.remove();
                    timeout.timer.pendingTimeouts.decrementAndGet();
                } else {
                    timeout.remainingRounds--;
                }
            }
        }

        public void clearTimeouts(Set<Timeout> set) {
            for (; ; ) {
                HashedWheelTimeout timeout = pollTimeout();
                if (timeout == null) {
                    return;
                }
                if (timeout.isExpired() || timeout.isCancelled()) {
                    continue;
                }
                set.add(timeout);
            }
        }

        private HashedWheelTimeout pollTimeout() {
            if (timeoutList.isEmpty()) {
                return null;
            }

            HashedWheelTimeout timeout = null;
            Iterator<HashedWheelTimeout> it = timeoutList.iterator();
            if (it.hasNext()) {
                timeout = it.next();
                it.remove();

                // null out prev and next to allow for GC.
                timeout.bucket = null;
            }

            return timeout;
        }
    }

    private final class Worker implements Runnable {
        private final Set<Timeout> unprocessedTimeouts = new HashSet<>();

        private long tick;

        @Override
        public void run() {
            //初始化startTime, startTime只是一个起始时间的标记, 任务的deadline是相对这个时间点来的.
            startTime = System.nanoTime();

            //因为nanoTime返回值可能为0, 甚至负数, 所以这时赋值为1, Timer中start方法会判断该值, 直到不为0才跳出循环.
            if (startTime == 0) {
                startTime = 1;
            }

            // Notify the other threads waiting for the initialization at start().
            startTimeInitialized.countDown();

            do {
                final long deadline = waitForNextTick();
                if (deadline > 0) {
                    int idx = (int) (tick & mask);//获取Current Index

                    processCancelledTasks();//移除cancel了的task
                    transferTimeoutsToBuckets();//因为添加任务是先加入到timeouts队列中, 而这里就是将任务从队列中取出, 放到对应的bucket中

                    HashedWheelBucket bucket = wheel[idx];//当前tick对应的wheel
                    bucket.expireTimeouts(deadline);

                    tick++;
                }
            } while (WORKER_STATE_UPDATER.get(HashedWheelTimer.this) == WORKER_STATE_STARTED);

            //返回调用stop()时, 还未处理的任务.
            for (HashedWheelBucket bucket : wheel) {
                bucket.clearTimeouts(unprocessedTimeouts);
            }

            //加上还没来得及放入bucket中的任务
            for (; ; ) {
                HashedWheelTimeout timeout = timeouts.poll();
                if (timeout == null) {
                    break;
                }
                if (!timeout.isCancelled()) {
                    unprocessedTimeouts.add(timeout);
                }
            }

            //最好移除下cancel了的task
            processCancelledTasks();
        }

        private void transferTimeoutsToBuckets() {
            //一次tick, 最多放入10w任务, 防止太多了, 造成worker线程在这里停留太久.
            for (int i = 0; i < 100000; i++) {
                HashedWheelTimeout timeout = timeouts.poll();
                if (timeout == null) { //全部处理完了, 退出循环
                    break;
                }

                if (timeout.state() == HashedWheelTimeout.ST_CANCELLED) {
                    //还没加入到bucket中, 就取消了
                    continue;
                }

                //calculated 表示任务要经过多少个tick
                long calculated = timeout.deadline / tickDuration;
                //设置任务要经过的轮数
                timeout.remainingRounds = (calculated - tick) / wheel.length;

                //如果任务在timeouts队列里面放久了, 以至于已经过了执行时间,
                //这个时候就使用当前tick, 也就是放到当前bucket, 于是方法调用完后就会执行.
                final long ticks = Math.max(calculated, tick);
                int stopIndex = (int) (ticks & mask);

                HashedWheelBucket bucket = wheel[stopIndex];
                bucket.addTimeout(timeout);
            }
        }

        private void processCancelledTasks() {
            for (; ; ) {
                HashedWheelTimeout timeout = cancelledTimeouts.poll();
                if (timeout == null) {
                    break;
                }
                try {
                    timeout.remove();
                } catch (Throwable t) {
                }
            }
        }

        //sleep, 直到下次tick到来, 然后返回该次tick和启动时间之间的时长
        private long waitForNextTick() {

            //下次tick的时间点, 用于计算需要sleep的时间
            long deadline = tickDuration * (tick + 1);

            for (; ; ) {
                //计算需要sleep的时间, 之所以加9999999后再除10000000, 是因为保证为10毫秒的倍数.
                final long currentTime = System.nanoTime() - startTime;
                long sleepTimeMs = (deadline - currentTime + 999999) / 1000000;

                if (sleepTimeMs <= 0) { //小于等于0, 表示本次tick已经到了, 直接返回.
                    if (currentTime == Long.MIN_VALUE) {
                        return -Long.MAX_VALUE;
                    } else {
                        return currentTime; //返回过去的时间
                    }
                }

                try {
                    Thread.sleep(sleepTimeMs);
                } catch (InterruptedException ignored) {
                    //当调用Timer.stop时, 退出
                    if (WORKER_STATE_UPDATER.get(HashedWheelTimer.this) == WORKER_STATE_SHUTDOWN) {
                        return Long.MIN_VALUE;
                    }
                }
            }
        }

        public Set<Timeout> unprocessedTimeouts() {
            return Collections.unmodifiableSet(unprocessedTimeouts);
        }
    }
}
