package com.woo.base.scheduled;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Test {
    private final static ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public static void main(String[] args) throws Exception{
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                int[] s = new int[1];
                System.out.println("OK");
               // System.out.println(s[1]); // 数组越界
            }
        }, 0,  TimeUnit.MILLISECONDS);
    }
}

