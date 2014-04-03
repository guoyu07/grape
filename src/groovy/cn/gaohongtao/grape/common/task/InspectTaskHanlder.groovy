package cn.gaohongtao.grape.common.task

import org.jetbrains.annotations.NotNull

/**
 * 用于反映任务自身状况的接口
 *
 * Created by gaohongtao on 14-3-24.
 */
public interface InspectTaskHanlder {

    def get(@NotNull def name);

    def list();

}