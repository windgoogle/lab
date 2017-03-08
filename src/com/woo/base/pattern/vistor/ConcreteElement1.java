package com.woo.base.pattern.vistor;

/**
 * Created by huangfeng on 2016/12/20.
 */

public class ConcreteElement1 extends Element{
    public void doSomething(){
        System.out.println("这是元素1");
    }
    public void accept(IVisitor visitor){
        visitor.visit(this);
    }
}