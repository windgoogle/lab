package com.woo.base.io.nio2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class AsyncTimeServerHandler implements Runnable {
    CountDownLatch latch;
    AsynchronousServerSocketChannel asynchronousServerSocketChannel;

    public AsyncTimeServerHandler(int port) {
        //在构造?法中， 我们?先创建?个异步的服务端通道AsynchronousServerSocketChannel，
        //然后调?它的bind?法绑定监听端?， 如果端?合法且没被占?， 绑定成功， 打印启动成功提?到控制台。
        try {
            asynchronousServerSocketChannel = AsynchronousServerSocketChannel.open();
            asynchronousServerSocketChannel.bind(new InetSocketAddress(port));
            System.out.println("The time server is start in port : " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
    //在线程的run?法中， 初始化CountDownLatch对象，
    //它的作?是在完成?组正在执?的操作之前， 允许当前的线程?直阻塞。
    //在本例程中， 我们让线程在此阻塞， 防?服务端执?完成退出。
    //在实际项?应?中， 不需要启动独?的线程来处理AsynchronousServerSocketChannel， 这?仅仅是个demo演?。
        latch = new CountDownLatch(1);
        doAccept();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //?于接收客户端的连接， 由于是异步操作，
    //我们可以传递?个CompletionHandler＜ AsynchronousSocketChannel,? super A＞类型的handler实例接收accept操作成功的通知消息，
    //在本例程中我们通过AcceptCompletionHandler实例作为handler来接收通知消息，
    public void doAccept() {
        asynchronousServerSocketChannel.accept(this, new CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler>() {
            @Override
            public void completed(AsynchronousSocketChannel result,
                                  AsyncTimeServerHandler attachment) {
        //我们从attachment获取成员变量AsynchronousServerSocketChannel， 然后继续调?它的accept?法。
        //在此可能会?存疑惑： 既然已经接收客户端成功了， 为什么还要再次调?accept?法呢？
        //原因是这样的： 当我们调?AsynchronousServerSocketChannel的accept?法后，
        //如果有新的客户端连接接?， 系统将回调我们传?的CompletionHandler实例的completed?法，
        //表?新的客户端已经接?成功， 因为?个AsynchronousServerSocket Channel可以接收成千上万个客户端，
        //所以我们需要继续调?它的accept?法， 接收其他的客户端连接， 最终形成?个循环。
        //每当接收?个客户读连接成功之后， 再异步接收新的客户端连接。
                attachment.asynchronousServerSocketChannel.accept(attachment, this);
        //链路建?成功之后， 服务端需要接收客户端的请求消息，
        //创建新的ByteBuffer， 预分配1M的缓冲区。
                ByteBuffer buffer = ByteBuffer.allocate(1024);
        //通过调?AsynchronousSocketChannel的read?法进?异步读操作。
        //下?我们看看异步read?法的参数。
        //ByteBuffer dst： 接收缓冲区， ?于从异步Channel中读取数据包；
        //A attachment： 异步Channel携带的附件， 通知回调的时候作为?参使?；
        //CompletionHandler＜ Integer,? super A＞： 接收通知回调的业务handler， 本例程中为ReadCompletionHandler。
                result.read(buffer, buffer, new ReadCompletionHandler(result));
            }

            @Override
            public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
                exc.printStackTrace();
                attachment.latch.countDown();
            }
        });
    }
}