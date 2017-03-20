package com.woo.base.concurrency.disruptor.test;


public interface EventPublisher {
    void publish(int data) throws Exception;
}
