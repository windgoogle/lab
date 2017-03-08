package com.woo.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangfeng on 2016/12/19.
 */
public class TestByteCode {

    public String a="this is a test !";
    public int n=9;
    private String b;
    private int c=2;
    public boolean isLocked=false;
    public double s=9.89;
    public float sss=1;
    public char ccc='t';
    public byte l=4;

    public String [] tl = new String[]{"s","b","c"};

    public List<String> aList=new ArrayList<String>();

    public void Test () {
        System.out.println("sdfsfasdfasdfasf");
    }

    public List<String> getAlist() {
        return aList;
    }

    public static void main (String args[]) {
          System.out.println("this is main method");
    }

}
