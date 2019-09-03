package com.woo.base.concurrency;


public final class FalseSharingFail implements Runnable {
    public final static int NUM_THREADS = 4; // change
    public final static long ITERATIONS = 500L * 1000L * 1000L;
    private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];

    static {
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new VolatileLong();
        }
    }

    private final int arrayIndex;

    public FalseSharingFail(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public static void main(final String[] args) throws Exception {
        System.out.println("VolatileLong未解决了伪共享问题.所以每次longs[i]都会将其他的longs[i+1]或者longs[i-1]的64位加载进来.");
        final long start = System.nanoTime();
        runTest();
        System.out.println("持续时间 = " + (System.nanoTime() - start));
    }

    private static void runTest() throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseSharingFail(i));
        }
// 启动所有线程
        for (Thread t : threads) {
            t.start();
        }
// 等待所有线程停止
        for (Thread t : threads) {
            t.join();
        }
    }

    public void run() {
        long i = ITERATIONS + 1;
        while (0 != --i) {
            longs[arrayIndex].value = i;
        }
    }

    public final static class VolatileLong {
        public volatile long value = 0L;
        //public long p1, p2, p3, p4, p5, p6; // comment out
    }
}
