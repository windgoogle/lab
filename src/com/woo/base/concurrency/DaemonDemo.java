package com.woo.base.concurrency;

/**
 * Created by huangfeng on 2017/2/27.
 */
public class DaemonDemo {
    public static class DaemonT extends Thread {
         @Override
          public void run () {
             while (true) {
                 System.out.println("I am alive !");
                 try{
                     Thread.sleep(1000);
                 }catch (InterruptedException e){
                     e.printStackTrace();
                 }
             }
          }
    }

    public static void main(String[] args) throws  InterruptedException{
        Thread t=new DaemonT();
        t.setDaemon(true);     //必须start() 之前设置
        t.start();
        Thread.sleep(2000);
    }
}
