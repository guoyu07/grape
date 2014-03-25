package cn.gaohongtao.grape.common.task

import cn.gaohongtao.grape.common.task.TaskHandler
import cn.gaohongtao.grape.common.task.TaskHandlerContext
import cn.gaohongtao.grape.common.task.TaskPipeline
import org.junit.Before

import javax.validation.constraints.NotNull

import static org.junit.Assert.*
import org.junit.Test

/**
 * Created by gaohongtao on 14-3-19.
 */
class TaskHandlerTest {

    def handler;

    TaskHandlerContext handlerContext;

    StringBuilder result;

    private class TestHandler implements TaskHandler {
        private TaskHandlerContext context;

        @Override
        TaskHandler setContext(@NotNull TaskHandlerContext context) {
            this.context = context;
            return this;
        }

        TaskHandler doSomething(String content) {
            result << content << " ";
            context.fireDoSomething(content);
            return this;
        }
    }

    @Before
    void initHandler() {
        result = new StringBuilder();
        handler = new TestHandler();
        handlerContext = new TaskHandlerContext(handler);
    }

    TaskHandlerContext getMock(){
        TaskHandlerContext mock = new TaskHandlerContext(null);
        mock.next = handlerContext;
        handlerContext.pre = mock;
        return mock;
    }


    @Test
    void testHandlerContextFire() {

        getMock().fireDoSomething("do it");
        assertEquals "do it ", result.toString()
    }

    @Test(expected = NoSuchMethodException)
    void testHandlerContextMisFire() {
        //调用处理器中不存在的方法
        getMock().fireDoMisSomething("do noting");
    }

    @Test
    void testAppendPipeline() {
        //链式调用
        TaskPipeline pipeline = new TaskPipeline();
        pipeline << new TaskHandlerContext(new TestHandler()) << new TaskHandlerContext(new TestHandler())
        pipeline.fireDoSomething("do it");
        assertEquals "do it do it ", result.toString();
    }

    @Test
    void testInsertPipeline() {
        TaskPipeline pipeline = new TaskPipeline();
        pipeline.addFirst(new TaskHandlerContext(new TestHandler())) addLast(new TaskHandlerContext(new TestHandler()))
        pipeline.fireDoSomething("do it");
        assertEquals "do it do it ", result.toString();
    }
}
