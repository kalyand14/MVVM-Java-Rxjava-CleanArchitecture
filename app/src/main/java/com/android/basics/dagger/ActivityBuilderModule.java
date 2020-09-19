package com.android.basics.dagger;

import com.android.basics.dagger.auth.AuthModule;
import com.android.basics.dagger.auth.AuthScope;
import com.android.basics.dagger.auth.AuthViewModelsModule;
import com.android.basics.dagger.main.MainModule;
import com.android.basics.dagger.main.MainScope;
import com.android.basics.dagger.main.MainViewModelsModule;
import com.android.basics.dagger.todo.TodoModule;
import com.android.basics.dagger.todo.TodoScope;
import com.android.basics.dagger.todo.TodoViewModelsModule;
import com.android.basics.presentation.home.HomeActivity;
import com.android.basics.presentation.login.LoginActivity;
import com.android.basics.presentation.registration.RegisterUserActivity;
import com.android.basics.presentation.splash.SplashActivity;
import com.android.basics.presentation.todo.add.AddTodoActivity;
import com.android.basics.presentation.todo.edit.EditTodoActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @AuthScope
    @ContributesAndroidInjector(modules = {AuthModule.class, AuthViewModelsModule.class})
    abstract SplashActivity contributeSplashActivity();

    @AuthScope
    @ContributesAndroidInjector(modules = {AuthModule.class, AuthViewModelsModule.class})
    abstract RegisterUserActivity contributeRegisterUserActivity();

    @AuthScope
    @ContributesAndroidInjector(modules = {AuthModule.class, AuthViewModelsModule.class})
    abstract LoginActivity contributeLoginActivity();

    @MainScope
    @ContributesAndroidInjector(modules = {MainViewModelsModule.class, MainModule.class})
    abstract HomeActivity contributeHomeActivity();

    @TodoScope
    @ContributesAndroidInjector(modules = {
            TodoModule.class, TodoViewModelsModule.class
    })
    abstract AddTodoActivity contributeAddTodoActivity();


    @TodoScope
    @ContributesAndroidInjector(modules = {
            TodoModule.class, TodoViewModelsModule.class
    })
    abstract EditTodoActivity contributeEditTodoActivity();

}
