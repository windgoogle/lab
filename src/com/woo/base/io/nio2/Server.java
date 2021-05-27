package com.woo.base.io.nio2;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.Future;

public class Server {
    public static void main(String[] args) {
        try {
            Server server = new Server();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Server() throws Exception {
        AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 80);
        serverSocketChannel.bind(inetSocketAddress);

        Future<AsynchronousSocketChannel> accept;

        while (true) {
            // accept()不会阻塞。
            accept = serverSocketChannel.accept();

            System.out.println("=================");
            System.out.println("服务器等待连接...");
            AsynchronousSocketChannel socketChannel = accept.get();// get()方法将阻塞。

            System.out.println("服务器接受连接");
            System.out.println("服务器与" + socketChannel.getRemoteAddress() + "建立连接");

            ByteBuffer buffer = ByteBuffer.wrap("zhangphil".getBytes());
            Future<Integer> write=socketChannel.write(buffer);

            while(!write.isDone()) {
                Thread.sleep(10);
            }

            System.out.println("服务器发送数据完毕.");
            socketChannel.close();
        }
    }
}
