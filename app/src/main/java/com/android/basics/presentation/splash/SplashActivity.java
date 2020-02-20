package com.android.basics.presentation.splash;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.android.basics.R;

public class SplashActivity extends AppCompatActivity {

    SplashViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SplashInjector.getInstance().inject(this);
        new Handler().postDelayed(() -> viewModel.navigate(), 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SplashInjector.getInstance().destroy();
    }
}
