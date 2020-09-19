package com.android.basics.presentation;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.basics.core.navigation.Navigator;
import com.android.basics.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity<VM extends ViewModel> extends DaggerAppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Inject
    Navigator navigator;

    protected VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        viewModel = new ViewModelProvider(this, viewModelProviderFactory).get(getViewModel());

        navigator.setActivity(this);

    }

    protected abstract Integer getContentViewId();

    protected abstract Class<? extends VM> getViewModel();

}
