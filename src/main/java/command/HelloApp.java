package command;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

public class HelloApp {

    public static void main(String[] args) {
        // 1. 创建参数模型对象
        HelloArgs helloArgs = new HelloArgs();

        // 2. 创建 JCommander 实例，并将参数模型对象与之关联
        JCommander jCommander = JCommander.newBuilder()
                .addObject(helloArgs)
                .programName("HelloApp") // 设置程序名称，用于帮助信息
                .build();

        try {
            // 3. 解析命令行参数
            jCommander.parse(args);

            // 4. 业务逻辑处理
            if (helloArgs.isVerbose()) {
                System.out.println("【详细模式】: 开始执行打招呼程序...");
                System.out.println("【详细模式】: 接收到的名字: " + helloArgs.getName());
                System.out.println("【详细模式】: 接收到的年龄: " + helloArgs.getAge());
            }

            System.out.println("Hello, " + helloArgs.getName() + "! You are " + helloArgs.getAge() + " years old.");

        } catch (ParameterException e) {
            // 5. 处理参数错误（如缺少必填项、类型错误等）
            System.err.println("参数错误: " + e.getMessage());
            System.err.println("请使用 --help 查看用法。");
            // 打印帮助信息
            jCommander.usage();
        }
    }
}