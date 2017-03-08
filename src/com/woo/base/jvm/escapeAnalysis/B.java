package com.woo.base.jvm.escapeAnalysis;

/**
 * Created by huangfeng on 2016/12/26.
 */
public class B {

    public void printClassName(A a) {
        System.out.println(a.getClass().getName());
    }
}
