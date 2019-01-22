package com.woo.base.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class Hello
{
    public static void main(String[] args) throws Exception
    {
        Singleton instanceA = Singleton.getInstance();
        Singleton instanceB = Singleton.getInstance();
        System.out.println(instanceA.hashCode());
        System.out.println(instanceB.hashCode());
        System.out.println(instanceA == instanceB);


        //利用反射破除单例限制
        Constructor c = Singleton.class.getDeclaredConstructor();
        c.setAccessible(true);

        Singleton instanceC = (Singleton)c.newInstance();
        Singleton instanceD = (Singleton)c.newInstance();
        System.out.println(instanceC.hashCode());
        System.out.println(instanceD.hashCode());
        System.out.println(instanceC == instanceD);

        //利用Unsafe 破除单例限制

        Field theUnsafeField = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafeField.setAccessible(true);
        Unsafe unsafeInstance = (Unsafe)theUnsafeField.get(null);
        Singleton instanceE = (Singleton)unsafeInstance.allocateInstance(Singleton.class);
        Singleton instanceF = (Singleton)unsafeInstance.allocateInstance(Singleton.class);
        System.out.println(instanceE.hashCode());
        System.out.println(instanceF.hashCode());
        System.out.println(instanceE == instanceF);
    }
}
