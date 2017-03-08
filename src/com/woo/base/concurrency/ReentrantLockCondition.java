package com.woo.base.concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by huangfeng on 2017/2/27.
 */
public class ReentrantLockCondition implements Runnable{
    public static ReentrantLock lock=new ReentrantLock();
    public static Condition cond=lock.newCondition();

    @Override
    public void run() {

        try {
            lock.lock();
            cond.await();      //释放锁，等待
            System.out.println("Thread is going on !");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new ReentrantLockCondition());
        t1.start();
        Thread.sleep(2000);
        lock.lock();
        cond.signal();      //获取锁后唤醒线程t1
        lock.unlock();
    }
}
