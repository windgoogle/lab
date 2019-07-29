package com.woo.base.concurrency.spinlock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * CLHLock的另一种实现，有两个节点
 */
public class CLHLock2 implements Lock {
    AtomicReference<QNode> tail ;
    ThreadLocal<QNode> myPred;
    ThreadLocal<QNode> myNode;

    public CLHLock2() {

        tail = new AtomicReference<QNode>(new QNode());
        myNode = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return new QNode();
            }
        };
        myPred = new ThreadLocal<QNode>() {
            protected QNode initialValue() {
                return null;
            }
        };
    }

    @Override
    public void lock() {
        QNode qnode = myNode.get();
        qnode.locked = true;
        QNode pred = tail.getAndSet(qnode);
        myPred.set(pred);
        while (pred.locked) {
        }
    }

    @Override
    public void unlock() {
        QNode qnode = myNode.get();
        qnode.locked = false;
        myNode.set(myPred.get());
    }

    class QNode {
        boolean locked = false;
        MCSLock2.QNode next = null;
    }
}