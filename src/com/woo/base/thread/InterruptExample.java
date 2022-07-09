package com.woo.base.thread;

import java.util.concurrent.TimeUnit;

public class InterruptExample extends  Thread{
    @Override
    public void run(){
        int i=0;
        while(!Thread.currentThread().isInterrupted()){
            i++;
        }
        System.out.println("线程已中断，i="+i);
    }

    public static void main(String[] args) throws InterruptedException{
        InterruptExample interruptExample=new InterruptExample();
        interruptExample.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("before:InterruptExample 中断状态："+interruptExample.isInterrupted());
        interruptExample.interrupt();
        System.out.println("after:InterruptExample 中断状态："+interruptExample.isInterrupted());

    }
}
