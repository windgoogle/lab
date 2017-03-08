package com.woo.base.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ScatteringByteChannel;

/**
 * Created by huangfeng on 2017/1/11.
 * 从两个缓存区读取数据保存到文件中
 * 将文件里的数据读取到缓冲区里
 */
public class GatherChannelDemo {

    public static void main(String[] args) throws Exception{
        ByteBuffer header = ByteBuffer.allocateDirect (10);
        ByteBuffer body = ByteBuffer.allocateDirect (80);
        ByteBuffer [] buffers={ header, body };

        header.put((byte)'a').put((byte)'a').put((byte)'a').put((byte)'a');
        body.put((byte)'b').put((byte)'c').put((byte)'d').put((byte)'e');
        header.flip();
        body.flip();
        FileChannel channel= new FileOutputStream("test.txt").getChannel();

        long bytesRead = channel.write(buffers);
        System.out.println(bytesRead);
        channel.close();

        ByteBuffer header1 = ByteBuffer.allocateDirect (10);
        ByteBuffer body1 = ByteBuffer.allocateDirect (80);
        ByteBuffer [] buffers1={ header1, body1 };
        FileChannel channel1= new FileInputStream("test.txt").getChannel();
        channel1.read(buffers1);
        System.out.println("header1 : " + header1 );
        System.out.println("body1 : " + body1 );
    }
}
