package com.woo.base.concurrency;

/**
 * Created by huangfeng on 2017/2/24.
 */
public class SimpleWN {
    final static Object o=new Object();

    public static class T1 extends Thread {

        public void run() {
            System.out.println(System.currentTimeMillis()+ " : T1 start !");
            synchronized (o) {
                try{
                    System.out.println(System.currentTimeMillis()+ " :T1 wait for object !");
                    o.wait();    //这个程序貌唤不醒，因为T1 后获取锁的化，T2 先获取锁，然后进行了通知，sleep 2s后，交出了锁，结果T1拿到了锁，又wait,但是没人唤醒T1
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis()+ " :T1 end !");   // 这儿走不到
            }
        }

    }


    public static class T2 extends Thread {

        public void run() {

            synchronized (o) {
                System.out.println(System.currentTimeMillis()+ " : T2 start ,notify one thread !");

                    o.notify();
                    System.out.println(System.currentTimeMillis()+ " :T2 end !");

                    try{
                        Thread.sleep(2000);   //不释放锁
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }

                o.notify();
            }
        }

    }

    public static void main(String[] args) {
          Thread t1=new T1();
          Thread t2=new T2();
          t1.start();
          t2.start();

    }
}
