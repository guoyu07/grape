package cn.gaohongtao.grape.task

import cn.gaohongtao.grape.common.task.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

import static cn.gaohongtao.grape.common.task.TaskState.*

/**
 * 使用内部缓存存储任务数据，该层作为调试使用，不使用在生产环境
 *
 * Created by gaohongtao on 14-3-20.
 */
class InJvmTaskControlHandler implements TaskControlHandler, InspectTaskHanlder {
    private TaskHandlerContext context;

    private Map<Object, Object> taskCache = new HashMap<>();

    private static final Logger log = LoggerFactory.getLogger InJvmTaskControlHandler.class;

    private final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    @Override
    TaskControlHandler register(
            @NotNull def name,
            @Nullable def defaultContext, @NotNull TaskRunnable taskRunnable) throws Exception {
        if (taskCache.get(name)) {
            throw new TaskHandlerContext("任务 ${name} 已经注册")
        } else {
            if (log.isDebugEnabled())
                log.debug("开始注册任务 {}", name);
            if (!Inactive.valid(New)) {
                throw new TaskHandlerException("添加任务状态不正确");
            }
            Job job = new Job();
            job.name = name;
            job.context = defaultContext;
            job.state = Inactive;
            job.runnable = taskRunnable;

            taskCache.put(name, job);
        }
        return this
    }

    @Override
    TaskControlHandler remove(@NotNull def name) throws Exception {
        Job job = (Job) taskCache.get(name);
        if (job) {
            if (log.isDebugEnabled())
                log.debug("开始移除任务 {}", name);
            taskCache.remove(name);
        } else {
            throw new TaskHandlerException("没有注册${name}");
        }
        return this
    }

    @Override
    TaskControlHandler active(
            @NotNull def Object name, @Nullable def plan) throws Exception {
        changeState(name, Active, null);
        return this
    }

    @Override
    TaskControlHandler inactive(@NotNull def Object name) throws Exception {
        changeState(name, Inactive, null);
        return this
    }

    @Override
    TaskControlHandler run(
            @NotNull def name, @Nullable def runtimeContext) throws Exception {
        changeState(name, Running) { Job job ->
            if (runtimeContext != null)
                job.@runnable.run(runtimeContext);
            else
                job.@runnable.run(job.context);
            changeState(name, Active, null);
        }
        return this
    }

    @Override
    TaskControlHandler interrupt(@NotNull def Object name) throws Exception {
        changeState(name, Active, null)
        return this
    }

    @Override
    TaskHandler setContext(@NotNull TaskHandlerContext context) {
        this.context = context;
        return this
    }

    private void changeState(def name, TaskState state, Closure after) {
        Job job = (Job) taskCache.get(name);
        synchronized (job) {
            if (job && state.valid(job.state)) {
                if (log.isDebugEnabled())
                    log.debug("状态转换 {} -> {}", job.state, state);
                job.state = state;
                after?.call(job);
            } else {
                throw new TaskHandlerException("不能转变 ${job?.name} 任务状态");
            }
        }

    }

    @Override
    def get(def name) {
        def result = taskCache.get(name);
        if (result)
            return gson.toJson(result).toString();
        return null;
    }

    @Override
    def list() {
        gson.toJson(taskCache.values()).toString();
    }

    private static class Job {

        @Expose
        private String name;

        @Expose
        private String plan;

        @Expose
        private TaskState state;

        @Expose
        private TaskRunnable runnable;

        @Expose
        private def context;

        String getName() {
            return name
        }

        String getPlan() {
            return plan
        }

        TaskState getState() {
            return state;
        }

        String getRunnable() {
            return runnable.content
        }

        def getContext() {
            return context
        }
    }
}
