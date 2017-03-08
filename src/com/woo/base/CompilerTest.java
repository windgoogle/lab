package com.woo.base;

/**
 * Created by huangfeng on 2016/12/19.
 */

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * 动态编译
 *
 */
public class CompilerTest {

    public static void main(String[] args) throws Exception {
        // 类的文本
        String source = "public class Main { public static void main(String[] args) {System.out.println(\"Hello World! \");} }";

        // 为 JavaFileObject 中的大多数方法提供简单实现。应子类化此类并用作 JavaFileObject 实现的基础。
        // 子类可以重写此类任意方法的实现和规范，只要不违背 JavaFileObject 的常规协定。
        SimpleJavaFileObject sourceObject = new CompilerTest.StringSourceJavaObject(
                "Main", source);

        // 为查找工具提供者提供方法，例如，编译器的提供者。
        // 获取此平台提供的 Java 编程语言编译器。
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        // 为此工具获取一个标准文件管理器实现的新实例。
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(
                null, null, null);

        Iterable<? extends JavaFileObject> fileObjects = Arrays
                .asList(sourceObject);

        // 使用给定组件和参数创建编译任务的 future。
        CompilationTask task = compiler.getTask(null, fileManager, null, null,
                null, fileObjects);
        boolean result = task.call();
        if (result) {
            System.out.println(" 编译成功。");
        }
    }

    /**
     * 主要指定了编译的存储位置和格式
     *
     */
    static class StringSourceJavaObject extends SimpleJavaFileObject {

        private String content = null;

        public StringSourceJavaObject(String name, String content)
                throws URISyntaxException {
            super(URI.create("string:///" + name.replace('.', '/')
                    + Kind.SOURCE.extension), Kind.SOURCE);
            this.content = content;
        }

        public CharSequence getCharContent(boolean ignoreEncodingErrors)
                throws IOException {
            return content;
        }
    }
}
