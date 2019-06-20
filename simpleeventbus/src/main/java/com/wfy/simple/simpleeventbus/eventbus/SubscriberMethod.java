package com.wfy.simple.simpleeventbus.eventbus;

import java.lang.reflect.Method;

/**
 * @Describe:
 * @Author: wfy
 * @Version: Create time: (wfy) on 2019/6/20 16:51
 * company :
 */
class SubscriberMethod {

    //方法本身
    private Method method;
    //线程模型
    private ThreadMode threadMode;
    //方法参数类型
    private Class<?> eventType;

    public SubscriberMethod(Method method, ThreadMode threadMode, Class<?> eventType) {
        this.method = method;
        this.threadMode = threadMode;
        this.eventType = eventType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        this.threadMode = threadMode;
    }

    public Class<?> getEventType() {
        return eventType;
    }

    public void setEventType(Class<?> eventType) {
        this.eventType = eventType;
    }
}
