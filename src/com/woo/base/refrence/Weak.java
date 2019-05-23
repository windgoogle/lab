package com.woo.base.refrence;

import java.lang.ref.WeakReference;

/**
 * Created by huangfeng on 2017/7/3.
 * 这是个弱引用
 */
public class Weak {
    public static void main(String[] args) {
        WeakReference<String> sr = new WeakReference<String>(new String("hello"));

        System.out.println(sr.get());
        System.gc();                //通知JVM的gc进行垃圾回收
        System.out.println(sr.get());
    }
}
