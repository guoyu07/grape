package cn.gaohongtao.grape

/**
 * Created by gaohongtao on 14-3-17.
 */
interface TriggerTaskHandler extends TaskHandler {

    TriggerTaskHandler beforeTaskRun(def name);

    TriggerTaskHandler afterTaskRun(def name);



}
