package com.woo.base.concurrency;

import java.util.concurrent.CountDownLatch;


/**
 * 这个例子说明 i++ 是线程不安全的
 * i 是全局变量，线程不安全
 * i 是局部变量，线程安全的
 * 解决办法：1. 加锁控制 2. 使用原子变量ActomicInteger
 */

public class ThreadSafeDemo {

    private int i;

    public static void main(String[] args) throws InterruptedException {

        for (int j = 0; j < 10; j++) {
            final ThreadSafeDemo main = new ThreadSafeDemo();
            final CountDownLatch count = new CountDownLatch(10000);
            for (int i = 0; i < 100; i++) {
                new Thread(new Runnable() {
                    public void run() {

                        for (int j = 0; j < 100; j++) {
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {

                                e.printStackTrace();
                            }
                            main.incr();
                            count.countDown();
                        }
                    }
                }).start();

            }
            //主线程等待子线程结束
            count.await();
            System.out.println(main.getI());
        }


    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public  void incr() {
        i++;
    }

}



