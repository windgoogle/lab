package com.woo.base.concurrency;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

public class KillThread {
    public  volatile  List<Worker> workers=new Vector<Worker>();
    public  ReentrantLock lock=new ReentrantLock();

    public int num=0;

    public static void main(String[] args) throws Exception {
        KillThread main=new KillThread();

        main.test();
    }

    public void test () throws  Exception{
        Worker worker1=new Worker("ToBeKilled-1");
        Worker worker2=new Worker("ToBeKilled-2");


        workers.add(worker1);
        workers.add(worker2);

        worker1.thread.start();
        worker2.thread.start();
        System.out.println("工作线程队列大小： "+workers.size());
        Thread.sleep(1000);
        kill(worker1.thread);
        Thread.sleep(1000);
        kill(worker2.thread);

        while(worker1.thread.isAlive()||worker2.thread.isAlive()){
            System.out.println("1--thread1 is alive: "+worker1.thread.isAlive());
            System.out.println("1--thread2 is alive: "+worker2.thread.isAlive());
            System.out.println("-------"+worker1.getN());
            System.out.println("--1----workers size: "+workers.size());
            System.out.println("--1----num: "+num);
        }

        System.out.println("2--thread1 is alive: "+worker1.thread.isAlive());
        System.out.println("2--thread2 is alive: "+worker2.thread.isAlive());
        System.out.println("--2----workers size: "+workers.size());
        System.out.println("--2----num: "+num);

        // System.out.println("--2----thread is quit: "+threadIsQuit);
        int m=10;
        while (true){
            m--;
            Thread.sleep(1000);
            // System.out.println("---"+worker.getN());
        }
    }

    public static void kill(Thread thread) {
        try {
            System.out.println("进入了kill方法！");
            ThreadMXBean threadMXBean= ManagementFactory.getThreadMXBean();
            ThreadInfo[] threadInfos=threadMXBean.dumpAllThreads(true ,true);
            for(ThreadInfo threadInfo :threadInfos){
                if(thread.getName().equals(threadInfo.getThreadName())){
                    System.out.println("Thread state :"+threadInfo.getThreadState());
                    StackTraceElement [] stackTraces=threadInfo.getStackTrace();
                    for(StackTraceElement stackTraceElement : stackTraces){
                        System.out.println(stackTraceElement.toString());
                    }
                }
            }
            thread.stop();
            //thread.interrupt();
           // Thread.dumpStack();


        } catch (ThreadDeath e) {
            System.out.println("进入了catch()方法！");
            //e.printStackTrace();
        }
        System.out.println("-----end");
    }


    public  final class Worker implements Runnable {

        private String name;
        final Thread thread;

        public Worker(String name) {
            this.name=name;
            this.thread = new Thread(this);
            thread.setName(name);
            thread.setDaemon(true);

        }

        public int getN() {
            return n;
        }

        public void setN(int n) {
            this.n = n;
        }

        private int n=100;
        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {


           // Thread.setDefaultUncaughtExceptionHandler(new MyHandler());
           // Thread.currentThread().setUncaughtExceptionHandler(new MyHandler());
            //System.out.println("getUncaughtExceptionHandler: "+Thread.currentThread().getUncaughtExceptionHandler());
            try {
                while (true) {
                    //try {
                    n++;
                    if (n >= Integer.MAX_VALUE - 1)
                        n = 0;
                    // Thread.sleep(1000);
                    //} catch (InterruptedException e) {
                    //  Thread.currentThread().interrupt();
                    //e.printStackTrace();
                    //}
                }
            }catch (ThreadDeath e){
               // threadIsQuit=true;

                System.out.println("线程["+Thread.currentThread().getName()+","+Thread.currentThread().getId()+"]死了 ");
                //e.printStackTrace();
                //throw new ThreadDeath();
                num+=10;
            }finally {
              //  threadIsQuit=true;
               // lock.lock();
                System.out.println(workers.remove(this));
                //lock.unlock();
                System.out.println("工作线程大小 ：  "+workers.size()+" ,"+thread.getName()+" , n="+n);   //造成数据不一致
            }

            //try {
              //  Thread.sleep(5000);
            //} catch (InterruptedException e) {
              //  e.printStackTrace();
            //}
           // System.out.println("------thread death");
        }
    }


    static class MyHandler implements Thread.UncaughtExceptionHandler {

        /**
         * Method invoked when the given thread terminates due to the
         * given uncaught exception.
         * <p>Any exception thrown by this method will be ignored by the
         * Java Virtual Machine.
         *
         * @param t the thread
         * @param e the exception
         */
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println("------3 thread is alive : "+t.isAlive());
            e.printStackTrace();
          //  threadIsQuit=true;

        }
    }
}
