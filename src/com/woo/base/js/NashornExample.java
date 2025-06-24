package com.woo.base.js;

// Java 代码示例：使用 Nashorn 执行 JavaScript
import javax.script.*;

public class NashornExample {
    public static void main(String[] args) throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");

        // 执行 JavaScript 代码
        engine.eval("var msg = 'Hello from Nashorn!'; print(msg);");

        // 调用 JavaScript 函数
        engine.eval("function add(a, b) { return a + b; }");
        Invocable invocable = (Invocable) engine;
        Object result = invocable.invokeFunction("add", 3, 4);
        System.out.println("Result: " + result);
    }
}