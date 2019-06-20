package com.wfy.zsxq;


import android.util.Log;

public class LockExample {
    public synchronized void entranslock1(){
        Log.e("tag", "来了老弟A");
        entranslock2();
    }

    public synchronized void entranslock2() {
        Log.e("tag", "来了老弟B");
    }
}
