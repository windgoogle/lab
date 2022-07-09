package com.woo.base.thread;

public class ThreadStopExample extends  Thread{
    @Override
    public void run (){
        try{
            for(int i=0;i<100000;i++){
                System.out.println("Running .."+i);
            }
            System.out.println("the code that it will be executed.");
        }catch (Throwable e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException{
        Thread t1=new ThreadStopExample();
        t1.start();
        Thread.sleep(100);
        try {
            t1.stop();
        }catch (ThreadDeath threadDeath){
            threadDeath.printStackTrace();
        }

        System.out.println("thread t1 stopped");
    }
}
