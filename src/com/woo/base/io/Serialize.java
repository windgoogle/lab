package com.woo.base.io;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by huangfeng on 2017/1/10.
 */
public class Serialize implements Serializable{
    private final static long serialVersionUID=-1;
    public int num=1390;

    public static void main(String[] args) {
        try{
            FileOutputStream fos=new FileOutputStream("serialize.dat");
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            Serialize serialize =new Serialize();
            oos.writeObject(serialize);
            oos.flush();
            oos.close();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }


}
