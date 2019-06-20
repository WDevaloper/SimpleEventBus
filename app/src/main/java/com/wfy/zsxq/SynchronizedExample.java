package com.wfy.zsxq;

import android.util.Log;

public class SynchronizedExample {

    public synchronized void share() {
            Log.e("tag", "share 开始执行.....");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.e("tag", "share 结束.....");
    }


    public synchronized void share2() {
        Log.e("tag", "share2 开始执行.....");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.e("tag", "share2 结束.....");
    }
}
