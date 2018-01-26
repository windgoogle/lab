package com.woo.base.jvm.heap;

import java.util.List;
import java.util.Vector;

public class TraceStudent {
    static List<WebPage> webPages=new Vector<WebPage>();
    public static void createWebPages() {
        for (int i=0;i<100;i++) {
            WebPage wp=new WebPage();
            wp.setUrl("http://www."+Integer.toString(i)+".com");
            wp.setContent(Integer.toString(i));
            webPages.add(wp);
        }
    }


    public static void main(String[] args) {
        createWebPages();
        Student st3=new Student(3,"billy");
        Student st5=new Student(5,"alice");
        Student st7=new Student(7,"taotao");

        for(int i=0;i<webPages.size();i++) {
            if(i%st3.getId()==0)
                st3.visit(webPages.get(i));
            if(i%st5.getId()==0)
                st5.visit(webPages.get(i));
            if(i%st7.getId()==0)
                st7.visit(webPages.get(i));
        }
        webPages.clear();
        System.gc();
    }
}

