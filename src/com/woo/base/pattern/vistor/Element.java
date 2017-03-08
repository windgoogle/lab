package com.woo.base.pattern.vistor;

/**
 * Created by huangfeng on 2016/12/20.
 */
public  abstract class Element
{
    public abstract void accept(IVisitor visitor);
    public abstract void doSomething();
}