package com.android.basics.presentation.splash;

import androidx.lifecycle.ViewModel;

import com.android.basics.presentation.TodoCoordinator;

import javax.inject.Inject;

public class SplashViewModel extends ViewModel {

    private SplashContract.Navigator navigator;

    @Inject
    public SplashViewModel(TodoCoordinator navigator) {
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
