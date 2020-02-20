package com.android.basics.presentation.splash;

import androidx.lifecycle.ViewModel;

public class SplashViewModel extends ViewModel {

    private SplashContract.Navigator navigator;

    public SplashViewModel(SplashContract.Navigator navigator) {
        this.navigator = navigator;
    }

    public void navigate() {
        navigator.goToLoginScreen();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        navigator = null;
    }
}
