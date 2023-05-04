package com.woo.base.jdk8.defaultinterf;

public interface Person {
    default String getName(){
        return "我是默认姓名";
    }
    default String getId(){
        return "我是默认ID";
    }
}