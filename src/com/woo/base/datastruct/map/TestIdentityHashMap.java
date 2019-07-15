package com.woo.base.datastruct.map;

import java.util.IdentityHashMap;
import java.util.Map;

public class TestIdentityHashMap {

    public static void main(String[] args) {
        //IdentityHashMap使用===================================
        Map<String, String> identityHashMap = new IdentityHashMap<>();
        identityHashMap.put(new String("a"), "1");
        identityHashMap.put(new String("a"), "2");
        identityHashMap.put(new String("a"), "3");
        System.out.println(identityHashMap.size()); //3

        Map<Demo, String> identityHashMap2 = new IdentityHashMap<>();
        identityHashMap2.put(new Demo(1), "1");
        identityHashMap2.put(new Demo(1), "2");
        identityHashMap2.put(new Demo(1), "3");
        System.out.println(identityHashMap2.size()); //3

    }

    private static class Demo {
        private Integer id;

        public Demo(Integer id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object obj) {
            return true;
        }

        @Override
        public int hashCode() {
            return id;
        }
    }

}
