package cn.gaohongtao.grape.common.task

import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
/**
 * 定义前向操作，由用户触发的相关操作。
 *
 * Created by gaohongtao on 14-3-17.
 */
public interface TaskControlHandler extends TaskHandler {

    /**
     * 任务注册，新增任务
     *
     * @param name 任务名称
     * @param defaultContext 上下文表达式,可空
     * @param taskRunnable 任务的可运行闭包
     * @return TaskControlHandler 链式调用
     * @throws Exception 操作异常，包括所有的异常
     */
    TaskControlHandler register(@NotNull def name,@Nullable def defaultContext, @NotNull TaskRunnable taskRunnable) throws Exception;

    /**
     * 删除任务,删除任务前要保证任务要失活，调用{@link TaskControlHandler#inactive(java.lang.Object)}
     *
     * @param name 任务名称
     * @return TaskControlHandler 链式调用
     * @throws Exception 操作异常，包括所有的异常
     */
    TaskControlHandler remove(@NotNull def name) throws Exception;

    /**
     * 激活任务，激活后的任务会根据其提交的计划任务开始执行任务。
     * @param name 任务名称
     * @param plan 运行计划，为空则执行原有的计划任务，如果原作业无任务计划则抛出
     * @return TaskControlHandler 链式调用
     * @throws Exception 操作异常，包括所有的异常
     */
    TaskControlHandler active(@NotNull def name,@Nullable def plan) throws Exception;

    /**
     * 失活，原有的计划任务不进行执行，但计划任务可以通过{@link TaskControlHandler#active(java.lang.Object, java.lang.Object)}，</br>
     * 重新执行原有计划
     * @param name 任务名称
     * @return TaskControlHandler 链式调用
     * @throws Exception 操作异常，包括所有的异常
     */
    TaskControlHandler inactive(@NotNull def name) throws Exception;

    /**
     * 主动触发任务,可以临时覆盖本次运行的上下文
     *
     * @param name 任务名称
     * @param runtimeContext 运行时上下文
     * @return TaskControlHandler 链式调用
     * @throws Exception 操作异常，包括所有的异常。运行一个正在运行的任务，将抛出{@link TaskHandlerException}
     */
    TaskControlHandler run(@NotNull def name,@Nullable def runtimeContext) throws Exception;

    /**
     * 终端运行中的任务,中断一个没有运行的任务，将不抛出任何异常
     * @param name 任务名称
     * @return TaskControlHandler 链式调用
     * @throws Exception
     */
    TaskControlHandler interrupt(@NotNull def name) throws Exception;

}