package cn.gaohongtao.grape

/**
 *  任务处理器
 *
 * Created by gaohongtao on 14-3-17.
 */
public interface TaskHandler {

    TaskHandler setContext(TaskHandlerContext context);

    TaskHandler removeContext(TaskHandlerContext context);

}