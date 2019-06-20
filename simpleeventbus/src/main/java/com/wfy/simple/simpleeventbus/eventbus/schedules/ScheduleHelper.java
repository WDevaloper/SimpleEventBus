package com.wfy.simple.simpleeventbus.eventbus.schedules;


import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.wfy.simple.simpleeventbus.eventbus.ThreadMode;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduleHelper {

    private static Handler mainHandler = new Handler(Looper.getMainLooper());

    private static ThreadPoolExecutor executor =
            new ThreadPoolExecutor(5,
                    10,
                    60,
                    TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(128),
                    new ThreadFactory() {
                        @Override
                        public Thread newThread(@NonNull final Runnable r) {
                            return new Thread("simple event Thread") {
                                @Override
                                public void run() {
                                    r.run();
                                }
                            };
                        }
                    }
            );


    public static void execute(EventTask task) {
        if (task.getThreadMode() == ThreadMode.BACKGROUND) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                executor.execute(task);
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
}
