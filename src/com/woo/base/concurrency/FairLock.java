package com.woo.base.concurrency;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by huangfeng on 2017/2/27.
 */
public class FairLock implements Runnable {
    public static ReentrantLock fairlock=new ReentrantLock(true); // true : 公平锁

    @Override
    public void run() {
        while (true){

            try{
                fairlock.lock();
                System.out.println(Thread.currentThread().getId()+ " get lock !");
            }finally {
                fairlock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Thread t1=new Thread(new  FairLock());
        Thread t2=new Thread(new  FairLock());
        t1.start();
        t2.start();
    }
}
