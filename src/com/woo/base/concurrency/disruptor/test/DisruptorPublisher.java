package com.woo.base.concurrency.disruptor.test;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DisruptorPublisher implements EventPublisher {

    private static final WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();
    private Disruptor<TestEvent> disruptor;
    private TestEventHandler handler;
    private RingBuffer<TestEvent> ringbuffer;
    private ExecutorService executor;
    public DisruptorPublisher(int bufferSize, TestHandler handler) {
        this.handler = new TestEventHandler(handler);
        executor = Executors.newSingleThreadExecutor();
        disruptor = new Disruptor<TestEvent>(new EventFactory() {
            public TestEvent newInstance() {
                return new TestEvent();
            }
        },
                bufferSize,
                executor, ProducerType.SINGLE,
                YIELDING_WAIT);
    }

    @SuppressWarnings("unchecked")
    public void start() {
        disruptor.handleEventsWith(handler);
        disruptor.start();
        ringbuffer = disruptor.getRingBuffer();
    }

    @Override
    public void publish(int data) throws Exception {
        long seq = ringbuffer.next();
        try {
            TestEvent evt = ringbuffer.get(seq);
            evt.setValue(data);
        } finally {
            ringbuffer.publish(seq);
        }
    }

    private class TestEventHandler implements EventHandler<TestEvent> {

        private TestHandler handler;

        public TestEventHandler(TestHandler handler) {
            this.handler = handler;
        }

        @Override
        public void onEvent(TestEvent event, long sequence, boolean endOfBatch)
                throws Exception {
            handler.process(event);
        }

    }

    //省略其它代码；
}