package com.android.basics.presentation.home;

import android.app.ProgressDialog;

import androidx.lifecycle.ViewModelProvider;

import com.android.basics.core.TodoApplication;
import com.android.basics.core.navigation.Navigator;
import com.android.basics.di.ApplicationComponent;
import com.android.basics.di.TodoComponent;
import com.android.basics.di.ViewModelFactory;
import com.android.basics.presentation.TodoCoordinator;
import com.android.basics.presentation.components.TodoSession;
import com.android.basics.presentation.components.UserSession;
import com.android.basics.presentation.home.components.TodoListAdapter;

import java.util.ArrayList;

public class HomeScreenInjector {

    private ApplicationComponent applicationComponent;
    private Navigator navigator;
    private TodoCoordinator coordinator;

    private static HomeScreenInjector instance = null;

    private HomeScreenInjector() {
    }

    public static HomeScreenInjector getInstance() {
        if (instance == null) {
            instance = new HomeScreenInjector();
        }
        return instance;
    }

    public void inject(HomeActivity activity) {
        applicationComponent = ((TodoApplication) activity.getApplication()).getApplicationComponent();
        injectView(activity);
        injectObject(activity);
    }

    private void injectView(HomeActivity activity) {
        activity.progressDialog = new ProgressDialog(activity);
        activity.progressDialog.setIndeterminate(true);
        activity.progressDialog.setMessage("Logging in");
    }

    private void injectObject(HomeActivity activity) {
        activity.user = UserSession.getInstance().getUser();
        ViewModelFactory viewModelFactory = new ViewModelFactory(applicationComponent);
        navigator = viewModelFactory.getNavigator();
        navigator.setActivity(activity);
        coordinator = viewModelFactory.getTodoCoordinator();
        activity.viewModel = new ViewModelProvider(activity, viewModelFactory).get(HomeScreenViewModel.class);

        activity.todoListAdapter = provideTodoListAdapter();
    }

    private TodoListAdapter provideTodoListAdapter() {
        return new TodoListAdapter(new ArrayList<>(), coordinator, TodoSession.getInstance(), TodoComponent.getInstance());
    }

    public void destroy() {
        navigator.clear();
        navigator = null;
        instance = null;
    }
}
