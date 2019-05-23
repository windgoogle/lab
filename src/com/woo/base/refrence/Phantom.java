package com.woo.base.refrence;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * Created by huangfeng on 2017/7/3.
 * 幽灵引用，虚引用，不会决定引用对象的生命周期，在任何时间均会被垃圾回收器回收
 * 必须和一个引用队列相关联
 * 虚引用主要用来跟踪对象被垃圾回收器回收的活动
 */
public class Phantom {

        public static void main(String[] args) {
            ReferenceQueue<String> queue = new ReferenceQueue<String>();
            PhantomReference<String> pr = new PhantomReference<String>(new String("hello"), queue);
            System.out.println(pr.get());
        }

}
