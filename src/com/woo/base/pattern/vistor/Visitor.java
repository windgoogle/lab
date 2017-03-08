package com.woo.base.pattern.vistor;

/**
 * Created by huangfeng on 2016/12/20.
 */
public class Visitor implements IVisitor{
    public void visit(ConcreteElement1 el1){
        el1.doSomething();
    }
    public void visit(ConcreteElement2 el2){
        el2.doSomething();
    }
}