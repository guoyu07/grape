package cn.gaohongtao.grape.task

import cn.gaohongtao.grape.common.task.TaskControlHandler
import cn.gaohongtao.grape.common.task.TaskHandler
import cn.gaohongtao.grape.common.task.TaskHandlerContext
import cn.gaohongtao.grape.common.task.TaskPipeline
import cn.gaohongtao.grape.common.task.TaskRunnable
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import org.quartz.Job
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.Scheduler
import org.quartz.impl.StdSchedulerFactory

import static org.quartz.JobBuilder.*

/**
 * 使用quartz作为trigger的任务
 *
 * Created by gaohongtao on 14-3-26.
 */
class QuartzTaskControlHandler implements TaskControlHandler {
    private final Scheduler sch;
    private final TaskPipeline jobProxy;

    QuartzTaskControlHandler(TaskPipeline jobProxy) {
        sch = StdSchedulerFactory.getDefaultScheduler();
        this.jobProxy = jobProxy;
    }

    @Override
    TaskControlHandler register(
            @NotNull def name,
            @Nullable def defaultContext,
            @NotNull TaskRunnable taskRunnable) throws Exception {
        sch.start();
        return null
    }

    @Override
    TaskControlHandler remove(@NotNull def @NotNull Object name) throws Exception {
        return null
    }

    @Override
    TaskControlHandler active(@NotNull def @NotNull Object name, @Nullable def @Nullable Object plan) throws Exception {
        return null
    }

    @Override
    TaskControlHandler inactive(@NotNull def @NotNull Object name) throws Exception {
        return null
    }

    @Override
    TaskControlHandler run(
            @NotNull def @NotNull Object name, @Nullable def @Nullable Object runtimeContext) throws Exception {
        return null
    }

    @Override
    TaskControlHandler interrupt(@NotNull def @NotNull Object name) throws Exception {
        return null
    }

    @Override
    TaskHandler setContext(@NotNull @NotNull TaskHandlerContext context) {
        return null
    }
}
