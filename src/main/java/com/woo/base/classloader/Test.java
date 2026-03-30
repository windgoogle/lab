package com.woo.base.classloader;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException {
        // 获取线程上下文类加载器
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        // 原来的写法
        // Class<?> clazz = loader.loadClass("[Ljava.lang.String;");

        // 改成 Class.forName() 写法 ✅
        Class<?> clazz = Class.forName("[Ljava.lang.String;", false, loader);

        // 验证结果
        System.out.println(clazz); // class [Ljava.lang.String;
        System.out.println(clazz.isArray()); // true
        System.out.println(clazz.getComponentType()); // class java.lang.String
    }
}