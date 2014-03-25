package cn.gaohongtao.grape.common.task

import com.google.gson.annotations.Expose

/**
 * 脚本运行器
 * Created by gaohongtao on 14-3-24.
 */
class TaskRunnable {

    @Expose
    private final String content;

    private Script script;

    TaskRunnable(String content) {
        this.content = content;
    }

    String getContent() {
        return content
    }

    void run(def context) {
        if (!script) {
            GroovyShell shell = new GroovyShell();
            script = shell.parse(this.content);
        }
        Binding binding = new Binding();
        binding.setVariable("context", context);
        script.setBinding(binding);
        script.run();
    }
}
