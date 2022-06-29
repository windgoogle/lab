package com.woo.base.io.nio2;

import java.io.IOException;

public class TimeServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
// ��?Ĭ��ֵ
            }
        }
        //?�ȴ����첽��ʱ������������࣬ Ȼ�������߳̽�AsyncTimeServerHandler������
        AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(port);
        new Thread(timeServer, "AIO-AsyncTimeServerHandler-001").start();
    }
}