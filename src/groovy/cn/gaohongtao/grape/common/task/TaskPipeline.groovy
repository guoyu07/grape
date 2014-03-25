package cn.gaohongtao.grape.common.task

/**
 * Created by gaohongtao on 14-3-18.
 */
class TaskPipeline {

    private final TaskHandlerContext head;

    private final TaskHandlerContext tail;

    TaskPipeline() {
        head = new TaskHandlerContext(null);
        tail = new TaskHandlerContext(null);
        head.next = tail;
        tail.pre = head;
    }

    def addLast(TaskHandlerContext context) {
        def preContext = tail.pre;
        context.next = tail;
        context.pre = preContext;
        preContext.next = context;
        tail.pre = context;
        return this;
    }

    /**
     * 使用<< 符号 进行操作
     * @param context
     * @return
     */
    def leftShift(TaskHandlerContext context) {
        return this.addLast(context);
    }

    def addFirst(TaskHandlerContext context) {
        def nextContext = head.next;
        context.pre = head;
        context.next = nextContext;
        nextContext.pre = context;
        head.next = context;
        return this;
    }

    /**
     * 触发方法动态调用，从head开始调用
     * @param name fire类方法的方法名
     * @param args 请求参数
     * @return {@link TaskPipeline}
     */
    @Override
    Object invokeMethod(String name, Object args) {
        if (name.startsWith("fire")) {
            return head.invokeMethod(name, args);
        } else {
            throw new NoSuchMethodException(name);
        }
    }


}
