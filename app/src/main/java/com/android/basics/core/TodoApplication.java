package com.android.basics.core;

import android.app.Application;

import com.android.basics.dagger.DaggerAppComponent;
import com.android.basics.di.ApplicationComponent;
import com.android.basics.di.ApplicationModule;
import com.android.basics.di.PresentationModule;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class TodoApplication extends DaggerApplication {


    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = ApplicationComponent.getInstance();
        applicationComponent.setApplicationModule(new ApplicationModule(this));
        applicationComponent.setPresentationModule(new PresentationModule());

    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
        //return null;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
