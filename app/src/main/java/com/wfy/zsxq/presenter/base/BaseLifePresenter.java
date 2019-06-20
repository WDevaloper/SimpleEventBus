package com.wfy.zsxq.presenter.base;


/**
 * 声明Presenter声明周期回调河口
 *
 */
public interface BaseLifePresenter {
    void onCreate();

    void onStart();

    void onPause();

    void onResume();

    void onStop();

    void onDestroy();

}
