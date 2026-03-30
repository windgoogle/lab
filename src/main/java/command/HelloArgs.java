package command;

import com.beust.jcommander.Parameter;

public class HelloArgs {

    // 定义一个必选参数，长格式为 --name，短格式为 -n
    @Parameter(names = {"--name", "-n"}, required = true, description = "你的名字")
    private String name;

    // 定义一个可选参数，有默认值，长格式为 --age
    @Parameter(names = "--age", description = "你的年龄")
    private int age;

    // 定义一个布尔值的开关参数，长格式为 --verbose，短格式为 -v
    @Parameter(names = {"--verbose", "-v"}, description = "开启详细输出模式")
    private boolean verbose;

    // Getter 和 Setter 方法
    public String getName() { return name; }
    public int getAge() { return age; }
    public boolean isVerbose() { return verbose; }
}