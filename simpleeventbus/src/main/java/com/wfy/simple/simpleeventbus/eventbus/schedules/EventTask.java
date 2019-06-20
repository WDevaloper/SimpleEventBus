package com.wfy.simple.simpleeventbus.eventbus.schedules;

import com.wfy.simple.simpleeventbus.eventbus.ThreadMode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Describe:
 * @Author: wfy
 * @Version: Create time: (wfy) on 2019/6/20 18:49
 * company :
 */
public class EventTask implements Runnable {
    private Method method;
    private Object object;
    private Object beanObject;
    ThreadMode threadMode;

    public EventTask(Method method, Object object, Object beanObject, ThreadMode threadMode) {
        this.method = method;
        this.object = object;
        this.beanObject = beanObject;
        this.threadMode = threadMode;
    }

    public Method getMethod() {
        return method;
    }

    public Object getObject() {
        return object;
    }

    public Object getBeanObject() {
        return beanObject;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    @Override
    public void run() {
        try {
            method.invoke(object, beanObject);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
