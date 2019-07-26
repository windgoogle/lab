package com.woo.base.concurrency.spinlock;

public interface Lock {
    public void lock();
    public void unlock();
}
