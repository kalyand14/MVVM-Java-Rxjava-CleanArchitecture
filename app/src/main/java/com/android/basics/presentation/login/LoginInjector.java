package com.android.basics.presentation.login;

import android.app.ProgressDialog;

import androidx.lifecycle.ViewModelProvider;

import com.android.basics.core.TodoApplication;
import com.android.basics.core.navigation.Navigator;
import com.android.basics.di.ApplicationComponent;
import com.android.basics.di.ViewModelFactory;

public class LoginInjector {

    private ApplicationComponent applicationComponent;
    private Navigator navigator;

    private static LoginInjector instance = null;

    private LoginInjector() {
    }

    public static LoginInjector getInstance() {
        if (instance == null) {
            instance = new LoginInjector();
        }
        return instance;
    }

    public void inject(LoginActivity activity) {
        applicationComponent = ((TodoApplication) activity.getApplication()).getApplicationComponent();
        injectView(activity);
        injectObject(activity);
    }

    private void injectView(LoginActivity activity) {
        activity.progressDialog = new ProgressDialog(activity);
        activity.progressDialog.setIndeterminate(true);
        activity.progressDialog.setMessage("Logging in");
    }

    private void injectObject(LoginActivity activity) {
        ViewModelFactory viewModelFactory = new ViewModelFactory(applicationComponent);
        navigator = viewModelFactory.getNavigator();
        activity.viewModel = new ViewModelProvider(activity, viewModelFactory).get(LoginViewModel.class);
    }

    public void destroy() {
        navigator.clear();
        navigator = null;
        instance = null;
    }
}
