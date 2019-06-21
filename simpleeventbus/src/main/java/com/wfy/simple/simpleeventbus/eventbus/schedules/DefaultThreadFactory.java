package com.wfy.simple.simpleeventbus.eventbus.schedules;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池工厂类
 */
public class DefaultThreadFactory implements ThreadFactory {

    //统计线程池的数量
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    //统计当前线程池的线程数量
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final ThreadGroup threadGroup;
    private final String namePrefix;

    public DefaultThreadFactory() {
        SecurityManager securityManager = System.getSecurityManager();
        threadGroup = (securityManager != null) ? securityManager.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        namePrefix = "Simple EventBus task pool No." + poolNumber.getAndIncrement() + ", thread No.";
    }

    @Override
    public Thread newThread(@NonNull Runnable runnable) {
        String threadName = namePrefix + threadNumber.getAndIncrement();


        Log.e("tag", "Thread production, name is [" + threadName + "]");

        final Thread thread = new Thread(threadGroup, runnable, threadName, 0);

        if (thread.isDaemon()) {//设置为非后台线程
            thread.setDaemon(false);
        }


        if (thread.getPriority() != Thread.NORM_PRIORITY) {//优先级为normal
            thread.setPriority(Thread.NORM_PRIORITY);
        }


        // 捕获多线程处理中的异常
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                Log.e("tag", "Running task appeared exception! Thread [" + thread.getName() + "], because [" + ex.getMessage() + "]");
            }
        });
        return thread;
    }
}
