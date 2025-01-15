package com.woo.base.exception;

public class Test {

    public static void main(String[] args) throws Exception {
        try {
            try {
                // 模拟一个可能发生的异常
                if (3 > 2) {
                    throw new Exception("结果大于2的异常");
                }

            }catch(Exception e){
                throw new ArithmeticException("计算错误");
            }finally{
                System.out.println("第一层 finally 块执行。");
            }
        } finally {
            System.out.println("外层 finally 块执行。");
        }
    }
}
