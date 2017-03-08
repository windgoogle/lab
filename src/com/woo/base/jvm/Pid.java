package com.woo.base.jvm;

import java.lang.management.ManagementFactory;

/**
 * Created by huangfeng on 2016/12/28.
 */
public class Pid {

     static {
         System.out.println("pid static code block!");
         Thread.currentThread().dumpStack();
     }

    public static void main (String args[]) throws Exception {
        String pid = ManagementFactory.getRuntimeMXBean().getName();
        int indexOf = pid.indexOf('@');
        if (indexOf > 0) {
            pid = pid.substring(0, indexOf);
        }
        System.out.println("jvm process id : "+pid);
    }
}
