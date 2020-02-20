package com.android.basics.presentation.todo.add;

import android.app.ProgressDialog;

import androidx.lifecycle.ViewModelProvider;

import com.android.basics.core.TodoApplication;
import com.android.basics.core.navigation.Navigator;
import com.android.basics.di.ApplicationComponent;
import com.android.basics.di.ViewModelFactory;
import com.android.basics.presentation.todo.edit.EditTodoViewModel;

public class AddTodoInjector {

    private ApplicationComponent applicationComponent;
    private static AddTodoInjector instance = null;
    private Navigator navigator;

    private AddTodoInjector() {
    }

    public static AddTodoInjector getInstance() {
        if (instance == null) {
            instance = new AddTodoInjector();
        }
        return instance;
    }

    public void inject(AddTodoActivity activity) {
        applicationComponent = ((TodoApplication) activity.getApplication()).getApplicationComponent();
        injectView(activity);
        injectObject(activity);
    }

    private void injectView(AddTodoActivity activity) {
        activity.progressDialog = new ProgressDialog(activity);
        activity.progressDialog.setIndeterminate(true);
    }

    private void injectObject(AddTodoActivity activity) {
        ViewModelFactory viewModelFactory = new ViewModelFactory(applicationComponent);
        navigator = viewModelFactory.getNavigator();
        navigator.setActivity(activity);
        activity.viewModel = new ViewModelProvider(activity, viewModelFactory).get(AddTodoViewModel.class);
    }


    public void destroy() {
        navigator.clear();
        navigator = null;
        instance = null;
    }

}
