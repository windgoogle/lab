package com.woo.base.jvm.singleton;

/**
 * Created by huangfeng on 2017/1/9.
 * 利用静态内部类实现单例 ，Lazy loading + 线程安全
 */
public class Singleton2 {

    private static class SinletonHolder {
        private static final Singleton2 INSTANCE=new Singleton2();  //A行， 类加载机制保证只有单线程操作创建实例
    }

    private Singleton2(){   //第A行才调用
        System.out.println("Singleton2 involked !");
        Thread.dumpStack();
    }

    public static Singleton2 getInstance() {
        return SinletonHolder.INSTANCE;
    }

    public static void main(String[] args) {
        Singleton2.getInstance();
        System.out.println("this is Main!");
    }
}
