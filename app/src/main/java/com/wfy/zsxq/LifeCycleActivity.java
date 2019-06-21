package com.wfy.zsxq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wfy.simple.simpleeventbus.eventbus.SimpleEventBus;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LifeCycleActivity extends Activity {


    private final static String S_TAG = "LifeCycleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_life_cycle);

        TextView view = (TextView) findViewById(R.id.text);
        Log.e(S_TAG, "" + view.getWidth() + "====" + view.getMeasuredWidth());


        Log.e(S_TAG, "onCreatefinish");

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

            }
        }).observeOn(Schedulers.io()).subscribeOn(AndroidSchedulers.mainThread()).subscribe();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    int index = i;
                    new Thread() {
                        @Override
                        public void run() {
                            SimpleEventBus.getDefault().post(new EventMessage("Event" + index));
//                            SystemClock.sleep(1000);
                        }
                    }.start();
                }
            }
        }, 1000);

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.e(S_TAG, "onPostCreate");
        View view = findViewById(R.id.text);
        Log.e(S_TAG, "" + view.getWidth() + "====" + view.getMeasuredWidth());
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(S_TAG, "onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(S_TAG, "onStart");
    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.e(S_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(S_TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(S_TAG, "onRestart");
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(S_TAG, "onSaveInstanceState");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(S_TAG, "onNewIntent");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(S_TAG, "onRestoreInstanceState");
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e(S_TAG, "onAttachedToWindow");
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        Log.e(S_TAG, "onContentChanged");
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e(S_TAG, "onDetachedFromWindow");
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        Log.e(S_TAG, "onUserInteraction");
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        Log.e(S_TAG, "onUserLeaveHint");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(S_TAG, "onResume");
        View view = findViewById(R.id.text);
        Log.e(S_TAG, "" + view.getWidth() + "====" + view.getMeasuredWidth());
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.e(S_TAG, "onPostResume");
        View view = findViewById(R.id.text);
        Log.e(S_TAG, "" + view.getWidth() + "====" + view.getMeasuredWidth());
        view.post(new Runnable() {
            @Override
            public void run() {
                Log.e(S_TAG, "" + view.getWidth() + "====" + view.getMeasuredWidth());
            }
        });

    }

}
