package com.woo.base.io.nio2;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ReadCompletionHandler implements CompletionHandler<Integer, ByteBuffer> {
    private AsynchronousSocketChannel channel;

    public ReadCompletionHandler(AsynchronousSocketChannel channel) {
//��AsynchronousSocketChannelͨ���������ݵ�ReadCompletion Handler�е�����Ա������ʹ?
//��Ҫ?�ڶ�ȡ�����Ϣ�ͷ���Ӧ�� �����̲��԰����д��?����˵��
        if (this.channel == null)
            this.channel = channel;
    }

    @
            Override
    public void completed(Integer result, ByteBuffer attachment) {
//��ȡ����Ϣ��Ĵ��� ?�ȶ�attachment��?flip������ Ϊ�����ӻ�������ȡ������׼����
        attachment.flip();
//���ݻ������Ŀɶ��ֽ�������byte����
        byte[] body = new byte[attachment.remaining()];
        attachment.get(body);
        try {
//ͨ��new String?������������Ϣ�� ��������Ϣ��?�жϣ�
//�����"QUERY TIME ORDER"���ȡ��ǰϵͳ��������ʱ�䣬
            String req = new String(body, "UTF-8");
            System.out.println("The time server receive order : " + req);
            String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(req) ? new java.util.Date(
                    System.currentTimeMillis()).toString() : "BAD ORDER";
//��?doWrite?�����͸��ͻ��ˡ�
            doWrite(currentTime);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void doWrite(String currentTime) {
        if (currentTime != null && currentTime.trim().length() > 0) {
//?�ȶԵ�ǰʱ���?�Ϸ���У�飬 ����Ϸ��� ��?�ַ����Ľ���?����Ӧ����Ϣ������ֽ����飬
//Ȼ�������Ƶ����ͻ�����writeBuffer�У�
            byte[] bytes = (currentTime).getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
//����?AsynchronousSocketChannel���첽write?����
//����ǰ?���ܵ��첽read?��?���� ��Ҳ��������read?����ͬ�Ĳ�����
//�ڱ�����������ֱ��ʵ��write?�����첽�ص���?CompletionHandler��
            channel.write(writeBuffer, writeBuffer,
                    new CompletionHandler<Integer, ByteBuffer>() {
                        @Override
                        public void completed(Integer result, ByteBuffer buffer) {
//�Է��͵�writeBuffer��?�жϣ� �������ʣ����ֽڿ�д�� ˵��û�з�����ɣ� ��Ҫ�������ͣ� ֱ�����ͳɹ���
                            if (buffer.hasRemaining())
                                channel.write(buffer, buffer, this);
                        }

                        @Override
                        public void failed(Throwable exc, ByteBuffer attachment) {
//��ע��failed?���� ����ʵ�ֺܼ򵥣� ���ǵ���?�쳣��ʱ�� ���쳣Throwable��?�жϣ�
//�����I/O�쳣�� �͹ر���·�� �ͷ���Դ��
//����������쳣�� ����ҵ��??���߼���?����,���û�з�����ɣ� ��������.
//��������Ϊ��demo�� û�ж��쳣��?�����жϣ� ֻҪ��?�˶�д�쳣�� �͹ر���·�� �ͷ���Դ��
                            try {
                                channel.close();
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
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
