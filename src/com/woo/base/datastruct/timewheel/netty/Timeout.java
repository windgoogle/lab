package com.woo.base.datastruct.timewheel.netty;

public interface Timeout {
    Timer timer();
    TimerTask task();
    boolean isExpired();
    boolean isCancelled();
    boolean cancel();

}
