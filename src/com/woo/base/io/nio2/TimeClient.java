package com.woo.base.io.nio2;

public class TimeClient {
    public static void main(String[] args) {
        int port = 8080;
//ͨ��?����?��I/O�̴߳����첽ʱ��������ͻ���handler��
//��ʵ����?�У� ���ǲ���Ҫ��?���̴߳����첽���Ӷ��� ��Ϊ�ײ㶼��ͨ��JDK��ϵͳ�ص�ʵ�ֵ�.
        new Thread(new AsyncTimeClientHandler("127.0.0.1", port), "AIO-AsyncTimeClientHandler-001").start();
    }
}