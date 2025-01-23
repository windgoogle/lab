package com.woo.base.nio2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.Scanner;

public class AsyncClient {
    private final AsynchronousSocketChannel clientChannel;
    private final String host;
    private final int port;

    public AsyncClient(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        this.clientChannel = AsynchronousSocketChannel.open();
    }

    public void start() throws IOException, ExecutionException, InterruptedException {
        // 连接服务器
        Future<Void> connectFuture = clientChannel.connect(new InetSocketAddress(host, port));
        connectFuture.get(); // 等待连接完成
        System.out.println("已连接到服务器");

        // 创建用户输入扫描器
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("请输入消息 (输入 'exit' 退出): ");
            String message = scanner.nextLine();

            if ("exit".equalsIgnoreCase(message)) {
                break;
            }

            // 发送消息
            ByteBuffer writeBuffer = ByteBuffer.wrap(message.getBytes());
            Future<Integer> writeFuture = clientChannel.write(writeBuffer);
            writeFuture.get(); // 等待写入完成

            // 接收响应
            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
            Future<Integer> readFuture = clientChannel.read(readBuffer);
            readFuture.get(); // 等待读取完成

            readBuffer.flip();
            byte[] responseData = new byte[readBuffer.limit()];
            readBuffer.get(responseData);
            System.out.println("服务器响应: " + new String(responseData));
        }

        // 关闭资源
        scanner.close();
        clientChannel.close();
    }

    public static void main(String[] args) {
        try {
            AsyncClient client = new AsyncClient("localhost", 8080);
            client.start();
        } catch (IOException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}