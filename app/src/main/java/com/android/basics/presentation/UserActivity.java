package com.android.basics.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.basics.core.navigation.Navigator;
import com.android.basics.core.presentation.AuthResource;
import com.android.basics.domain.model.User;
import com.android.basics.presentation.login.LoginActivity;
import com.android.basics.viewmodel.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class UserActivity<VM extends ViewModel> extends DaggerAppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Inject
    ViewModelProviderFactory viewModelProviderFactory;

    @Inject
    Navigator navigator;

    @Inject
    SessionManager sessionManager;

    protected VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());

        viewModel = new ViewModelProvider(this, viewModelProviderFactory).get(getViewModel());

        navigator.setActivity(this);

        sessionManager.getUserAuthState().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> it) {
                if (it != null) {
                    handleState(it.getState(), it.getData(), it.getMessage());
                }
            }
        });

    }

    private void handleState(AuthResource.AuthResourceState state, User user, String errorMessage) {
        switch (state) {
            case AUTHENTICATED:
                Log.d(TAG, "onChanged: BaseActivity: AUTHENTICATED... " +
                        "Authenticated as: " + user.getUserName());
                break;
            case ERROR:
                Log.d(TAG, "onChanged: BaseActivity: ERROR...");
                break;
            case LOADING:
                Log.d(TAG, "onChanged: BaseActivity: LOADING...");
                break;

            case UN_AUTHENTICATED:
                Log.d(TAG, "onChanged: BaseActivity: NOT AUTHENTICATED. Navigating to Login screen.");
                navLoginScreen();
                break;
        }
    }

    private void navLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    protected abstract Integer getContentViewId();

    protected abstract Class<? extends VM> getViewModel();

    @Override
    protected void onDestroy() {
        super.onDestroy();
     /*   navigator.clear();
        navigator = null;*/
    }

}
