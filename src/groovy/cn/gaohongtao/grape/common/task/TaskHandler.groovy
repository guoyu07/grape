package cn.gaohongtao.grape.common.task

import com.sun.istack.internal.NotNull


/**
 *  任务处理器,对外提供处理器组建
 *
 * Created by gaohongtao on 14-3-17.
 */
public interface TaskHandler {

    /**
     * 设置处理器的上下文,只有设置过上下文的处理器才能加入到管道中
     *
     * @param context 上下文对象
     * @return TaskHandler 用于链式调用
     */
    TaskHandler setContext(@NotNull TaskHandlerContext context);


}