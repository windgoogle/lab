package com.woo.base.jvm;

/**
 * Created by huangfeng on 2016/12/30.
 */
public class JavaStackOOM {

    private void dontStop() {
        while(true) {

        }
    }

    public void stackLeakByThread () {
        while (true) {
            Thread thread=new Thread() {
                @Override
                public void run() {
                    dontStop();
                }
            };
            thread.start();
        }
    }


    public static  void main (String args[]) throws Exception {
        JavaStackOOM oom=new JavaStackOOM();
        oom.stackLeakByThread();
    }
}
