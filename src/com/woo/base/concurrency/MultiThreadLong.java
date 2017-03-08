package com.woo.base.concurrency;

/**
 * Created by huangfeng on 2017/2/24.
 */
public class MultiThreadLong {

    public static  long t=0;   //拯救该问题的关键是使用修饰符volatile

    public static class changeT implements Runnable {
        private long t0;
        public changeT (long t0) {
          this.t0=t0;
        }

        @Override
        public void run() {

           while(true) {
               MultiThreadLong.t=t0;
               Thread.yield();
           }
        }
    }


    public static class readT implements  Runnable {

        @Override
        public void run() {
             while(true) {
                 long tmp=MultiThreadLong.t;
                 if(tmp!=111L&&tmp!=-999L&&tmp!=333L &&tmp!=-444L ){
                     System.out.println(tmp);   //这里有输出，表明多线程执行产生了预期不符的结果
                 }
                 Thread.yield();
             }
        }
    }

    public static void main(String[] args) {
         System.out.println("test start!");
         new Thread(new changeT(111L)).start();
         new Thread(new changeT(-999L)).start();
         new Thread(new changeT(333L)).start();
         new Thread(new changeT(-444L)).start();
         new Thread(new readT()).start();
    }
}
