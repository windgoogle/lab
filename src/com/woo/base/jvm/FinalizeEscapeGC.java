package com.woo.base.jvm;

/**
 * Created by huangfeng on 2017/1/3.
 */
public class FinalizeEscapeGC {

    public static FinalizeEscapeGC SAVE_HOOK=null;

    public void isAlive() {
        System.out.println("yes ,  i am still alive :) ");
    }

    @Override
    //这个方法只执行一次
    protected  void finalize() throws Throwable{
      super.finalize();
      System.out.println("finalize method executed ! " + this);
      FinalizeEscapeGC.SAVE_HOOK=this;
    }

    public static void main (String args[]) throws Throwable {
        SAVE_HOOK=new FinalizeEscapeGC();

        //对象第一次成功解救自己
        SAVE_HOOK=null;
        System.gc();
        Thread.sleep(500);
        if(SAVE_HOOK!=null) {
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("no ,i am dead !"+SAVE_HOOK);
        }

        //下面代码完全相同，但是解救失败了
        SAVE_HOOK=null;
        System.gc();
        Thread.sleep(500);
        if(SAVE_HOOK!=null) {
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("no ,i am dead !" +SAVE_HOOK);
        }

    }
}
