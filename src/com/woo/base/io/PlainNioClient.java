package com.woo.base.io;

import java.net.Socket;

/**
 * Created by huangfeng on 2017/1/13.
 */
public class PlainNioClient {

    public static void main(String[] args) throws Exception {
        Socket s=new Socket("168.1.2.25",8118);
        System.out.println(s.isConnected());

    }
}
