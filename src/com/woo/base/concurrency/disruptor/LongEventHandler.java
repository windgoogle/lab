package com.woo.base.concurrency.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * Created by huangfeng on 2017/3/20.
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch)
    {
        System.out.println("Event: " + event);
    }
}