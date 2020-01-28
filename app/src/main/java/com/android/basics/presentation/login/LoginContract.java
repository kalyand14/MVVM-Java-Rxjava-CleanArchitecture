package com.android.basics.presentation.login;

import com.android.basics.core.mvp.BasePresenter;

public interface LoginContract {
    interface View {

        void showProgressDialog();

        void dismissProgressDialog();

        void showAuthenticationError();

    }

    interface Presenter extends BasePresenter<View> {
        void OnLoginClick(String userName, String password);

        void OSingUpClick();
    }

    interface Navigator {
        void goToLoginScreen();

        void goToRegisterScreen();
    }
}
