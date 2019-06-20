package com.wfy.zsxq;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

/**
 * Describe:
 * Author: wfy
 * Version:
 * Create by (wfy) on 2019/4/24 22:33
 * <p>
 * company :
 */
public class BActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("BActivity");
        setContentView(textView);
    }


    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        Log.e("tag", "onUserLeaveHint");
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        Log.e("tag", "onUserInteraction");
    }

    @Override
    protected void onPause() {
        setResult(999999);
        super.onPause();
        Log.e("tag", "B onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("tag", "B onStart");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("tag", "B onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("tag", "B onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("tag", "B onDestroy");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
