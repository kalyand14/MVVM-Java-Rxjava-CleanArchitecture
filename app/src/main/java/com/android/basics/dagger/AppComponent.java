package com.android.basics.dagger;

import android.app.Application;

import com.android.basics.core.TodoApplication;
import com.android.basics.presentation.SessionManager;
import com.android.basics.presentation.components.UserCache;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityBuilderModule.class,
        ViewModelProviderFactoryModule.class,
        AppModule.class,
})

public interface AppComponent extends AndroidInjector<TodoApplication> {

    SessionManager sessionManager();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();

    }

}
