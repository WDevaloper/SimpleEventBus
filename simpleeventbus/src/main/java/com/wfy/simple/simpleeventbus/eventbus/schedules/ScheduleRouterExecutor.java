package com.wfy.simple.simpleeventbus.eventbus.schedules;


import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.wfy.simple.simpleeventbus.eventbus.ThreadMode;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * 线程切换
 */
public class ScheduleRouterExecutor extends ThreadPoolExecutor {
    //主线程
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    //CPU数量
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    //核心线程数量
    private static final int INIT_THREAD_COUNT = CPU_COUNT + 1;
    //线程池最大县城内容量
    private static final int MAX_THREAD_COUNT = INIT_THREAD_COUNT * 2;
    //非核心线程闲置存活的时间（秒）
    private static final long SURPLUS_THREAD_LIFE = 30L;

    //默认线程工厂
    private static ThreadFactory DEFAULT_THREAD_FACTORY = new DefaultThreadFactory();

    private static RejectedExecutionHandler REJECTED_HANDLER = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            Log.e("tag", "Task rejected, too many task!");
        }
    };

    private static ScheduleRouterExecutor instance;

    @NonNull
    public static ScheduleRouterExecutor getInstance() {
        if (null == instance) {
            synchronized (ScheduleRouterExecutor.class) {
                if (null == instance) {
                    instance = new ScheduleRouterExecutor(INIT_THREAD_COUNT, MAX_THREAD_COUNT,
                            SURPLUS_THREAD_LIFE, TimeUnit.SECONDS,
                            new LinkedBlockingQueue<Runnable>(64),
                            DEFAULT_THREAD_FACTORY,
                            REJECTED_HANDLER);
                }
            }
        }
        return instance;
    }


    private ScheduleRouterExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                   BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                                   RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }


    public void executeTask(@NonNull EventTask task) {
        if (task.getThreadMode() == ThreadMode.BACKGROUND) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                execute(task);
                return;
            }
            task.run();
        } else if (task.threadMode == ThreadMode.MAIN) {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                mainHandler.post(task);
            } else {
                task.run();
            }
        }
    }


    /**
     * 线程执行结束，顺便看一下有么有什么乱七八糟的异常
     *
     * @param r the runnable that has completed
     * @param t the exception that caused termination, or null if
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        Log.e("tag", "afterExecute");
        if (t == null && r instanceof Future<?>) {
            try {
                ((Future<?>) r).get();
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException ie) {
                // ignore/reset
                Thread.currentThread().interrupt();
            }
        }
        if (t != null) {
            Log.e("tag", "Running task appeared exception! Thread [" +
                    Thread.currentThread().getName() + "], because [" + t.getMessage() + "]\n" +
                    formatStackTrace(t.getStackTrace()));
        }
    }

    public static String formatStackTrace(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : stackTrace) {
            sb.append("    at ").append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
