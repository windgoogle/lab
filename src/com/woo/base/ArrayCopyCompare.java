package com.woo.base;

import java.util.Date;

public class ArrayCopyCompare {
    public static void main(String[] args) {
        int length = 1000000;
        //init
        System.out.println("array length : "+length);
        int[] array = new int[length];
        for(int i = 0 ; i < array.length ; i ++){
            array[i] = i;
        }

        //use method by system
        long t1 = System.currentTimeMillis();
        int[] arrayCopyBySystem = new int[length];
        System.arraycopy(array, 0, arrayCopyBySystem, 0, array.length);
        long t2 = System.currentTimeMillis();
        System.out.println("use time by system method : "+(t2 - t1));

        //use method normal
        long t3 = System.currentTimeMillis();
        int[] arrayCopyByNormal = new int[length];
        for(int i = 0 ; i < arrayCopyByNormal.length ; i ++){
            arrayCopyByNormal[i] = array[i];
        }
        long t4 = System.currentTimeMillis();
        System.out.println("use time by narmal method : " +(t4 - t3));
    }
}