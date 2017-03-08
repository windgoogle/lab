package com.woo.base.jvm;

/**
 * Created by huangfeng on 2016/12/29.
 */
public class TestString {

    public static void main (String args[]) throws Exception {
        String str1 = "a";
        String str2 = "bc";
        String str3 = "a"+"bc";
        String str4 = str1+str2;

        System.out.println(str3==str4);
        str4 = (str1+str2).intern();
        System.out.println(str3==str4);

    }
}
