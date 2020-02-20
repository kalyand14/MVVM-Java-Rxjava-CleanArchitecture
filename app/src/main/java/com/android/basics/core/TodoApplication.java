package com.android.basics.core;

import android.app.Application;

import com.android.basics.di.ApplicationComponent;
import com.android.basics.di.ApplicationModule;
import com.android.basics.di.PresentationModule;

public class TodoApplication extends Application {


    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = ApplicationComponent.getInstance();
        applicationComponent.setApplicationModule(new ApplicationModule(this));
        applicationComponent.setPresentationModule(new PresentationModule());

    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
