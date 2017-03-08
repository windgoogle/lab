package com.woo.base.asm;

/**
 * Created by huangfeng on 2016/12/19.
 */
public class Test {
    public static void main(String[] args) throws InterruptedException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        C c = new C();
        c.m();
        Class cc = c.getClass();
        System.out.println(cc.getField("timer").get(c));
    }
}