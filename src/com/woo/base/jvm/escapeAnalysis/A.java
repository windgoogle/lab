package com.woo.base.jvm.escapeAnalysis;

/**
 * Created by huangfeng on 2016/12/26.
 */
public class A {
    public static B b;

    public void globalVariablePointerEscape() { // 给全局变量赋值，发生逃逸
        b = new B();
    }

    public B methodPointerEscape() { // 方法返回值，发生逃逸
        return new B();
    }

    public void instancePassPointerEscape() {
        methodPointerEscape().printClassName(this); // 实例引用传递，发生逃逸
    }


    public static void main (String args[]) throws Exception{
         A a=new A();
         int n=1000000;
         long  t1=System.currentTimeMillis();
         //for (int i=0;i<n;i++) {
           //  a.instancePassPointerEscape();
         //}
         B b =new B();
         for (int i=0;i<n;i++)
            b.printClassName(a);   //这个没有发生逃逸
         long t2=System.currentTimeMillis();
         System.out.println("cost time :" +(t2-t1));
    }
}

