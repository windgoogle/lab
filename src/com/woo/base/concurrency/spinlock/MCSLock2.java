package com.woo.base.concurrency.spinlock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * MCSLock 感觉实现有错误，找了新的实现，个人感觉是比较正确的，看看差别
 */
public class MCSLock2 implements Lock {
    AtomicReference<QNode> tail;
    ThreadLocal<QNode> myNode;

    @Override
    public void lock() {
        tail = new AtomicReference<QNode>(new QNode());
        QNode qnode = myNode.get();
        QNode pred = tail.getAndSet(qnode);
        if (pred != null) {
            qnode.locked = true;
            pred.next = qnode;

            // wait until predecessor gives up the lock
            while (qnode.locked) {
            }
        }
    }

    @Override
    public void unlock() {
        QNode qnode = myNode.get();
        if (qnode.next == null) {
            if (tail.compareAndSet(qnode, null))
                return;

            // wait until predecessor fills in its next field
            while (qnode.next == null) {
            }
        }
        qnode.next.locked = false;
        qnode.next = null;
    }

    class QNode {
        boolean locked = false;
        QNode next = null;
    }
}