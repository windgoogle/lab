package com.woo.base.jvm;

import java.util.Random;

/**
 * Created by huangfeng on 2017/1/3.
 */
public class StringInternDemo3 {
    static final int MAX = 1000 * 10000;
    static final String[] arr = new String[MAX];

    public static void main(String[] args) throws Exception {
        Integer[] DB_DATA = new Integer[10];
        Random random = new Random(10 * 10000);
        for (int i = 0; i < DB_DATA.length; i++) {
            DB_DATA[i] = random.nextInt();
        }
        long t = System.currentTimeMillis();
        for (int i = 0; i < MAX; i++) {
            //arr[i] = new String(String.valueOf(DB_DATA[i % DB_DATA.length]));
            arr[i] = new String(String.valueOf(DB_DATA[i % DB_DATA.length])).intern(); //-XX:StringTableSize=1099 ,默认大小
        }

        System.out.println((System.currentTimeMillis() - t) + "ms");
        System.gc();
        System.in.read();

    }
}
