package com.woo.base.datastruct;

import static java.lang.System.out;


/**
 * Created by huangfeng on 2016/12/22.
 */
public class StackDemo {

    public static void main (String args[]) throws  Exception {

        String  test="test string";
        QueueStack queueStack=new QueueStack();
        long t1=System.currentTimeMillis();
        for(int i=0;i<100000;i++) {
            queueStack.push(test);
        }
        long t2=System.currentTimeMillis();


        for(int i=0;i<100000;i++) {
            queueStack.push(test);
        }

    }
}
