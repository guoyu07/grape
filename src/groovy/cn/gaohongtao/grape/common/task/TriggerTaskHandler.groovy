package cn.gaohongtao.grape.common.task

/**
 * Created by gaohongtao on 14-3-17.
 */
interface TriggerTaskHandler extends TaskHandler {

    TriggerTaskHandler beforeTaskRun(def name) throws Exception;

    TriggerTaskHandler afterTaskRun(def name) throws Exception;


}
