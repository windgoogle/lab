package com.woo.base.io.nio;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class MappedByteBufferTest {
    public static void main(String[] args) {
        File file = new File("E:\\test\\TW_2019-05-10\\bin\\external.vmoptions");
        long len = file.length();
        byte[] ds = new byte[(int) len];

        try {
            MappedByteBuffer mappedByteBuffer = new RandomAccessFile(file, "rw")
                    .getChannel()
                    .map(FileChannel.MapMode.READ_ONLY, 0, len);
            for (int offset = 0; offset < len; offset++) {
                byte b = mappedByteBuffer.get();
                ds[offset] = b;
            }

            Scanner scan = new Scanner(new ByteArrayInputStream(ds)).useDelimiter("\n");
            while (scan.hasNext()) {
                System.out.print(scan.next()+"\n");
            }

        } catch (IOException e) {
        }
    }
}