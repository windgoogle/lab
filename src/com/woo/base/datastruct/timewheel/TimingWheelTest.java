package com.woo.base.datastruct.timewheel;

import java.util.concurrent.TimeUnit;

public class TimingWheelTest {


    public static void main(String[] args) throws Exception {

        /*
        TimingWheel<TimerTask> timingWheel=new TimingWheel<TimerTask>(1,60, TimeUnit.SECONDS);

        TimerTask task1=new TimerTask("Task-1");

        TimerTask task2=new TimerTask("Task-2");

        timingWheel.start();

        timingWheel.addExpirationListener(task1);

        timingWheel.add(task1);

        Thread.sleep(2000);

        timingWheel.add(task2);

        //timingWheel.addExpirationListener(task2);

         */

        int ticksPerWheel =62;
        ticksPerWheel=normalizeTicksPerWheel(ticksPerWheel);
        System.out.println("ticksPerWheel="+ticksPerWheel);

       // ticksPerWheel =128;
        //System.out.println("ticksPerWheel="+normalizeTicksPerWheel(ticksPerWheel));

    }


    private static int normalizeTicksPerWheel(int ticksPerWheel) {
        int normalizedTicksPerWheel = 1;
        while (normalizedTicksPerWheel < ticksPerWheel) {
            normalizedTicksPerWheel <<= 1;
        }
        return normalizedTicksPerWheel;
    }

}
