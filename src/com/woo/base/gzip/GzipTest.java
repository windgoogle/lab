package com.woo.base.gzip;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipTest {
    public static void main(String[] args) throws Exception{
        String outfilePath="E:\\test\\daliybuild\\TW_2019-10-30\\deployment\\WebDemo\\index.ht";
        String infilePath="E:\\test\\daliybuild\\TW_2019-10-30\\deployment\\WebDemo\\index.html";
        FileOutputStream fos = new FileOutputStream(outfilePath);
        FileInputStream fis=new FileInputStream(infilePath);
        compress(fis,fos);

    }


    public static void compress(InputStream in , OutputStream out) throws  Exception{
        GZIPOutputStream gos =new GZIPOutputStream(out);

        int count;
        int buffer=4086;
        byte []data =new byte[buffer];
        while((count=in.read(data,0,buffer))!=-1){
            gos.write(data,0,count);
        }

        gos.finish();
        gos.flush();
        gos.close();

    }

    public static void decompress(InputStream in , OutputStream out) throws  Exception{
        GZIPInputStream gis =new GZIPInputStream(in);

        int count;
        int buffer=4086;
        byte []data =new byte[buffer];
        while((count=gis.read(data,0,buffer))!=-1){
            out.write(data,0,count);
        }

        gis.close();

    }
}
