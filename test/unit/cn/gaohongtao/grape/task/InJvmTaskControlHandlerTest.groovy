package cn.gaohongtao.grape.task

import cn.gaohongtao.grape.common.task.TaskHandlerContext
import cn.gaohongtao.grape.common.task.TaskPipeline
import cn.gaohongtao.grape.common.task.TaskRunnable
import com.google.gson.Gson

import static org.junit.Assert.*;
import org.junit.Before
import org.junit.Test

/**
 * 测试缓存处理器
 *
 * Created by gaohongtao on 14-3-24.
 */
class InJvmTaskControlHandlerTest {

    TaskPipeline pipeline = new TaskPipeline();

    @Before
    void init() {
        pipeline << new TaskHandlerContext(new InJvmTaskControlHandler())
        pipeline.fireRegister("test", null, new TaskRunnable(
                """if(context)
    context.put("result","ok");"""));
    }

    @Test
    void addTask() {
        def job = pipeline.fireGet("test")
        assertNotNull(job)
        assertEquals """{"name":"test","state":"Inactive","runnable":{"content":"if(context)\\n    context.put(\\"result\\",\\"ok\\");"}}""", job
    }

    @Test
    void stateChange() {
        pipeline.fireActive("test", null);
        def job = pipeline.fireGet("test")
        assertEquals """{"name":"test","state":"Active","runnable":{"content":"if(context)\\n    context.put(\\"result\\",\\"ok\\");"}}""", job

        Map<String, String> runnableContext = new HashMap<>(1);
        runnableContext.put("11", "11");
        pipeline.fireRun("test", runnableContext);
        job = pipeline.fireGet("test")
        assertEquals """{"name":"test","state":"Active","runnable":{"content":"if(context)\\n    context.put(\\"result\\",\\"ok\\");"}}""", job
        assertEquals("ok", runnableContext.get("result"))

        pipeline.fireInactive("test");
        pipeline.fireRemove("test");
        assertNull(pipeline.fireGet("test"));
    }
}
