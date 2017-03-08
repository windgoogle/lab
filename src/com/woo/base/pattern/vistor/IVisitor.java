package com.woo.base.pattern.vistor;

/**
 * Created by huangfeng on 2016/12/20.
 */
public  interface IVisitor{
    public void visit(ConcreteElement1 el1);
    public void visit(ConcreteElement2 el2);
}
