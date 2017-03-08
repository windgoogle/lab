package com.woo.base.io;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;


/**
 * Created by huangfeng on 2017/1/11.
 */
public  class BufferDemo {

    public static void main(String[] args) {
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
        char [] cArray=new char[100];

        cArray[0]='l';

        cArray[1]='o';

        CharBuffer charBuffer=CharBuffer.wrap(cArray,3,30);


        charBuffer.put('a').put('b').put('c').put('d').put('e').put('f').put('g');

        System.out.println(cArray);
        System.out.println("has array : "+ charBuffer.hasArray());

        CharBuffer charBuffer1=CharBuffer.wrap("this is a char array buffer !");

        CharBuffer dupBuf=charBuffer.duplicate();

        System.out.println("duplicate buffer: "+dupBuf);

        System.out.println("char buffer byte order : "+charBuffer.order());

        System.out.println("byte buffer byte order : "+byteBuffer.order());


    }
}
