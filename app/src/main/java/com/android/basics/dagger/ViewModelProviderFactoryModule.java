package com.android.basics.dagger;

import androidx.lifecycle.ViewModelProvider;

import com.android.basics.viewmodel.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelProviderFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelProviderFactory(ViewModelProviderFactory viewModelProviderFactory);

}
