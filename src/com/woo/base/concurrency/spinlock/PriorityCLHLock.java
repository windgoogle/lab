package com.woo.base.concurrency.spinlock;


import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class PriorityCLHLock implements Lock {
    private final ThreadLocal<Qnode> mynode;
    public AtomicBoolean locked;
    PriorityBlockingQueue<Qnode> priorityQueue = new PriorityBlockingQueue<Qnode>();

    public PriorityCLHLock() {
        locked = new AtomicBoolean();
        this.mynode = new ThreadLocal<Qnode>() {
            protected Qnode initialValue() {
                return new Qnode(5);
            }
        };
    }

    public void lock() {
        final Qnode qnode = this.mynode.get();
        qnode.priority = Thread.currentThread().getPriority();
        priorityQueue.add(qnode);
//		while (priorityQueue.peek() != qnode || !locked.compareAndSet(false, true));
        while (true) {
            while (priorityQueue.peek() != qnode) ;
            while (!locked.compareAndSet(false, true)) ;
            if (priorityQueue.peek() == qnode)
                break;
            locked.set(false);
        }
    }

    public void unlock() {
        final Qnode qnode = this.mynode.get();
        priorityQueue.remove(qnode);
        locked.set(false);
    }
}
