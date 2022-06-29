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
        //�ڹ���?���У� ����?�ȴ���?���첽�ķ����ͨ��AsynchronousServerSocketChannel��
        //Ȼ���?����bind?���󶨼�����?�� �����?�Ϸ���û��ռ?�� �󶨳ɹ��� ��ӡ�����ɹ���?������̨��
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
    //���̵߳�run?���У� ��ʼ��CountDownLatch����
    //������?�������?������ִ?�Ĳ���֮ǰ�� ����ǰ���߳�?ֱ������
    //�ڱ������У� �������߳��ڴ������� ��?�����ִ?����˳���
    //��ʵ����?Ӧ?�У� ����Ҫ������?���߳�������AsynchronousServerSocketChannel�� ��?�����Ǹ�demo��?��
        latch = new CountDownLatch(1);
        doAccept();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //?�ڽ��տͻ��˵����ӣ� �������첽������
    //���ǿ��Դ���?��CompletionHandler�� AsynchronousSocketChannel,? super A�����͵�handlerʵ������accept�����ɹ���֪ͨ��Ϣ��
    //�ڱ�����������ͨ��AcceptCompletionHandlerʵ����Ϊhandler������֪ͨ��Ϣ��
    public void doAccept() {
        asynchronousServerSocketChannel.accept(this, new CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler>() {
            @Override
            public void completed(AsynchronousSocketChannel result,
                                  AsyncTimeServerHandler attachment) {
        //���Ǵ�attachment��ȡ��Ա����AsynchronousServerSocketChannel�� Ȼ�������?����accept?����
        //�ڴ˿��ܻ�?���ɻ� ��Ȼ�Ѿ����տͻ��˳ɹ��ˣ� Ϊʲô��Ҫ�ٴε�?accept?���أ�
        //ԭ���������ģ� �����ǵ�?AsynchronousServerSocketChannel��accept?����
        //������µĿͻ������ӽ�?�� ϵͳ���ص����Ǵ�?��CompletionHandlerʵ����completed?����
        //��?�µĿͻ����Ѿ���?�ɹ��� ��Ϊ?��AsynchronousServerSocket Channel���Խ��ճ�ǧ������ͻ��ˣ�
        //����������Ҫ������?����accept?���� ���������Ŀͻ������ӣ� �����γ�?��ѭ����
        //ÿ������?���ͻ������ӳɹ�֮�� ���첽�����µĿͻ������ӡ�
                attachment.asynchronousServerSocketChannel.accept(attachment, this);
        //��·��?�ɹ�֮�� �������Ҫ���տͻ��˵�������Ϣ��
        //�����µ�ByteBuffer�� Ԥ����1M�Ļ�������
                ByteBuffer buffer = ByteBuffer.allocate(1024);
        //ͨ����?AsynchronousSocketChannel��read?����?�첽��������
        //��?���ǿ����첽read?���Ĳ�����
        //ByteBuffer dst�� ���ջ������� ?�ڴ��첽Channel�ж�ȡ���ݰ���
        //A attachment�� �첽ChannelЯ���ĸ����� ֪ͨ�ص���ʱ����Ϊ?��ʹ?��
        //CompletionHandler�� Integer,? super A���� ����֪ͨ�ص���ҵ��handler�� ��������ΪReadCompletionHandler��
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