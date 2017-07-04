package com.woo.base.refrence;

import java.lang.ref.SoftReference;

/**
 * Created by huangfeng on 2017/7/3.
 */
public class Soft {

    public static void main(String[] args) {
        SoftReference<String> sr = new SoftReference<String>(new String("hello"));
        System.gc();
        try { // 下面休息几分钟，让上面的垃圾回收线程运行完成
            Thread.currentThread().sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(sr.get());
    }
}
