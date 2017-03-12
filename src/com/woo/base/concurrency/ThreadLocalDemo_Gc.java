package com.woo.base.concurrency;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hzy on 2017/3/12.
 */
public class ThreadLocalDemo_Gc {
    static volatile ThreadLocal<SimpleDateFormat> t1 = new ThreadLocal<SimpleDateFormat>() {
        protected void finalize() throws Throwable {
            System.out.println(this.toString() + " is gc !");
        }
    };

    static volatile CountDownLatch cd = new CountDownLatch(10000);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10000; i++) {
            es.execute(new ParseDate(i));
        }
        cd.await();
        System.out.println("misson complete!");
        t1 = null;
        System.gc();
        System.out.println("first GC complete !");
        t1 = new ThreadLocal<SimpleDateFormat>();
        cd = new CountDownLatch(10000);
        for (int i = 0; i < 10000; i++) {
            es.execute(new ParseDate(i));
        }
        cd.await();
        Thread.sleep(1000);
        System.gc();
        System.out.println("second GC complete !");

    }

    public static class ParseDate implements Runnable {
        int i = 0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                if (t1.get() == null) {
                    t1.set(new SimpleDateFormat("") {
                        protected void finalize() throws Throwable {
                            System.out.println(this.toString() + " is gc !");
                        }
                    });
                    System.out.println(Thread.currentThread().getId() + " : create simpleDateFormat!");
                }
                Date t = t1.get().parse("2017-10-12 19:32:" + i % 60);
            } catch (Exception e) {
                //  e.printStackTrace();
            } finally {
                cd.countDown();

            }
        }
    }
}
