package com.woo.base.refrence;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * Created by huangfeng on 2017/7/3.
 */
public class Phantom {

        public static void main(String[] args) {
            ReferenceQueue<String> queue = new ReferenceQueue<String>();
            PhantomReference<String> pr = new PhantomReference<String>(new String("hello"), queue);
            System.out.println(pr.get());
        }

}
