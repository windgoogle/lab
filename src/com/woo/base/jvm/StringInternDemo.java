package com.woo.base.jvm;

/**
 * Created by huangfeng on 2016/12/30.
 */
public class StringInternDemo {
    public static void main(String[] args) {
        printJdkVersion();
        testAndPrintResult("计算机", "软件");     //jdk 1.6 这些字符串和StringBuilder创建的字符串是不同（一个在堆里，一个在Perm Space）的，所以全部为false

        testAndPrintResult("ja", "va");           //jdk1.7+  常量池在堆空间，String.intern() 如果堆中已经存在该字符串，常量池存的是该字符串的引用地址，如果不存在，
                                                   // 则在常量池里创建一个对象，
        testAndPrintResult("ma", "in");
        testAndPrintResult("Str", "ing");
    }

    private static void testAndPrintResult(String prefix, String suffix) {
        String str3 = new StringBuilder(prefix).append(suffix).toString();
        System.out.println(str3.intern() == str3);
        //计算机软件这个字符串不存在，所以str3.intern() 在常量池里存的是堆中这个对象的引用，str3.intern()方法返回值是这个堆对象地址，所以他们是相等的
        //java 常量池已经存在了，不是引用地址，再创建一个str3，是新的对象，所以他们不相等



    }

    private static void printJdkVersion() {
        String javaVersion = "java.version";
        System.out.println(javaVersion + ":" + System.getProperty(javaVersion));
    }
}