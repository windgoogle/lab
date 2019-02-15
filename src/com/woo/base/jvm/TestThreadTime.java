package com.woo.base.jvm;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import static java.lang.System.out;

/**
 * Created by huangfeng on 2016/12/23.
 */
public class TestThreadTime {

    public static void main(String args[]) throws Exception {
        Thread t1 = new Thread(new Runnable() {
            @Override

            public void run() {

                try {

                    for (; ; ) {

                        long start = ThreadUtil.getUserTime();

                        //long start = System.currentTimeMillis();
                        out.println("T1 Start: " + start);

                        for (int i = 0; i < 900000000; i++) {

                            for (int j = 0; j < 10000000; j++) {

                            }

                        }

                        Thread.sleep(2);

                        long now = ThreadUtil.getUserTime();

                        // long now = System.currentTimeMillis();
                        out.println("T1 End: " + now);

                        out.println("Thread 1, const(milli Sec): "

                                + (now - start) / 1000000);

                        Thread.sleep(2000);

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }

            }

        });

        Thread t2 = new Thread(new Runnable() {

            @Override

            public void run() {

                try {

                    boolean runned = false;

                    for (; ; ) {

                        long start = ThreadUtil.getUserTime();

                        out.println("T2 Start: " + start);

//                        if (!runned) {

//                            for (int i = 0; i < 900000000; i++) {

//                                for (int j = 0; j < 10000000; j++) {

//                                }

//                            }

//                            runned=true;

//                        }

                        Thread.sleep(500);

                        long now = ThreadUtil.getUserTime();

                        out.println("T2 End: " + now);

                        out.println("Thread 2, const(milli Sec): "

                                + (now - start) / 1000000);

                    }

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }

            }

        });
        t1.setName("test-1");
        t1.setName("test-2");
        t1.start();
        t2.start();

    }

    static class ThreadUtil {
        /**
         * Get CPU time in nanoseconds.
         */

        public static long getCpuTime() {

            ThreadMXBean bean = ManagementFactory.getThreadMXBean();

            return bean.isCurrentThreadCpuTimeSupported() ? bean

                    .getCurrentThreadCpuTime() : 0L;

        }

        /**
         * Get user time in nanoseconds.
         */

        public static long getUserTime() {

            ThreadMXBean bean = ManagementFactory.getThreadMXBean();

            return bean.isCurrentThreadCpuTimeSupported() ? bean

                    .getCurrentThreadUserTime() : 0L;

        }

    }


}
