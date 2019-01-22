package com.woo.base.unsafe;

class Singleton
{
    private String info = "HELLO SHIT";

    private static Singleton instance;

    private Singleton()
    {
        System.out.println("******实例化对象******");
    }

    public static Singleton getInstance()
    {
        synchronized (Singleton.class)
        {
            if (instance == null)
            {
                instance = new Singleton();
            }
        }
        return instance;
    }

    public void show()
    {
        System.out.println("www.google.com");
    }
}