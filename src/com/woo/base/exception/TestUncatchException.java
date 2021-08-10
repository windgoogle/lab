package com.woo.base.exception;


public class TestUncatchException
{
    public static class TestRuntimeExceptionThread extends Thread
    {
        // 指定线程名称 便于问题定位
        public TestRuntimeExceptionThread()
        {
            super.setName("thread-TestRuntimeExceptionThread");
        }

        @Override
        public void run()
        {
            throw new RuntimeException("run time exception");
        }
    }

    public static void main(String[] args)
    {
        Thread test = new TestRuntimeExceptionThread();
        // 设置线程默认的异常捕获方法
        test.setUncaughtExceptionHandler((Thread t, Throwable e) -> {
            System.out.println(t.getName() + ": " + e.getMessage());
        });
        test.start();
    }

}
