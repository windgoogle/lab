package com.woo.base.timer;


import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;


/**
 * 例子示范timer 任务执行情况，第二个任务执行预期是3s后，但结果是4s, 实际上要等任务1执行完
 */
public class TimerTest {
    private static long start;

    public static void main(String[] args) throws Exception {

        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {

                System.out.println("task1 invoked ! "
                        + (System.currentTimeMillis() - start));
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                System.out.println("task2 invoked ! "
                        + (System.currentTimeMillis() - start));
            }
        };
        Timer timer = new Timer();
        start = System.currentTimeMillis();
        timer.schedule(task1, 1000);
        timer.schedule(task2, 3000);

    }
}
