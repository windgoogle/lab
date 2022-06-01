package com.woo.base.scheduled;

import java.util.concurrent.*;

import  java.util.concurrent.TimeUnit;

public class TestDelay {
    public static void main(String[] args) throws Exception {

        ThreadFactory threadFactory=new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                String name="test-sche";
                return new Thread(name);
            }
        };
        System.out.println("Test Start!");
        final ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1, threadFactory, new ThreadPoolExecutor.DiscardPolicy());
        executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        executor.setRemoveOnCancelPolicy(true);
        ScheduledExecutorService executorService=executor;
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("===========ok=");
            }
        },6, TimeUnit.SECONDS);
        System.out.println("Done!");
        Thread.sleep(100000000);

    }
}
