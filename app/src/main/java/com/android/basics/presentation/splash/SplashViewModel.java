package com.android.basics.presentation.splash;

import androidx.lifecycle.ViewModel;

public class SplashViewModel extends ViewModel implements SplashContract.Presenter {

    private SplashContract.Navigator navigator;

    public SplashViewModel(SplashContract.Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void navigate() {
        navigator.goToLoginScreen();
    }
}
