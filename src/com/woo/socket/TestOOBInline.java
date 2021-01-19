package com.woo.socket;


import java.io.*;
import java.net.*;

/**
 * TCP 紧急指针
 *
 * 结果是server收到数据ABCHellWorld 中国
 * 原因是AB立即发送，C和HelloWorld flush()才发送
 */
public class TestOOBInline {


    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket socket = new Socket("127.0.0.1", 1234);
        socket.setOOBInline(true);
        OutputStream out = socket.getOutputStream();
        OutputStreamWriter outWriter = new OutputStreamWriter(out);
        outWriter.write(67); // 向服务器发送字符"C"
        outWriter.write("hello world\r\n");
        socket.sendUrgentData(65); // 向服务器发送字符"A"
        socket.sendUrgentData(322); // 向服务器发送字符"B"
        outWriter.flush();
        socket.sendUrgentData(214); // 向服务器发送汉字”中”
        socket.sendUrgentData(208);
        socket.sendUrgentData(185); // 向服务器发送汉字”国”
        socket.sendUrgentData(250);
        socket.close();
    }
}

class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("服务器已经启动，端口号：1234");
        while (true) {
            Socket socket = serverSocket.accept();
            socket.setOOBInline(true);
            InputStream in = socket.getInputStream();
            InputStreamReader inReader = new InputStreamReader(in);
            BufferedReader bReader = new BufferedReader(inReader);
            System.out.println(bReader.readLine());
            System.out.println(bReader.readLine());
            socket.close();
        }
    }
}