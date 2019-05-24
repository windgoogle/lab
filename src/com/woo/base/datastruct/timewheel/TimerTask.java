package com.woo.base.datastruct.timewheel;

public class TimerTask  implements ExpirationListener{


    private String name;

    public TimerTask(String name){
        this.name=name;
    }

    @Override
    public void expired(Object expiredObject) {
        System.out.println("task ["+name+"]  expired !");
    }
}
