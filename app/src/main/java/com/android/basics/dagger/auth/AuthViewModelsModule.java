package com.android.basics.dagger.auth;

import androidx.lifecycle.ViewModel;

import com.android.basics.dagger.ViewModelKey;
import com.android.basics.presentation.login.LoginViewModel;
import com.android.basics.presentation.registration.RegistrationViewModel;
import com.android.basics.presentation.splash.SplashViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class AuthViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel.class)
    public abstract ViewModel bindSplashViewModel(SplashViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel.class)
    public abstract ViewModel bindRegistrationViewModel(RegistrationViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    public abstract ViewModel bindLoginViewModel(LoginViewModel viewModel);


}
