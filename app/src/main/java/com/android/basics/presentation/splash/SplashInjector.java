package com.android.basics.presentation.splash;

import androidx.lifecycle.ViewModelProvider;

import com.android.basics.core.TodoApplication;
import com.android.basics.core.navigation.Navigator;
import com.android.basics.di.ApplicationComponent;
import com.android.basics.di.ViewModelFactory;

public class SplashInjector {

    private ApplicationComponent applicationComponent;
    private static SplashInjector instance = null;
    private Navigator navigator;

    private SplashInjector() {
    }

    public static SplashInjector getInstance() {
        if (instance == null) {
            instance = new SplashInjector();
        }
        return instance;
    }

    public void inject(SplashActivity activity) {
        applicationComponent = ((TodoApplication) activity.getApplication()).getApplicationComponent();
        injectObject(activity);
    }

    private void injectObject(SplashActivity activity) {
        ViewModelFactory viewModelFactory = new ViewModelFactory(applicationComponent);
        navigator = viewModelFactory.getNavigator();
        activity.viewModel = new ViewModelProvider(activity, viewModelFactory).get(SplashViewModel.class);
    }

    public void destroy() {
        navigator.clear();
        navigator = null;
        instance = null;
    }

}
