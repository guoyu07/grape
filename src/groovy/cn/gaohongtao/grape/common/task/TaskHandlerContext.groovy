package cn.gaohongtao.grape.common.task

/**
 * 提供动态链式调用
 *
 * Created by gaohongtao on 14-3-17.
 */
class TaskHandlerContext {

    protected final TaskHandler handler;

    protected TaskHandlerContext pre;

    protected TaskHandlerContext next;

    TaskHandlerContext(TaskHandler handler) {
        this.handler = handler;
        if (handler)
            this.handler.context = this;
    }

    /**
     * 动态调用方法,根据类似fireXXX的方法调用处理流下端的处理器方法XXX
     *
     * @param name 方法名
     * @param args
     * @return
     */
    @Override
    Object invokeMethod(String name, Object args) {
        if (name.startsWith("fire")) {
            String suffix = name[4..name.length() - 1];
            String firstChar = suffix.charAt(0);
            def targetMethod = suffix.replaceFirst(firstChar, firstChar.toLowerCase());

            def nextHandler = next?.handler
            if (nextHandler) {
                if (nextHandler.metaClass.respondsTo(nextHandler, targetMethod)) {
                    return nextHandler.invokeMethod(targetMethod, args);
                } else {
                    throw new NoSuchMethodException(targetMethod);
                }
            }

        } else {
            throw new NoSuchMethodException(name);
        }

        return null;
    }
}