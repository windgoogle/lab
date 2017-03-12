package com.woo.base.concurrency;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * Created by huangfeng on 2017/3/8.
 */
public class AtomicIntegerFieldUpdaterDemo {

    public static class Candidate {
        int id;
        volatile  int score;   //不能是私有或保护变量，换句话说是必须对AtomicIntegerFieldUpdater可见
    }

    public final static AtomicIntegerFieldUpdater scoreUpdator=AtomicIntegerFieldUpdater.newUpdater(Candidate.class,"score");

    public static AtomicInteger allScore=new AtomicInteger(0);


    public static void main(String[] args) throws InterruptedException {

        final Candidate stu=new Candidate();
        int threads=10000;
        Thread [] ts=new Thread[threads];

        for(int i=0;i<threads;i++) {
            ts[i]=new Thread() {
               @Override
                public void run () {
                   if(Math.random()>0.4){
                       scoreUpdator.getAndIncrement(stu);
                       allScore.incrementAndGet();
                   }
               }
            };

            ts[i].start();
        }

        for(int k=0;k<threads;k++) {
            ts[k].join();
        }

        System.out.println("score="+stu.score);
        System.out.println("allScore="+allScore);
    }


}
