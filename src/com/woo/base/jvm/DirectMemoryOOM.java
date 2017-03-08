package com.woo.base.jvm;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by huangfeng on 2016/12/30.
 */
public class DirectMemoryOOM {

    private final static  int _1MB=1024*1024;

    public static void main (String args[]) throws Exception {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe=(Unsafe)unsafeField.get(null);
        while (true) {
            try {
                unsafe.allocateMemory(_1MB);
            }catch(OutOfMemoryError oom) {
                oom.printStackTrace();
                break;
            }
        }
        System.in.read();
    }
}
