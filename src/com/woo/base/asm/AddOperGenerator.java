package com.woo.base.asm;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.FileOutputStream;

/**
 * Created by huangfeng on 2016/12/20.
 * 生成接口AddOper
 * public interface AddOper extends Oper {
     public static final String SYMBOL = "+";

     public int add(int a, int b);
 }

 */
public class AddOperGenerator implements Opcodes{
    public static void main (String args[]) throws Exception {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_5, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE, "com/woo/base/asm/AddOper", null, "java/lang/Object", new String[]{"com/woo/base/asm/Oper"});
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "SYMBOL", "Ljava/lang/String;",null, "+").visitEnd();
        cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "add", "(II)I", null, null).visitEnd();
        cw.visitEnd();
        FileOutputStream fos = new FileOutputStream("AddOper.class");
        fos.write(cw.toByteArray());
        fos.close();

    }


}
