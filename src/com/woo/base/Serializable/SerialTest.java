package com.woo.base.Serializable;

import java.io.*;

public class SerialTest extends Parent implements Serializable {

    private static final long serialVersionUID = 1L;
    public static int getAnInt() {
        return anInt;
    }

    public static void setAnInt(int anInt) {
        SerialTest.anInt = anInt;
    }

    private static int anInt=10;

    int version = 66;

    Contain con = new Contain();



    public int getVersion() {

        return version;

    }

    public static void main(String args[]) throws IOException {

        FileOutputStream fos = new FileOutputStream("temp.out");

        ObjectOutputStream oos = new ObjectOutputStream(fos);

        SerialTest st = new SerialTest();


        oos.writeObject(st);

        oos.flush();

        oos.close();

        st.setAnInt(102);

        FileInputStream fin=new FileInputStream("temp.out");
        ObjectInputStream ois=new ObjectInputStream(fin);

        try {
            SerialTest serialTest=(SerialTest) ois.readObject();
            System.out.println(serialTest.getAnInt());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

}