package com.woo.base.unsafe;


import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Unsafe 例子，直接修改字节数组内容，
 * 只由主类加载器(BootStrap classLoader)加载的类才能调用这个类中的方法，但是可以利用反射获取Unsafe实例
 */
public class Test {
    private static int byteArrayBaseOffset;

    public static void main(String[] args) throws SecurityException,
            NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe UNSAFE = (Unsafe) theUnsafe.get(null);
        System.out.println(UNSAFE);

        byte[] data = new byte[10];
        System.out.println(Arrays.toString(data));
        byteArrayBaseOffset = UNSAFE.arrayBaseOffset(byte[].class);

        System.out.println(byteArrayBaseOffset);
        UNSAFE.putByte(data, byteArrayBaseOffset, (byte) 1);
        UNSAFE.putByte(data, byteArrayBaseOffset + 5, (byte) 5);
        System.out.println(Arrays.toString(data));

        try {
            User t=(User)UNSAFE.allocateInstance(User.class);
            System.out.println(t.getName()+":"+t.getPassword()+" classloader:"+t.getClass().getClassLoader());
            t.setName("hhhh");
            t.setPassword("****");
            System.out.println(t.getName()+":"+t.getPassword());
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}