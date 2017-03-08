package com.woo.base.concurrency;

/**
 * Created by huangfeng on 2017/2/27.
 */
public class BadLockOnInteger implements Runnable {
    public static Integer i=0;
    static BadLockOnInteger instance=new BadLockOnInteger();

    @Override
    public void run() {
       for(int j=0;j<10000000;j++) {
           synchronized (i) {    //此处时错误的，Ingerger 在++运算后产生新的对象
               i++;
           }
       }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(instance);
        Thread t2=new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
