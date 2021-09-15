package com.woo.base;

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomTest {
    public static void main(String[] args) {
        long bound=10_000/40;
        long randm=ThreadLocalRandom.current().nextLong(bound);
        System.out.println("-----------"+randm);
    }
}
