package com.woo.base.jvm;

public class TestCodeSource {

    public static void main(String[] args) throws Exception {
        TestCodeSource tcs=new TestCodeSource();
        ClassLoader cl=tcs.getClass().getClassLoader();
        System.out.println("----"+tcs.getClass().getResource(""));
        System.out.println("----"+tcs.getClass().getProtectionDomain().getCodeSource().getLocation());
    }
}
