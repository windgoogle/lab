package com.woo.base.jvm;

/**
 * Created by huangfeng on 2017/1/6.
 */
public class MemAllocation {

    private final static int _1MB =1024*1024;

    public static void main (String args[]) throws Exception {
        System.in.read();
        testAllocation();
       // testPretenureSizeThreshold();
        System.in.read();
    }

    /*
       VM 参数 : -verbose:gc -Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8
     */

    public static void testAllocation () {
        byte [] allocation1,allocation2,allocation3,allocation4;

        allocation1=new byte[2*_1MB];
        allocation2=new byte[2*_1MB];
        allocation3=new byte[2*_1MB];
        allocation4=new byte[4*_1MB];

    }

     /*
       VM 参数 : -verbose:gc -Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8
       -XX:PretenureSizeThreshold=3145728
     */


    public static void testPretenureSizeThreshold () {
        byte [] allocation;
        allocation=new byte[8*_1MB];
    }


}
