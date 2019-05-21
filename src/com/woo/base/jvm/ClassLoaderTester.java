package com.woo.base.jvm;

import java.net.URL;

public class ClassLoaderTester {

    public static void main(String[] args) throws Exception{
        Thread.currentThread().setContextClassLoader(new XXClassLoader(new URL[]{new URL("file","localhost","/1.txt")}));

        DemoServlet d = new DemoServlet();

    }



}

