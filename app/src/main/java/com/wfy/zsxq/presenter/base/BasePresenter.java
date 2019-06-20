package com.wfy.zsxq.presenter.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

/**
 * 实现LifecycleObserver，让Presenter具有声明周期意识的类
 */
public class BasePresenter<T extends BaseView> implements BaseLifePresenter, LifecycleObserver {

    public BaseView mRootView;

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    @Override
    public void onCreate() {
        Log.e("tag", "onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    @Override
    public void onStart() {
        Log.e("tag", "onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    @Override
    public void onPause() {
        Log.e("tag", "onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    @Override
    public void onResume() {
        Log.e("tag", "onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    @Override
    public void onStop() {
        Log.e("tag", "onStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    @Override
    public void onDestroy() {
        Log.e("tag", "onDestroy");
        mRootView = null;
    }
}
