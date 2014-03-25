package cn.gaohongtao.grape.common.task
import com.sun.istack.internal.NotNull
/**
 * 用于反映任务自身状况的接口
 *
 * Created by gaohongtao on 14-3-24.
 */
public interface InspectTaskHanlder {

    def get(@NotNull def name);

}