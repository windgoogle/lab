package com.woo.base.nio2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class AsyncServer {
    private final AsynchronousServerSocketChannel serverChannel;
    private final CountDownLatch latch;

    public AsyncServer(int port) throws IOException {
        // 创建服务器通道
        serverChannel = AsynchronousServerSocketChannel.open();
        // 绑定端口
        serverChannel.bind(new InetSocketAddress(port));
        System.out.println("服务器启动在端口: " + port);
        latch = new CountDownLatch(1);
    }

    public void start() {
        // 开始接受客户端连接
        serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
            @Override
            public void completed(AsynchronousSocketChannel clientChannel, Void attachment) {
                // 继续接受下一个连接
                serverChannel.accept(null, this);

                // 处理当前连接
                handleClientConnection(clientChannel);
            }

            @Override
            public void failed(Throwable exc, Void attachment) {
                System.err.println("接受连接失败: " + exc.getMessage());
            }
        });

        try {
            latch.await(); // 保持服务器运行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleClientConnection(AsynchronousSocketChannel clientChannel) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // 开始读取数据
        clientChannel.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                if (result == -1) {
                    // 客户端断开连接
                    try {
                        clientChannel.close();
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                attachment.flip();
                byte[] data = new byte[attachment.limit()];
                attachment.get(data);
                String message = new String(data);
                System.out.println("收到客户端消息: " + message);

                // 发送响应
                String response = "服务器已收到消息: " + message;
                ByteBuffer writeBuffer = ByteBuffer.wrap(response.getBytes());

                clientChannel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        // 继续读取下一个消息
                        buffer.clear();
                        clientChannel.read(buffer, buffer, AsyncServer.this.new ReadHandler(clientChannel));
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        System.err.println("发送响应失败: " + exc.getMessage());
                    }
                });
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.err.println("读取数据失败: " + exc.getMessage());
            }
        });
    }

    // 内部读取处理器类
    private class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {
        private final AsynchronousSocketChannel clientChannel;

        ReadHandler(AsynchronousSocketChannel clientChannel) {
            this.clientChannel = clientChannel;
        }

        @Override
        public void completed(Integer result, ByteBuffer buffer) {
            if (result == -1) {
                try {
                    clientChannel.close();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            buffer.flip();
            byte[] data = new byte[buffer.limit()];
            buffer.get(data);
            String message = new String(data);
            System.out.println("收到客户端消息: " + message);

            // 发送响应
            String response = "服务器已收到消息: " + message;
            ByteBuffer writeBuffer = ByteBuffer.wrap(response.getBytes());
            clientChannel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    buffer.clear();
                    clientChannel.read(buffer, buffer, ReadHandler.this);
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    System.err.println("发送响应失败: " + exc.getMessage());
                }
            });
        }

        @Override
        public void failed(Throwable exc, ByteBuffer buffer) {
            System.err.println("读取数据失败: " + exc.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            AsyncServer server = new AsyncServer(18080);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}