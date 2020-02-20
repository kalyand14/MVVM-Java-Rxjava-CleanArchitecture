package com.android.basics.presentation.registration;

import android.app.ProgressDialog;

import androidx.lifecycle.ViewModelProvider;

import com.android.basics.core.TodoApplication;
import com.android.basics.core.navigation.Navigator;
import com.android.basics.di.ApplicationComponent;
import com.android.basics.di.ViewModelFactory;

public class RegisterUserInjector {

    private ApplicationComponent applicationComponent;
    private Navigator navigator;

    private static RegisterUserInjector instance = null;

    private RegisterUserInjector() {
    }

    public static RegisterUserInjector getInstance() {
        if (instance == null) {
            instance = new RegisterUserInjector();
        }
        return instance;
    }

    public void inject(RegisterUserActivity activity) {
        applicationComponent = ((TodoApplication) activity.getApplication()).getApplicationComponent();
        injectView(activity);
        injectObject(activity);
    }

    private void injectView(RegisterUserActivity activity) {
        activity.progressDialog = new ProgressDialog(activity);
        activity.progressDialog.setIndeterminate(true);
    }

    private void injectObject(RegisterUserActivity activity) {
        ViewModelFactory viewModelFactory = new ViewModelFactory(applicationComponent);
        navigator = viewModelFactory.getNavigator();
        activity.viewModel = new ViewModelProvider(activity, viewModelFactory).get(RegistrationViewModel.class);
    }


    public void destroy() {
        navigator.clear();
        navigator = null;
        instance = null;
    }
}
