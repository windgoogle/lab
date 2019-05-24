package com.woo.base.datastruct.timewheel.netty;

public interface  TimerTask {
    void run(Timeout timeout) throws Exception;
}
