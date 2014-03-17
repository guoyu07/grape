package cn.gaohongtao.grape

/**
 * 定义前向操作，由用户触发的相关操作。
 *
 * Created by gaohongtao on 14-3-17.
 */
public interface FrontTaskHandler extends TaskHandler {

    FrontTaskHandler register(def name, def defaultContext, Closure taskRunnable) throws Exception;

    FrontTaskHandler remove(def name) throws Exception;

    FrontTaskHandler active(def name) throws Exception;

    FrontTaskHandler inactive(def name) throws Exception;

    FrontTaskHandler run(def name, def runtimeContext) throws Exception;

    FrontTaskHandler interrupt(def name) throws Exception;

}