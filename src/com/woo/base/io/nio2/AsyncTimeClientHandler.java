package com.woo.base.io.nio2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class AsyncTimeClientHandler implements CompletionHandler<Void, AsyncTimeClientHandler>, Runnable {
    private AsynchronousSocketChannel client;
    private String host;
    private int port;
    private CountDownLatch latch;

    //?��ͨ��AsynchronousSocketChannel��open?������?���µ�AsynchronousSocketChannel����
    public AsyncTimeClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            client = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        //����CountDownLatch��?�ȴ��� ��?�첽����û��ִ?����߳̾��˳���
        latch = new CountDownLatch(1);
        //ͨ��connect?�������첽������ ��������������
        //A attachment�� AsynchronousSocketChannel�ĸ����� ?�ڻص�֪ͨʱ��Ϊ?�α����ݣ� ��?�߿���?���壻
        //CompletionHandler�� Void,? super A�� handler�� �첽�����ص�֪ͨ��?�� �ɵ�?��ʵ�֡�
        client.connect(new InetSocketAddress(host, port), this, this);
        try {
            latch.await();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //�첽���ӳɹ�֮���?���ص�����completed?��

    @Override
    public void completed(Void result, AsyncTimeClientHandler attachment) {
        //����������Ϣ�壬 �����?���룬 Ȼ���Ƶ����ͻ�����writeBuffer�У�
        //��?Asynchronous SocketChannel��write?����?�첽д��
        //���������ƣ� ���ǿ���ʵ��CompletionHandler �� Integer, ByteBuffer����??��д������ɺ�Ļص���
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        client.write(writeBuffer, writeBuffer,
                new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer buffer) {
                //������ͻ�������������δ���͵��ֽڣ� �������첽���ͣ� ����Ѿ�������ɣ� ��ִ?�첽��ȡ������
                        if (buffer.hasRemaining()) {
                            client.write(buffer, buffer, this);
                        } else {
                //�ͻ����첽��ȡʱ������������Ӧ����Ϣ�Ĵ����߼�
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                //��?AsynchronousSocketChannel��read?���첽��ȡ����˵���Ӧ��Ϣ��
                //����read�������첽�ģ� ��������ͨ���ڲ�������ʵ��CompletionHandler�� Integer�� ByteBuffer����?,
                //����ȡ��ɱ�JDK�ص�ʱ�� ����Ӧ����Ϣ��
                            client.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>() {
                                @Override
                                public void completed(Integer result, ByteBuffer buffer) {
                //��CompletionHandler��ByteBuffer�ж�ȡӦ����Ϣ�� Ȼ���ӡ�����
                                    buffer.flip();
                                    byte[] bytes = new byte[buffer.remaining()];
                                    buffer.get(bytes);
                                    String body;
                                    try {
                                        body = new String(bytes, "UTF-8");
                                        System.out.println("Now is : " + body);
                                        latch.countDown();
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void failed(Throwable exc, ByteBuffer attachment) {
            //����ȡ��?�쳣ʱ�� �ر���·��
            //ͬʱ��?CountDownLatch��countDown?����AsyncTimeClientHandler�߳�ִ?��ϣ� �ͻ����˳�ִ?��
                                    try {
                                        client.close();
                                        latch.countDown();
                                    } catch (IOException e) {
            // ingnore on close
                                    }
                                }
                            });
                        }
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        try {
                            client.close();
                            latch.countDown();
                        } catch (IOException e) {
            // ingnore on close
                        }
                    }
                });
    }

    @Override
    public void failed(Throwable exc, AsyncTimeClientHandler attachment) {
        exc.printStackTrace();
        try {
            client.close();
            latch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}