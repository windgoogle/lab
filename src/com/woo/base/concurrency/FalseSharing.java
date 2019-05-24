package com.woo.base.concurrency;

import java.util.concurrent.atomic.AtomicLong;


/**
 * 伪共享
 * 一条缓存行有 64 字节，而 Java 程序的对象头固定占 8 字节(32位系统)或 12 字节( 64 位系统默认开启压缩, 不开压缩为 16 字节)，
 * 所以我们只需要填 6 个无用的长整型补上6*8=48字节，让不同的 VolatileLong 对象处于不同的缓存行，
 * 就避免了伪共享( 64 位系统超过缓存行的 64 字节也无所谓，只要保证不同线程不操作同一缓存行就可以)。
 */
public final class FalseSharing
        implements Runnable
{
    public final static int NUM_THREADS = 16; // change
    public final static long ITERATIONS = 500L * 1000L * 1000L;
    private final int arrayIndex;

    private static VolatileLong[] longs = new VolatileLong[NUM_THREADS];
    static
    {
        for (int i = 0; i < longs.length; i++)
        {
            longs[i] = new VolatileLong();
        }
    }

    public FalseSharing(final int arrayIndex)
    {
        this.arrayIndex = arrayIndex;
    }

    public static void main(final String[] args) throws Exception
    {
        final long start = System.nanoTime();
        runTest();
        System.out.println("duration = " + (System.nanoTime() - start));
    }

    private static void runTest() throws InterruptedException
    {
        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < threads.length; i++)
        {
            threads[i] = new Thread(new FalseSharing(i));
        }

        for (Thread t : threads)
        {
            t.start();
        }

        for (Thread t : threads)
        {
            t.join();
        }
    }

    public void run()
    {
        long i = ITERATIONS + 1;
        while (0 != --i)
        {
            longs[arrayIndex].value=i;
        }
    }

    /**
     * 由于某些 Java 编译器的优化策略，那些没有使用到的补齐数据可能会在编译期间被优化掉，我们可以在程序中加入一些代码防止被编译优化。
     * 如下：
     */
    public static long sumPaddingToPreventOptimisation(final int index)
    {
        VolatileLong v = longs[index];
        return v.p1 + v.p2 + v.p3 + v.p4 + v.p5 + v.p6;

    }

    //jdk7以上使用此方法(jdk7的某个版本oracle对伪共享做了优化)
    public final static class VolatileLong
    {
        public volatile long value = 0L;
        public long p1, p2, p3, p4, p5, p6;
    }

    // jdk7以下使用此方法
   // public final static class VolatileLong
    //{
      //  public long p1, p2, p3, p4, p5, p6, p7; // cache line padding
       // public volatile long value = 0L;
       // public long p8, p9, p10, p11, p12, p13, p14; // cache line padding

    //}
}