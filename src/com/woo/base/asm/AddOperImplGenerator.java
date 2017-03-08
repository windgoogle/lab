package com.woo.base.asm;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Created by huangfeng on 2016/12/20.
 * 实现方法
 */
public class AddOperImplGenerator implements Opcodes {

    public static void main(String[] args) throws Exception {
        ClassWriter cw = new ClassWriter(0);
        PrintWriter printWriter = new PrintWriter(System.out);
        TraceClassVisitor visitor = new TraceClassVisitor(cw, printWriter);

        visitor.visit(V1_5, ACC_PUBLIC, "com/woo/base/asm/AddOperImpl", null, "java/lang/Object", new String[]{"com/woo/base/asm/AddOper"});

        //添加构造方法
        MethodVisitor mv = visitor.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mv.visitCode();
        mv.visitVarInsn(ALOAD, 0);
        mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        mv.visitInsn(RETURN);
        mv.visitMaxs(1, 1);
        mv.visitEnd();

        // 添加add方法
        mv = visitor.visitMethod(ACC_PUBLIC, "add", "(II)I", null, null);
        mv.visitCode();
        mv.visitVarInsn(ILOAD, 1);
        mv.visitVarInsn(ILOAD, 2);
        mv.visitInsn(IADD);
        mv.visitInsn(IRETURN);
        mv.visitMaxs(2, 3);
        mv.visitEnd();

        visitor.visitEnd();

        FileOutputStream fos = new FileOutputStream("AddOperImpl.class");
        fos.write(cw.toByteArray());
        fos.close();
    }
}
