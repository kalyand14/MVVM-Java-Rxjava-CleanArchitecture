package com.android.basics.presentation.login;

public interface LoginContract {

    interface Navigator {
        void goToLoginScreen();

        void goToRegisterScreen();

        void goToHomeScreen();
    }
}
