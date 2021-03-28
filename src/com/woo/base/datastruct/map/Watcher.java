package com.woo.base.datastruct.map;

import java.util.concurrent.ConcurrentHashMap;

public class Watcher {

    public ConcurrentHashMap<String,Integer> map=new ConcurrentHashMap<String,Integer>();

    public void add(String key) {
        Integer value = map.get(key);
        if(value==null) {
            map.put(key,1);
        }
        else {
            map.put(key,value+1);
        }
    }

    public void run(){
        add("newKey");
    }



    public static void main(String[] args) {
        Watcher watcher=new Watcher();
        Thread a=new TestThread(watcher);
        Thread b=new TestThread(watcher);
        a.start();
        b.start();
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("newKey:"+watcher.map.get("newKey"));
    }

}

 class TestThread extends Thread {
    private Watcher watcher;

    public TestThread(Watcher watcher) {
        this.watcher=watcher;
    }
    public void run(){
        watcher.add("newKey");
    }
}