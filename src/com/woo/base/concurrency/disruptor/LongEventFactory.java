package com.woo.base.concurrency.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by huangfeng on 2017/3/20.
 */
public class LongEventFactory implements EventFactory<LongEvent> {
    public LongEvent newInstance() {
        return new LongEvent();
    }
}