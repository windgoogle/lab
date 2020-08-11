package com.woo.base.Serializable;

import java.io.*;

public class Test {

    public static void main(String args[]) throws FileNotFoundException, IOException, ClassNotFoundException {
       /*
        User user = new User("小明");
        user.setAge("22");
        user.setName("小明");
        user.setPassword("admin");
        System.out.println(user.getAge() + "\t" + user.getName() + "\t" + user.getPassword());
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("user.txt"));
        oos.writeObject(user);
        oos.flush();
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("user.txt"));
        User users = (User) ois.readObject();

        System.out.println(users.getAge() + "\t" + users.getName() + "\t" + users.getPassword());

        */
       String url="/api/asdo/sdfsdf.js";
       int index=url.toLowerCase().lastIndexOf(".");
       String suffix="";
       if(index>-1){
           suffix=url.substring(index+1);
       }
        System.out.println("-----"+suffix);
   }

}
