package com.woo.base.concurrency.forkjoin;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

public class ForkJoinDemo {

    public static void main(String[] args) throws Exception {
        //创建一个支持分解任务的线程池ForkJoinPool
        ForkJoinPool pool = new ForkJoinPool();
        MyTask task = new MyTask(178);

        pool.submit(task);
        pool.awaitTermination(20, TimeUnit.SECONDS);//等待20s，观察结果
        pool.shutdown();
    }

    /**
     * 定义一个可分解的的任务类，继承了RecursiveAction抽象类
     * 必须实现它的compute方法
     */
    public static class MyTask extends RecursiveAction {

        private static final long serialVersionUID = 1L;
        //定义一个分解任务的阈值——50,即一个任务最多承担50个工作量
        int THRESHOLD = 50;
        //任务量
        int task_Num = 0;

        MyTask(int Num) {
            this.task_Num = Num;
        }

        @Override
        protected void compute() {
            if (task_Num <= THRESHOLD) {
                System.out.println(Thread.currentThread().getName() + "承担了" + task_Num + "份工作");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                //随机解成两个任务
                Random m = new Random();
                int x = m.nextInt(50);

                MyTask left = new MyTask(x);
                MyTask right = new MyTask(task_Num - x);

                left.fork();
                right.fork();
            }
        }
    }


}
