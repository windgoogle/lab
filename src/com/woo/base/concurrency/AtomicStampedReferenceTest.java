package com.woo.base.concurrency;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * AtomicStampedReference用来解决AtomicInteger中的ABA问题，
 * 该demo企图将integer的值从0一直增长到1000，但当integer的值增长到128后，将停止增长。
 * 出现该现象有两点原因：
 * 1、使用int类型而非Integer保存当前值
 * 2、Interger对-128~127的缓存
 */

public class AtomicStampedReferenceTest {

    private static AtomicStampedReference<Integer> integer=new AtomicStampedReference<Integer>(0,0);

    public static void main(String[] args) {
        for(int i=0;i<1000;i++){
            final int timestamp=integer.getStamp();
            new Thread() {
                @Override
                public void run() {
                    while(true) {
                        //Integer current = integer.getReference(); //正确
                        int current=integer.getReference();    //错误
                        System.out.println("curent:"+current);
                        if (integer.compareAndSet(current, current + 1, timestamp, timestamp + 1)) {
                            break;
                        }
                    }
                }

            }.start();
        }
    }

}
