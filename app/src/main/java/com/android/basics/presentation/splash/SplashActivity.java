package com.android.basics.presentation.splash;

import android.os.Bundle;
import android.os.Handler;

import com.android.basics.R;
import com.android.basics.presentation.BaseActivity;

public class SplashActivity extends BaseActivity<SplashViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(() -> viewModel.navigate(), 1000);
    }

    @Override
    protected Integer getContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected Class getViewModel() {
        return SplashViewModel.class;
    }

}
