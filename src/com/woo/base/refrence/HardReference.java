package com.woo.base.refrence;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Created by huangfeng on 2017/7/3.
 * 这是个软引用
 */
public class HardReference<T>  extends SoftReference<T> {
    private final T hard;

    public HardReference(T referent) {
        super(referent);
        this.hard=referent;
    }

    public static void main(String[] args) {
        HardReference<String> sr = new HardReference<String>(new String("hello"));
        System.gc();
        try { // 下面休息几分钟，让上面的垃圾回收线程运行完成
            Thread.currentThread().sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(sr.get());
    }
}
