package com.woo.base.jvm.singleton;

/**
 * Created by huangfeng on 2017/1/6.
 * DCL + volatile 完全正确的单例模式
 * 双重校验锁 JDK1.5才能正常使用  Lazy loading +线程安全 （JDK1.5+）
 */
public class Singleton {

    private volatile static Singleton instance;

    public static Singleton getInstance() {
        if(instance==null){              //第一重检查   ，多线程情况下，另一个线程B 发现instance可能为没有初始化的变量
            synchronized (Singleton.class) {  //锁定
                if(instance==null)      //第二重检查                    instance=new Singleton();
                //这行，用于是volatile ,所以禁止重排序，这里有1.分配内存空间，2.初始化对象，3.设置instance指向内存空间，2和3 顺序可能重排
                //JDK 1.5之前，这段代码仍然不好使，主要是volatile变量前后的代码仍然存在重排序的问题
                    instance=new Singleton();
            }
        }

        return instance;
    }


    public static void main(String[] args) {
        Singleton.getInstance();
    }

}
