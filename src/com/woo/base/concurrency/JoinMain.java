package com.woo.base.concurrency;

/**
 * Created by huangfeng on 2017/2/27.
 */
public class JoinMain {

    public static volatile int i=0;

    public static class AddThread extends Thread {
        @Override
        public void run () {
            System.out.println("t3:" + System.currentTimeMillis());
          for (i=0;i<1000000;i++){

          }
            System.out.println("t4:" + System.currentTimeMillis());
        }
    }


    public static void main(String[] args) throws InterruptedException {
        AddThread t = new AddThread();
        t.start();
        System.out.println("t1:" + System.currentTimeMillis());
        t.join();      //让当前线程（主线程）阻塞，join是个同步方法，必须拿到锁（该线程对象t,如果没拿到，不止阻塞这么长时间）
        System.out.println("t2:" + System.currentTimeMillis());
        System.out.println(i);
    }

}
