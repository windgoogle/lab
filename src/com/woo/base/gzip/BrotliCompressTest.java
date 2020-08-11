package com.woo.base.gzip;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.meteogroup.jbrotli.Brotli;
import org.meteogroup.jbrotli.BrotliCompressor;
import org.meteogroup.jbrotli.BrotliDeCompressor;
import org.meteogroup.jbrotli.libloader.BrotliLibraryLoader;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class BrotliCompressTest {

    private  static int deCompressBufSize;
    //private static final String source="E:\\gitlab\\POC_ITAID-288\\tw-release\\target\\TW_2020-06-04\\deployment\\basetest\\10k\\index.html";
    private static final String source="C:\\Users\\hzy20\\Desktop\\1\\static500k.html";
    public static void main(String[] args) throws Exception {
        //String source="E:\\gitlab\\POC_ITAID-288\\tw-release\\target\\TW_2020-06-04\\deployment\\basetest\\10k\\index.html";
        BrotliLibraryLoader.loadBrotli();

        byte[] inBuf = "Brotli: a new compression algorithm for the internet. Now available for Java!".getBytes();
        inBuf=readSource(new FileInputStream(source));

        deCompressBufSize=inBuf.length;

        System.out.println("brotli :    ");
        long t1=System.currentTimeMillis();
        byte[] compressedBuf = brotliCompress(inBuf);
        long t2=System.currentTimeMillis();
        System.out.println("----brotli outLength : "+compressedBuf.length+" ,ratio : "+ratio(compressedBuf.length,deCompressBufSize)+"， cost : "+(t2-t1) +" ms .");

        byte[] decompressedBuf =brotliDecompress(compressedBuf);
        System.out.println("--------after brotli  decompress : "+new String(decompressedBuf));

        System.out.println("gzip :    ");
        long t3=System.currentTimeMillis();
        byte[] gzipCompressedBuf=gzipCompress(inBuf);
        long t4=System.currentTimeMillis();
        System.out.println("---------gzip outLength : "+gzipCompressedBuf.length+" , ratio :"+ ratio(gzipCompressedBuf.length,deCompressBufSize)+", cost : "+(t4-t3) +"ms .");

        byte[] gzipDecompressedBuf =gzipDecompress(gzipCompressedBuf);

        System.out.println("-----------after gzip decompress : "+new String(gzipDecompressedBuf));

    }

    public static String ratio(int comressDateLength,int originalDataLength){
        String result="";//接受百分比的值
        double x_double=comressDateLength*1.0;
        double y_double=originalDataLength*1.0;
        double tempresult=(y_double-x_double)/y_double;
        NumberFormat nf   =   NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits( 2 );
        //DecimalFormat df1 = new DecimalFormat("0.00%");    //##.00%   百分比格式，后面不足2位的用0补齐
        //result=nf.format(tempresult);
        result= nf.format(tempresult);
        return result;
    }

    public static byte[] readSource(InputStream in) throws Exception{
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        int count;
        byte [] data=new byte[2048];
        while((count=in.read(data,0,2048))!=-1) {
            bos.write(data,0,count);
        }
        byte []inBuf=bos.toByteArray();
        in.close();
        return inBuf;
    }


    public static byte[] brotliCompress(byte[] in) throws Exception {
        BrotliCompressor compressor = new BrotliCompressor();
        byte[] data = new byte[in.length];
        Brotli.Parameter parameter=new Brotli.Parameter(Brotli.DEFAULT_MODE, 5, 22, 0);
        int outLength=compressor.compress(parameter,in,data);
        byte[] compressedBuf=new byte[outLength];
        System.arraycopy(data,0,compressedBuf,0,outLength);
        brotliToFile(compressedBuf,source);
        return compressedBuf;
    }

    public static void brotliToFile(byte[] in,String path) throws Exception {
        String brFile=path.substring(0,path.lastIndexOf("."))+".br";
        FileOutputStream out=new FileOutputStream(brFile);
        int n=0;
        InputStream is = new ByteArrayInputStream(in);
        byte[] buff = new byte[2048];
        int len = 0;
        while((len=is.read(buff))!=-1){
            out.write(buff, 0, len);
        }
        is.close();
        out.close();
    }


    public static byte[] brotliDecompress(byte[] compressedBuf) throws  Exception{
        BrotliDeCompressor deCompressor=new BrotliDeCompressor();
        byte[] decompressedBuf = new byte[deCompressBufSize];
        deCompressor.deCompress(compressedBuf,decompressedBuf);
        return decompressedBuf;
    }

    public static byte[] gzipCompress(byte[] in) throws Exception{
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        GzipCompressorOutputStream gout=new GzipCompressorOutputStream(baos);
        gout.write(in,0,in.length);
        gout.finish();
        gout.flush();
        gout.close();

        return baos.toByteArray();
    }

    public static byte[] gzipDecompress(byte [] gzipCompressedBuf) throws  Exception {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        ByteArrayInputStream bais=new ByteArrayInputStream(gzipCompressedBuf);
        GzipCompressorInputStream gins =new GzipCompressorInputStream(bais);
        int count;
        byte [] data=new byte[deCompressBufSize];
        while ((count=gins.read(data,0,deCompressBufSize))!=-1){
            baos.write(data,0,count);
        }
        gins.close();
        return baos.toByteArray();
    }

}
