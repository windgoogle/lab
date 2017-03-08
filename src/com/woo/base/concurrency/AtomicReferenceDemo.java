package com.woo.base.concurrency;

import java.util.concurrent.atomic.AtomicReference;


/**
 * Created by huangfeng on 2017/3/8.
 * 该例子展示了原子引用的缺陷，如果一个线程修改了值，又将其改了回去，另外一个线程不会发现这个变化
 * 对于这个变化敏感的场景，则是不合适的
 */
public class AtomicReferenceDemo {
    static AtomicReference<Integer> money=new AtomicReference<Integer>(19);

    public static void main(String[] args) {

        //模拟多个线程同时更新后台数据库，为用户充值
        for (int i=0;i<3;i++) {

            //充值线程
            new Thread(){
                @Override
                public void run(){
                    while(true) {
                        while(true) {
                            Integer m=money.get();
                            if(m<20) {
                                if(money.compareAndSet(m,m+20)){
                                    System.out.println("余额小于20元，充值成功，余额："+money.get()+" 元");
                                    break;
                                }
                            }else{
                                //System.out.println("余额大于20元，无需充值！");
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

                        Integer m=money.get();
                        if(m>10) {
                            System.out.println("大于10元！");
                            if(money.compareAndSet(m,m-10)){
                                System.out.println("成功消费10元，余额："+money.get()+" 元");
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
