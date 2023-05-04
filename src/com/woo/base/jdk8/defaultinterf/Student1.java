package com.woo.base.jdk8.defaultinterf;

/**
 * 类优先于接口，Person1 接口和Student都有getName()方法，但是使用的是类Student里的getName()方法
 */
public class Student1 extends Student implements Person1 {

    public static void main(String[] args) {
        Student1 student = new Student1();
        System.out.println(student.getName());
    }
}