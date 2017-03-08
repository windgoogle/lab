package com.woo.base.jvm.singleton;

/**
 * Created by huangfeng on 2017/1/9.
 * IDEA 模板生成的单例模式实现
 * 饿汉实现，优点：简单，线程安全（利用类加载机制） ，缺点： 非Lazy loading
 * 总结起来比较靠谱的四种实现（第五种不推荐）：
 * 1. 饿汉 ---线程安全,
 * 2. 静态内部类  ----饿汉的改良版，线程安全+Lazyloading
 * 3. 双重检查锁定 ----线程安全+Lazy Loading (配合volatile修适配符号，jdk1.5+才完备)
 * 4. 枚举     ----无需线程同步 （需要jdk1.5+,因为支持enum类型）
 * 5. 懒汉 -----需要方法同步，缺点是效率低 ,线程安全+Lazy Loading
 */
public class T {
    private static T ourInstance = new T();

    public static T getInstance() {
        return ourInstance;
    }

    private T() {
    }
}
