package com.example;

import java.util.ArrayList;
import java.util.List;

public class MATDemo {
    public static void main(String[] args)  {
        List<User> list=new ArrayList<>();
        while (true){
            list.add(new User());
        }
    }
}


class User {
    private String name="demo";
    public User() {
    }
}