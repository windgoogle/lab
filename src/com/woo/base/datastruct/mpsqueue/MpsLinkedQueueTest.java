package com.woo.base.datastruct.mpsqueue;

public class MpsLinkedQueueTest {


    public static void main(String[] args) throws  Exception {
        MpscLinkedQueue<String> queue=new MpscLinkedQueue<String>();
        int num=2;
        Thread [] producers=new Thread[num];
        long t1=System.currentTimeMillis();
        for (int i=0;i<num;i++) {
            Thread producer=new Thread() {
                @Override
                public void run() {
                    queue.offer("-----" + Thread.currentThread().getName());
                }
            };
            producers[i]=producer;
            producer.start();
        }

        for(Thread producer:producers){
            producer.join();
        }
        long t3=System.currentTimeMillis();
        System.out.println("----queue  :"+queue.headRef());
        System.out.println("----producer cost :"+(t3-t1));
        Thread consumer=new Thread() {
            @Override
            public void run() {
                String value;
                while((value=queue.poll())!=null) {
                    System.out.println(value);
                }
            }
        };
        consumer.start();


        consumer.join();
        long t2=System.currentTimeMillis();
        System.out.println("------cost :"+(t2-t1));

    }
}
