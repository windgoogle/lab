package com.woo.base.thread;

import java.util.concurrent.TimeUnit;

public class InterruptExample extends  Thread{
    @Override
    public void run(){
        int i=0;
        while(!Thread.currentThread().isInterrupted()){
            i++;
        }
        System.out.println("�߳����жϣ�i="+i);
    }

    public static void main(String[] args) throws InterruptedException{
        InterruptExample interruptExample=new InterruptExample();
        interruptExample.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("before:InterruptExample �ж�״̬��"+interruptExample.isInterrupted());
        interruptExample.interrupt();
        System.out.println("after:InterruptExample �ж�״̬��"+interruptExample.isInterrupted());

    }
}
