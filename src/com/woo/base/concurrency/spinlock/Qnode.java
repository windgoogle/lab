package com.woo.base.concurrency.spinlock;

public class Qnode implements Comparable<Qnode>{
    public volatile int priority;
    public Qnode(int x){
        this.priority = x;
    }
    public int getPriority(){
        return priority;
    }
    public boolean equals(Qnode other){
        return this.getPriority() == other.getPriority();
    }
    @Override
    public int compareTo(Qnode other) {
        if (other == null){
            return -1;
        }
        if (getPriority() >= other.getPriority()){
            return 1;
        }
        else {
            return -1;
        }
    }
    public String toString(){
        return "Priority of this thread is " + getPriority();
    }
}