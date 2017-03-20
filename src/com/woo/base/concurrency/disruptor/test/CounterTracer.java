package com.woo.base.concurrency.disruptor.test;

public interface CounterTracer {

    void start();
    long getMilliTimeSpan();

    boolean count();
    void waitForReached() throws InterruptedException;
 }
