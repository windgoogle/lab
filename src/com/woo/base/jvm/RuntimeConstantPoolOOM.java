package com.woo.base.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangfeng on 2016/12/30.
 */
public class RuntimeConstantPoolOOM {

    /**
     * JDK 1.6 perm space out of memory
     * JDK1.7 + heap space out of memory
     * @param args
     * @throws Exception
     */

    public static void main (String args[]) throws Exception {
        List<String> list=new ArrayList<String>();
        int i=0;
        while(true) {
            list.add(String.valueOf(i++).intern());  //jdk1.7+ 常量池存的是字符串引用,JDK1.6 存的是字符串本身
        }
    }
}
