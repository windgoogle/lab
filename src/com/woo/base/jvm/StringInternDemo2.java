package com.woo.base.jvm;

/**
 * Created by huangfeng on 2017/1/3.
 */
public class StringInternDemo2 {

    public static void main (String args[]) throws Exception {

        String s = new String("1");
        s.intern();
        String s2 = "1";
        System.out.println(s == s2);

        String s3 = new String("1") + new String("1");
       // s3.intern();
        String s4 = "11";
        s3.intern();
        System.out.println(s3 == s4);

    }
}
