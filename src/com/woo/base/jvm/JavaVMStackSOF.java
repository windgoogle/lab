package com.woo.base.jvm;

/**
 * Created by huangfeng on 2016/12/30.
 */
public class JavaVMStackSOF {

    private int stackLength=1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main (String args[]) throws Throwable {
        JavaVMStackSOF stack=new JavaVMStackSOF();
        try{

            stack.stackLeak();
        }catch(Throwable ex) {
           System.out.println("stack length : "+stack.stackLength);
            throw ex;
        }
    }
}
