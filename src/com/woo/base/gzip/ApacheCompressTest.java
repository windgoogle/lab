package com.woo.base.gzip;

import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipParameters;

import java.io.*;

public class ApacheCompressTest {

    public static void main(String[] args) throws Exception{
        String source="E:\\gitlab\\POC_ITAID-288\\tw-release\\target\\TW_2020-06-04\\deployment\\basetest\\10k\\index.html";
        FileInputStream in=new FileInputStream(source);
        int buffersize = 1024;
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        final byte[] buffer = new byte[buffersize];
        int n=0;
        while (-1 != (n = in.read(buffer))) {
            out.write(buffer, 0, n);
        }

        byte[] srcBytes=out.toByteArray();
        System.out.println("                   gzip compress                               ");
        System.out.println("--------src :"+srcBytes.length);
        for(int i=-1;i<10;i++){
            long t1=System.currentTimeMillis();
            byte[] compressBytes= gzipCompress(srcBytes,i);
            long t2=System.currentTimeMillis();

            System.out.println("                                                  ");
            System.out.println("--------compress level : "+i);
            System.out.println("---------------compress size:"+compressBytes.length);
            System.out.println("---------------compress cost time :"+(t2-t1)+" ms");

        }

    }

    public static byte[] gzipCompress(byte[] source,int compressLevel) throws Exception {
        if(source==null)
            return null;
        GzipParameters gzipParameters=new GzipParameters();
        gzipParameters.setCompressionLevel(compressLevel);
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        ByteArrayInputStream in=new ByteArrayInputStream(source);
        GzipCompressorOutputStream gzOut = new GzipCompressorOutputStream(out,gzipParameters);
        int buffersize = 1024;
        final byte[] buffer = new byte[buffersize];
        int n = 0;
        while (-1 != (n = in.read(buffer))) {
            gzOut.write(buffer, 0, n);
        }
        gzOut.close();
        in.close();
        return out.toByteArray();
    }



}
