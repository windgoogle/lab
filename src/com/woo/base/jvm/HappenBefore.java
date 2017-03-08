package com.woo.base.jvm;

/**
 * @Described：Happen-Before测试
 * @author YHJ create at 2013-4-13 下午05:12:36
 * @ClassNmae com.yhj.concurrent.HapenBefore
 */
public class HappenBefore {

    static int x,y,m,n;//测试用的信号变量

    public static void main(String[] args) throws InterruptedException {
        int count = 10000;
        for(int i=0;i<count;++i){
            x=y=m=n=0;
            //线程一
            Thread one = new Thread(){
                public void run() {
                    m=1;
                    x=n;
                };
            };
            //线程二
            Thread two = new Thread(){
                public void run() {
                    n=1;
                    y=m;
                };
            };
            //启动并等待线程执行结束
            one.start();
            two.start();
            one.join();
            two.join();
            //输出结果
            System.out.println("index:"+i+" {x:"+x+",y:"+y+"}");
        }
    }
}