package com.woo.base.generics;

/**
 *
 * 泛型类的例子
 * */
//此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型
//在实例化泛型类时，必须指定T的具体类型
public class Generic<T>{
    //key这个成员变量的类型为T,T的类型由外部指定
    private T key;

    public Generic(T key) { //泛型构造方法形参key的类型也为T，T的类型由外部指定
        this.key = key;
    }

    public T getKey(){ //泛型方法getKey的返回值类型为T，T的类型由外部指定
        return key;
    }

    public static void showKeyValue(Generic<Number> obj){
        System.out.println("泛型测试 key value is " + obj.getKey());
    }
    //通配符 ？ 是类型实参，不是形参
    public static void showKeyValue1(Generic<?> obj){
        System.out.println("泛型测试 key value is " + obj.getKey());
    }

    /**
     * 泛型上下边界
     * 在使用泛型的时候，我们还可以为传入的泛型类型实参进行上下边界的限制，如：类型实参只准传入某种类型的父类或某种类型的子类。
     *
     */

    public void showKeyValue3(Generic<? extends Number> obj){
        System.out.println("泛型测试 key value is " + obj.getKey());
    }


    public static void main(String[] args) throws  Exception {
        //泛型的类型参数只能是类类型（包括自定义类），不能是简单类型
        //传入的实参类型需与泛型的类型参数类型相同，即为Integer.
        Generic<Integer> genericInteger = new Generic<Integer>(123456);

        //传入的实参类型需与泛型的类型参数类型相同，即为String.
        Generic<String> genericString = new Generic<String>("key_vlaue");
        System.out.println("泛型测试 key is " + genericInteger.getKey());
        System.out.println("泛型测试 key is " + genericString.getKey());

        //传入的实参类型为任何类型，自动识别类型
        Generic generic = new Generic("111111");
        Generic generic1 = new Generic(4444);
        Generic generic2 = new Generic(55.55);
        Generic generic3 = new Generic(false);

        System.out.println("泛型测试 key is " + generic.getKey());
        System.out.println("泛型测试 key is " + generic1.getKey());
        System.out.println("泛型测试 key is " + generic2.getKey());
        System.out.println("泛型测试 key is " + generic3.getKey());


        Generic<Integer> gInteger = new Generic<Integer>(123);
        Generic<Number> gNumber = new Generic<Number>(456);

        //showKeyValue(gInteger);    报错

        // showKeyValue这个方法编译器会为我们报错：Generic<java.lang.Integer>
        // cannot be applied to Generic<java.lang.Number>
        // showKeyValue(gInteger);


        showKeyValue1(gNumber);

    }

    /**
     * 泛型的类型参数只能是类类型，不能是简单类型。
     *
     * 不能对确切的泛型类型使用instanceof操作。如下面的操作是非法的，编译时会出错。
     */
}