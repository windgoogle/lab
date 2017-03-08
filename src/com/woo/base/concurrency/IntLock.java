package com.woo.base.concurrency;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by huangfeng on 2017/2/27.
 * 线程死锁的例子，由于使用了重入锁，可以响应中断，主线程中断了一个线程后，死锁解开了
 */
public class IntLock implements Runnable{
    public static ReentrantLock lock1=new ReentrantLock();
    public static ReentrantLock lock2=new ReentrantLock();

    int lock;

    public IntLock(int lock) {
        this.lock=lock;
    }

    @Override
    public void run() {
       try{
           if(lock==1) {
               lock1.lockInterruptibly();    //可响应中断
               //lock1.lock();
               try{
                   Thread.sleep(500);
               }catch (InterruptedException ex){

               }
              lock2.lockInterruptibly();
               //lock2.lock();
           }else {
               lock2.lockInterruptibly();
              // lock2.lock();
               try{
                   Thread.sleep(500);
               }catch (InterruptedException ex){

               }
               lock1.lockInterruptibly();
               //lock1.lock();
           }
       }catch(Exception e) {
           e.printStackTrace();
       }finally {
         if(lock1.isHeldByCurrentThread())
             lock1.unlock();

          if(lock2.isHeldByCurrentThread())
              lock2.unlock();
           System.out.println("current thread "+Thread.currentThread().getId() +" exit !" );
       }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new IntLock(1));
        Thread t2=new Thread(new IntLock(2));
        t1.start();
        t2.start();
        Thread.sleep(2000);
        t2.interrupt();
    }
}
