package com.wfy.simple.simpleeventbus.eventbus;


import android.support.annotation.NonNull;

import com.wfy.simple.simpleeventbus.eventbus.annotations.Subscribe;
import com.wfy.simple.simpleeventbus.eventbus.schedules.EventTask;
import com.wfy.simple.simpleeventbus.eventbus.schedules.ScheduleRouterExecutor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class SimpleEventBus {

    private static SimpleEventBus mIntance = null;


    //观察者可能有多个
    private ConcurrentHashMap<Object, List<SubscriberMethod>> mCacheMap = new ConcurrentHashMap<>();


    private SimpleEventBus() {
        if (mIntance != null) {
            throw new RuntimeException("MyEVentBus 只能有一个实例");
        }
    }


    @NonNull
    public static SimpleEventBus getDefault() {
        if (mIntance == null) {
            synchronized (SimpleEventBus.class) {
                if (mIntance == null) {
                    mIntance = new SimpleEventBus();
                }
            }
        }
        return mIntance;
    }

    public void register(Object object) {
        List<SubscriberMethod> subscribes = mCacheMap.get(object);
        if (subscribes == null) {
            synchronized (SimpleEventBus.class) {
                subscribes = findSubscribeMethod(object);
                mCacheMap.put(object, subscribes);
            }
        }
    }


    public void unRegister(Object object) {
        List<SubscriberMethod> subscribes = mCacheMap.get(object);
        if (subscribes != null) {
            subscribes.clear();
            mCacheMap.remove(object);
        }
    }

    private List<SubscriberMethod> findSubscribeMethod(Object object) {
        List<SubscriberMethod> subscriberMethods = new CopyOnWriteArrayList<>();
        Class<?> clazz = object.getClass();

        while (clazz != null) {
            String name = clazz.getName();
            //排除系统的类或接口，不能是系统的类或接口，因为我们的订阅方法只能是我们自己的类或接口
            if (name.startsWith("java") || name.startsWith("javax") || name.startsWith("android")) {
                break;
            }

            //该类定义的Method
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                Annotation annotation = method.getAnnotation(Subscribe.class);
                if (annotation == null) continue;
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1) throw new RuntimeException("只能有一个参数");

                Class<?> methodParameterType = parameterTypes[0];
                ThreadMode threadMode = ((Subscribe) annotation).threadMode();
                subscriberMethods.add(new SubscriberMethod(method, threadMode, methodParameterType));
            }
            clazz = clazz.getSuperclass();
        }

        if (subscriberMethods.size() <= 0) {
            throw new RuntimeException("必须有一个订阅方法");
        }

        return subscriberMethods;
    }

    /**
     * 实际上是根据订阅方法的参数和发布传进来的对象进行对比
     *
     * @param eventMessage
     */
    public void post(Object eventMessage) {
        Set<Object> objects = mCacheMap.keySet();
        for (Object obj : objects) {
            List<SubscriberMethod> subscriberMethods = mCacheMap.get(obj);
            if (subscriberMethods == null) continue;

            for (SubscriberMethod subMethod : subscriberMethods) {
                Class<?> aClass = subMethod.getEventType();
                Class<?> bClass = eventMessage.getClass();
                //aClass 的class是是不是bClass的的父类或者接口
                if (aClass.isAssignableFrom(bClass)) {
                    invoke(subMethod, obj, eventMessage);
                }
            }
        }
    }

    private void invoke(SubscriberMethod subscriberMethod, Object obj, final Object eventObj) {
        EventTask eventTask = new EventTask(subscriberMethod.getMethod(), obj, eventObj, subscriberMethod.getThreadMode());
        ScheduleRouterExecutor.getInstance().executeTask(eventTask);
    }
}
