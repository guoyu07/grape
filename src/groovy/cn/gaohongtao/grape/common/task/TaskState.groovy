package cn.gaohongtao.grape.common.task

/**
 * 任务状态，用于核心存储层
 *
 * Created by gaohongtao on 14-3-24.
 */
public enum TaskState {
    New, Inactive, Running, Active, Dropped

    /**
     * 判断当前状态是否可以转换为现在的状态
     *
     * @param now 当前状态
     */
    boolean valid(TaskState now) {
        switch (this) {
            case New: switch (now) {
                default: return true;
            }
            case Inactive: switch (now) {
                case New:
                case Active: return true;
                default: return false;
            }
            case Active: switch (now) {
                case Inactive:
                case Running: return true;
                default: return false;
            }
            case Running: switch (now) {
                case Active: return true;
                default: return false;
            }
            case Dropped: switch (now) {
                case Inactive: return true;
                default: return false;
            }
            default: return false;
        }
    }


}