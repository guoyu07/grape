package cn.gaohongtao.grape.common.task

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by gaohongtao on 14-3-17.
 */
class TaskHandlerException extends Exception {
    private static Logger log = LoggerFactory.getLogger(TaskHandlerException.class);

    TaskHandlerException(Throwable throwable) {
        this("任务操作出现应处理异常", throwable)

    }

    TaskHandlerException(String s) {
        super(s)
        log.error(s)
    }

    TaskHandlerException(String s, Throwable throwable) {
        super(s, throwable)
        log.error(s, throwable);
    }
}
