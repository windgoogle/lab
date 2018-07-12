package com.woo.base.io.nio2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestNio {


    public static void main(String args[]) throws Exception{

        //String infile = "D:\\workspace\\test\\usagetracking.log";
        //FileInputStream fin= new FileInputStream(infile);
        //FileChannel fcin = fin.getChannel();\\

        int bufSize = 1024;
        File fin = new File("E:\\svn\\twnt\\products\\apache-tomee\\target\\tongweb-webprofile-1.5.0\\logs\\access\\access_log.server.18.05.02.13.txt");
        File fout = new File("E:\\svn\\twnt\\products\\apache-tomee\\target\\tongweb-webprofile-1.5.0\\logs\\test.log");

        FileChannel fcin = new RandomAccessFile(fin, "r").getChannel();
        ByteBuffer rBuffer = ByteBuffer.allocate(bufSize);

        FileChannel fcout = new RandomAccessFile(fout, "rws").getChannel();
        ByteBuffer wBuffer = ByteBuffer.allocateDirect(bufSize);


        readFileByLine(bufSize, fcin, rBuffer, fcout, wBuffer);

        System.out.print("OK!!!");
    }

    public static void readFileByLine(int bufSize, FileChannel fcin, ByteBuffer rBuffer, FileChannel fcout, ByteBuffer wBuffer){
        String enterStr = "\n";
        try{
            byte[] bs = new byte[bufSize];

            int size = 0;
            StringBuffer strBuf = new StringBuffer("");
            //while((size = fcin.read(buffer)) != -1){
            while(fcin.read(rBuffer) != -1){
                int rSize = rBuffer.position();
                rBuffer.rewind();
                rBuffer.get(bs);
                rBuffer.clear();
                String tempString = new String(bs, 0, rSize);
                System.out.print(tempString);
                //System.out.print("<200>");

                int fromIndex = 0;
                int endIndex = 0;
                while((endIndex = tempString.indexOf(enterStr, fromIndex)) != -1){
                    String line = tempString.substring(fromIndex, endIndex);
                    line = new String(strBuf.toString() + line);
                    //System.out.print(line);
                    //System.out.print("</over/>");
                    //write to anthone file
                    writeFileByLine(fcout, wBuffer, line);


                    strBuf.delete(0, strBuf.length());
                    fromIndex = endIndex + 1;
                }
                if(rSize > tempString.length()){
                    strBuf.append(tempString.substring(fromIndex, tempString.length()));
                }else{
                    strBuf.append(tempString.substring(fromIndex, rSize));
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void writeFileByLine(FileChannel fcout, ByteBuffer wBuffer, String line){
        try {
            //write on file head
            //fcout.write(wBuffer.wrap(line.getBytes()));
            //wirte append file on foot
            fcout.write(wBuffer.wrap(line.getBytes()), fcout.size());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}