package com.woo.base.concurrency;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Created by huangfeng on 2017/3/8.
 */
public class AtomicIntegerArrayDemo {

    static AtomicIntegerArray arr=new AtomicIntegerArray(10);

    public static class AddThread implements Runnable {

        @Override
        public void run() {
            for(int i=0;i<10000;i++) {
                arr.getAndIncrement(i%arr.length());
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
       Thread [] ts=new Thread[10];
        for(int k=0;k<10;k++){
            ts[k]=new Thread(new AddThread());
        }
        for(int k=0;k<10;k++){
            ts[k].start();
        }
        for(int k=0;k<10;k++){
            ts[k].join();
        }
        System.out.println(arr);
    }
}
