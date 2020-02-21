package com.android.basics.presentation.home;

public interface HomeScreenContract {

    interface Navigator {

        void goToEditTodoScreen();

        void gotoAddTodoScreen();

        void goToLoginScreen();
    }
}
