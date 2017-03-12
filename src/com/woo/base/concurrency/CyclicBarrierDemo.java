package com.woo.base.concurrency;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by hzy on 2017/3/12.
 */
public class CyclicBarrierDemo {

    public static class Soldier implements Runnable {
         private String soldier;
         private final CyclicBarrier cyclic;


        public Soldier (CyclicBarrier cyclic,String soldier) {
            this.cyclic=cyclic;
            this.soldier=soldier;
        }

        @Override
        public void run() {
            try{
                cyclic.await();
                doWork();
                cyclic.await();
            }catch (InterruptedException e){
                e.printStackTrace();
            }catch (BrokenBarrierException be){
                be.printStackTrace();
            }
        }

        private void doWork()  {
            try {
                Thread.sleep(Math.abs(new Random().nextInt()%10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(soldier+":任务完成");
        }
    }

    public static class BarrierRun  implements Runnable {
       boolean flag;
        int N;

        public  BarrierRun(boolean flag,int n) {
            this.flag=flag;
            this.N=n;
        }
        @Override
        public void run() {
           if(flag)
               System.out.println("司令：[士兵"+N+"个，任务完成！]");
            else {
               System.out.println("司令：[士兵"+N+"个，集合完毕！]");
               flag=true;
           }
        }
    }

    public static void main(String[] args) {
        final int N=10;
        Thread [] allSoildiers=new Thread[N];
        boolean flag=false;
        CyclicBarrier cyclic=new CyclicBarrier(N,new BarrierRun(flag,N) );
        //设置屏障点，主要是为了执行这个方法
        System.out.println("集合队伍！");
        for(int i=0;i<N;i++) {
            System.out.println("士兵 "+i+"报道！");
            allSoildiers[i]=new Thread(new Soldier(cyclic,"士兵"+i));
            allSoildiers[i].start();
        }
    }
}
