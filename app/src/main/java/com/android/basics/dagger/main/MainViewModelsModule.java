package com.android.basics.dagger.main;

import androidx.lifecycle.ViewModel;

import com.android.basics.dagger.ViewModelKey;
import com.android.basics.presentation.home.HomeScreenViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeScreenViewModel.class)
    public abstract ViewModel bindHomeScreenViewModel(HomeScreenViewModel viewModel);

}
