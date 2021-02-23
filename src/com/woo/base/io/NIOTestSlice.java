package com.woo.base.io;

import java.nio.ByteBuffer;

public class NIOTestSlice {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        int capacity = byteBuffer.capacity();
        for (int i = 0 ; i < capacity; i++) {
            byteBuffer.put((byte)i);
        }

        // 设置position、limit
        byteBuffer.position(2);
        byteBuffer.limit(6);

        //slice方法是前闭后开的 大于等于position，小于limit
        ByteBuffer subByteBuffer = byteBuffer.slice();

        // 改变subByteBuffer内容，也能反映到byteBuffer上
        for (int j = 0; j < subByteBuffer.capacity(); j++) {
            // 2到5位置的数*2
            subByteBuffer.put((byte)(2 * subByteBuffer.get(j)));
        }

        // 设置回原来的值，打印输出看下byteBuffer数据变化
        byteBuffer.position(0);
        byteBuffer.limit(capacity);
        while (byteBuffer.hasRemaining()) {
            System.out.println(byteBuffer.get());
        }
    }
}