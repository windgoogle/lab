package com.woo.base.concurrency.spinlock;


import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


public class TryPriorityCLHLock implements Lock {
    public static int trylock;
    final int minDelay, maxDelay;
    final Random random;
    private final ThreadLocal<Qnode> mynode;
    public AtomicBoolean locked;
    int limit;
    PriorityBlockingQueue<Qnode> priorityQueue = new PriorityBlockingQueue<Qnode>();

    public TryPriorityCLHLock() {
        locked = new AtomicBoolean();
        this.mynode = new ThreadLocal<Qnode>() {
            protected Qnode initialValue() {
                return new Qnode(5);
            }
        };
        minDelay = 1;
        maxDelay = 5;
        limit = minDelay;
        random = new Random();
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        long startTime = System.nanoTime();
        long patience = TimeUnit.NANOSECONDS.convert(time, unit);
        final Qnode qnode = this.mynode.get();
        qnode.priority = Thread.currentThread().getPriority();
        priorityQueue.add(qnode);
        while (System.nanoTime() - startTime < patience) {
            if (priorityQueue.peek() != qnode || !locked.compareAndSet(false, true)) {
                continue;
            } else {
                return true;
            }
        }
        priorityQueue.remove(qnode);
        return false;
    }

    public void lock() {
        try {
            while (!tryLock(2000, TimeUnit.NANOSECONDS)) {
                int delay = random.nextInt(limit);
                limit = Math.min(maxDelay, 2 * limit);
                Thread.sleep(delay);
                trylock = trylock + 1;
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void unlock() {
        final Qnode qnode = this.mynode.get();
        priorityQueue.remove(qnode);
        locked.set(false);
    }
}
