package com.woo.base.jdk8.defaultinterf;



public class Student implements Person, Person1 {

    @Override
    public String getName() {
        return "我是实现类中定义的姓名";
    }

    @Override
    public String getId() {
        return "32";
    }

    public static void main(String[] args) {
        Student student = new Student();
        System.out.println(student.getName());
        System.out.println("重写接口类的默认方法，则结果由实现类决定：");
        System.out.println(student.getId());
    }
}