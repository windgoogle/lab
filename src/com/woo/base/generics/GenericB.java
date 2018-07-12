package com.woo.base.generics;


/**
 * 泛型边界的另一个例子
 * @param <T>
 */
public class GenericB<T extends Number>{
    private T key;

    public GenericB(T key) {
        this.key = key;
    }

    public T getKey(){
        return key;
    }


    //? 是实参，如果改成T ，静态方法必须申明为泛型方法即加上<T> : public static <T> void showKeyValue1<T obj>
    public static void showKeyValue1(Generic<? extends Number> obj){
        System.out.println("泛型测试 key value is " + obj.getKey());
    }

    //在泛型方法中添加上下边界限制的时候，必须在权限声明与返回值之间的<T>上添加上下边界，即在泛型声明的时候添加
//public <T> T showKeyName(Generic<T extends Number> container)，编译器会报错："Unexpected bound"
    public <T extends Number> T showKeyName(GenericB <T> container){
        System.out.println("container key :" + container.getKey());
        T test = container.getKey();
        return test;
    }

    public static void main(String[] args) {
        Generic<String> generic1 = new Generic<String>("11111");
        Generic<Integer> generic2 = new Generic<Integer>(2222);
        Generic<Float> generic3 = new Generic<Float>(2.4f);
        Generic<Double> generic4 = new Generic<Double>(2.56);

//这一行代码编译器会提示错误，因为String类型并不是Number类型的子类
//showKeyValue1(generic1);

        showKeyValue1(generic2);
        showKeyValue1(generic3);
        showKeyValue1(generic4);
    }
}