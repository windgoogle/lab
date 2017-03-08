package com.woo.base;
import java.util.*;

/**
 * Created by huangfeng on 2016/12/9.
 */
public class BitJava {

    public static Map<String ,String>  hmp=new HashMap<String,String>(){
        {
            put("sdsd","ssdsdsd");
            put("haha","this is  a text !");
        }
    };

    public static void main (String args[]) throws Exception {
      //test1();
    }



    public static void test2 () {


    }


    public static  void test1() {
        int init = -10;
        int initPositive = 10 - 1;

        // 记录负的int值的长度
        final int theLength = Integer.toBinaryString(init).length();
        System.out.println("-10的二进制表示长度是：" + theLength);
        for (int i = 0; i < 10; i++) {
            System.out.println(init + "的二进制表示：" + Integer.toBinaryString(init));
            // 临时字符串，保存整数int值的二进制表现形式
            String temp = Integer.toBinaryString(initPositive);
            // 需要补的位数
            int complement = theLength - temp.length();
            StringBuffer complementStr = new StringBuffer();
            for (int j = 0; j < complement; j++) {
                // 用“0”补足
                complementStr.append("0");
            }
            System.out.println(initPositive + "的二进制表示：    " + complementStr
                    + temp);
            init++;
            initPositive--;
        }

        System.out.println("---------------------");
        String longBinaryStr = Long.toBinaryString(-5L);
        System.out.println("long类型(-5L)：" + longBinaryStr);
        System.out.println("长度是：" + longBinaryStr.length());

    }

}
