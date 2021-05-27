package com.woo.security;

import java.io.*;
import javax.crypto.*;
public class Skey_DES
{
    public static void main(String args[]) throws Exception {
        KeyGenerator kg=KeyGenerator.getInstance("DESede");
            kg.init(168); 
            SecretKey k=kg.generateKey( );
            FileOutputStream  f=new FileOutputStream("key1.dat");
	   ObjectOutputStream b=new  ObjectOutputStream(f);
	        b.writeObject(k);
         }
}
