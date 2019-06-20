package com.wfy.zsxq;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyAsyncTask extends AsyncTask<String, Integer, String> {

    private String name;

    public MyAsyncTask(String name) {
        super();
        this.name = name;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return name;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log.e("tag", s + " === " + format.format(new Date()));
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }
}
