package com.android.basics.presentation.splash;

public interface SplashContract {


    interface Presenter {
        void navigate();
    }

    interface Navigator {
        void goToLoginScreen();
    }
}
