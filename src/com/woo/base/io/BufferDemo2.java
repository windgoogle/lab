package com.woo.base.io;

import java.nio.ByteBuffer;

public class BufferDemo2 {
    public static void main(String[] args) throws Exception{
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);


        byteBuffer.put((byte)'a').put((byte)'b').put((byte)'c').put((byte)'d').put((byte)'e').put((byte)'f').put((byte)'g');
        System.out.println("========================before flip()=====================");
        System.out.println("limit="+byteBuffer.limit());
        System.out.println("position="+byteBuffer.position());
        byteBuffer.flip();

        //byteBuffer.flip();
        //byteBuffer.put((byte)'h');    //BufferOverflowExceptoin
        System.out.println("========================after flip()=====================");
        System.out.println("limit="+byteBuffer.limit());
        System.out.println("position="+byteBuffer.position());
        System.out.println("has array : "+ byteBuffer.hasArray());

        System.out.println("get  : "+ byteBuffer.get());

        System.out.println("========================after get( )=====================");
        System.out.println("limit="+byteBuffer.limit());
        System.out.println("position="+byteBuffer.position());

        byte [] dst=new byte[5];
        System.out.println("get and put into array  : "+ byteBuffer.get(dst,2,2));

        System.out.println("========================after get( byte array)=====================");
        System.out.println("limit="+byteBuffer.limit());
        System.out.println("position="+byteBuffer.position());
        System.out.println("byte array : "+ dst.length);
        System.out.println("buffer remaining: "+ byteBuffer.remaining());

        System.out.println("========================after buffer clear() =====================");
        byteBuffer.clear();
        System.out.println("limit="+byteBuffer.limit());
        System.out.println("position="+byteBuffer.position());
        System.out.println("byte array : "+ dst.length);
        System.out.println("buffer remaining: "+ byteBuffer.remaining());
        //flip() 和clear() 用于读和写的切换，flip()调用，可以读buffer了，clear后可以写buffer了

    }
}
