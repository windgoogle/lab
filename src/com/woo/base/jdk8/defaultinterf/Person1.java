package com.woo.base.jdk8.defaultinterf;

public interface Person1 {
    default String getName(){
        return "我是另一个默认姓名";
    }
}