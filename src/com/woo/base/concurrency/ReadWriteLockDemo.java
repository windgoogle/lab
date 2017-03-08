package com.woo.base.concurrency;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by huangfeng on 2017/2/27.
 */
public class ReadWriteLockDemo {

    private static Lock lock=new ReentrantLock();
    private static ReadWriteLock readWriteLock=new ReentrantReadWriteLock();
    private static Lock readLock=readWriteLock.readLock();
    private static Lock writeLock=readWriteLock.writeLock();

    private int value;

    public Object  read(Lock lock) {
        try{
            lock.lock();
            Thread.sleep(1000);
            return value;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    public void  write(Lock lock,int index) {
        try{
            lock.lock();
            Thread.sleep(1000);
            value=index;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) {
        final ReadWriteLockDemo demo=new ReadWriteLockDemo();
        Runnable read=new Runnable() {
            @Override
            public void run() {
               // demo.read(readLock);
                demo.read(lock);
            }
        };

        Runnable write=new Runnable() {
            @Override
            public void run() {
                //demo.write(writeLock,new Random().nextInt());
                demo.write(lock,new Random().nextInt());
            }
        };

        for(int i=0;i<20;i++) {
            new Thread(read).start();
        }

        for(int i=18;i<20;i++) {
            new Thread(write).start();
        }
    }
}
