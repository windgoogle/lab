package com.woo.base.datastruct;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * Created by huangfeng on 2016/12/22.
 */
public class QueueStack<E> {
    private Deque<E> deque = new ArrayDeque<E>();

    public void push(E e) {
        deque.push(e);
    }

    public E pop() {
        return deque.pop();
    }

    public Iterator<E> iterate() {
       return  deque.iterator();
    }

    public boolean isEmpty() {
        return deque.isEmpty();
    }

}
