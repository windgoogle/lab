package com.woo.base.jvm;

/**
 * Created by huangfeng on 2017/1/6.
 */
public class VolatileDemo {

    public static volatile  int  race=0;

    private static int THREAD_COUNT=3;

    public static  void increase() {
        race++;
    }

    public static void main (String args[]) throws Exception {

     Thread [] threads=new Thread[THREAD_COUNT];
        for (int i=0;i<THREAD_COUNT;i++){
            threads[i]=new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        increase();
                    }
                }
            });

            threads[i].start();
        }

        while (Thread.activeCount()>1)
            Thread.yield();  //让cpu     //这段程序不退出

        System.out.println(race);
    }
}
