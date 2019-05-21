package com.woo.base.timer;


import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 这个例子用ScheduledExecutorService解决了ScheduledThreadPoolDemo01 使用Timer造成的问题
 */
public class ScheduledThreadPoolDemo02
{


    public static void main(String[] args) throws InterruptedException
    {

        final TimerTask task1 = new TimerTask()
        {

            @Override
            public void run()
            {
                throw new RuntimeException();
            }
        };

        final TimerTask task2 = new TimerTask()
        {

            @Override
            public void run()
            {
                System.out.println("task2 invoked!");
            }
        };



        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        pool.schedule(task1, 100, TimeUnit.MILLISECONDS);
        pool.scheduleAtFixedRate(task2, 0 , 1000, TimeUnit.MILLISECONDS);

    }
}

