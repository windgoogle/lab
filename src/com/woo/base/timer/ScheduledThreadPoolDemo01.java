package com.woo.base.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 这个例子说明Timer 的另一个弊端，当一个任务异常，会造成所有任务中止
 */
public class ScheduledThreadPoolDemo01
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
               // while (true) {
                    System.out.println("task2 invoked!");
                //}
            }
        };

        Timer timer = new Timer();
        timer.schedule(task1, 100);   //运行一次的延时任务  ，这个任务出现异常，导致任务2停止执行周期性性的任务
        timer.scheduleAtFixedRate(task2, new Date(), 1000);  //周期性的任务


    }
}

