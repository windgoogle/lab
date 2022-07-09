package com.woo.base.thread;

import java.util.concurrent.TimeUnit;

public class BlockedThreadInterruptExample extends  Thread{
    @Override
    public void run(){

        while(!Thread.currentThread().isInterrupted()){
         try{
             TimeUnit.SECONDS.sleep(500);
         }catch (InterruptedException e){
             e.printStackTrace();
         }
        }
        System.out.println("�̱߳��ж�");
    }

    public static void main(String[] args) throws InterruptedException{
        BlockedThreadInterruptExample blocked=new BlockedThreadInterruptExample();
        blocked.start();
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("before:InterruptExample �ж�״̬��"+blocked.isInterrupted());
        blocked.interrupt();
        System.out.println("after:InterruptExample �ж�״̬��"+blocked.isInterrupted());   //�������ʱΪtrue����ʱΪfalse why?

    }

}
