package com.woo.base.concurrency;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Created by huangfeng on 2017/3/8.
 * 带时间戳的原子引用
 * 该例子说明了充值和消费，保证充值仅进行一次
 */
public class AtomicStampedReferenceDemo {

    static AtomicStampedReference<Integer> money=new AtomicStampedReference<Integer>(19,0);

    public static void main(String[] args) {

        //模拟多个线程同时更新后台数据库，为用户充值
        for (int i=0;i<3;i++) {
            final int  timestamp=money.getStamp();
            //充值线程
            new Thread(){
                 @Override
                 public void run(){
                    while(true) {
                     while(true) {
                         Integer m=money.getReference();
                         if(m<20) {
                             if(money.compareAndSet(m,m+20,timestamp,timestamp+1)){
                                 System.out.println("余额小于20元，充值成功，余额："+money.getReference()+" 元");
                                 break;
                             }
                         }else{
                             System.out.println("余额大于20元，无需充值！");
                             break;
                         }
                     }
                    }
                 }
             }.start();


        }


        //消费线程
        new Thread(){
            @Override
            public void run(){
               for(int i=0;i<100;i++){
                    while(true) {
                        int timestamp=money.getStamp();
                        Integer m=money.getReference();
                        if(m>10) {
                            System.out.println("大于10元！");
                            if(money.compareAndSet(m,m-10,timestamp,timestamp+1)){
                                System.out.println("成功消费10元，余额："+money.getReference()+" 元");
                                break;
                            }
                        }else{
                            System.out.println("没有足够的金额！");
                            break;
                        }

                    } //end while

                   try{
                       Thread.sleep(100);
                   }catch (InterruptedException e){

                   }
                }
            }
        }.start();

    }

}
