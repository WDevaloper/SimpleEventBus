package com.wfy.zsxq;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wfy.zsxq.presenter.base.BasePresenter;

public class BaseActivity extends AppCompatActivity {

    protected BasePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLifecycle().addObserver(mPresenter);
    }
}
