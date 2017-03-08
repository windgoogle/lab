package com.woo.base.concurrency;

/**
 * Created by huangfeng on 2017/2/24.
 */
public class StopThreadUnsafe {

    public static User u=new User();

    public static class User {
        private int id;
        private String name;
        public User () {
            this.id=0;
            this.name="0";

        }

        @Override
        public String toString() {
            return "User [id=" +id+", name="+name+"]";
        }

        public void setId(int id) {
            this.id=id;
        }

        public int getId(){
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name=name;
        }

    }


    public static class ChangeObjectThread extends Thread{

        @Override
        public void run () {
           while(true) {
               synchronized (u) {
                   int v=(int)(System.currentTimeMillis()/1000);
                   u.setId(v);

                   //do something else
                   try{
                     Thread.sleep(100);
                   }catch (InterruptedException ex){
                       ex.printStackTrace();
                   }

                   u.setName(String.valueOf(v));
                 // System.out.println(u.getName());
               }

               Thread.yield();
           }
        }
    }

    public static class ReadObjectThread extends Thread{

        @Override
        public void run () {
            while(true) {
                synchronized (u) {
                   if(u.getId()!=Integer.parseInt(u.getName())){
                        System.out.println(u);
                   }
                }

                Thread.yield();
            }
        }
    }




    public static void main(String[] args) throws InterruptedException {
      new ReadObjectThread().start();
        while (true) {
            Thread t=new ChangeObjectThread();
            t.start();
            Thread.sleep(150);
            //t.interrupt();
            t.stop();     //不要用stop()

        }
    }
}
