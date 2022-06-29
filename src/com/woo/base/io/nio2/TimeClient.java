package com.woo.base.io.nio2;

public class TimeClient {
    public static void main(String[] args) {
        int port = 8080;
//通过?个独?的I/O线程创建异步时间服务器客户端handler，
//在实际项?中， 我们不需要独?的线程创建异步连接对象， 因为底层都是通过JDK的系统回调实现的.
        new Thread(new AsyncTimeClientHandler("127.0.0.1", port), "AIO-AsyncTimeClientHandler-001").start();
    }
}