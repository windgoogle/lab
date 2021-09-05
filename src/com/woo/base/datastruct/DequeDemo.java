package com.woo.base.datastruct;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by huangfeng on 2016/12/22.
 */
public class DequeDemo {
    public static void main(String[] args) {
        Deque<String> deque = new LinkedList<String>();
        deque.push("a");
        deque.push("b");
        deque.push("c");
        System.out.println(deque);
        //获取栈首元素后，元素不会出栈
        String str = deque.peek();
        System.out.println(str);
        System.out.println(deque);
        while(deque.size() > 0) {
            //获取栈首元素后，元素将会出栈
            System.out.println(deque.pop());
        }
        System.out.println(deque);
        deque.offer("a");
        deque.offer("b");
        deque.offer("c");
        System.out.println(deque);
        System.out.println(deque.poll());

    }
}