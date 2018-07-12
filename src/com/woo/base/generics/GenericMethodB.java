package com.woo.base.generics;

public class GenericMethodB {

    /**
     * 泛型方法和可变参数的例子
     * @param args
     * @param <T>
     */
    public <T> void printMsg( T... args){
        for(T t : args){
            System.out.println("泛型测试 t is " + t);
        }
    }

    public static void main(String[] args) {
        GenericMethodB test =new GenericMethodB();
        test.printMsg("111",222,"aaaa","2323.4",55.55);
    }
}
