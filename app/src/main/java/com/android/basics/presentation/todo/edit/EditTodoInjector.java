package com.android.basics.presentation.todo.edit;

import android.app.ProgressDialog;

import androidx.lifecycle.ViewModelProvider;

import com.android.basics.core.TodoApplication;
import com.android.basics.core.navigation.Navigator;
import com.android.basics.di.ApplicationComponent;
import com.android.basics.di.ViewModelFactory;

public class EditTodoInjector {

    private ApplicationComponent applicationComponent;
    private static EditTodoInjector instance = null;
    private Navigator navigator;

    private EditTodoInjector() {
    }

    public static EditTodoInjector getInstance() {
        if (instance == null) {
            instance = new EditTodoInjector();
        }
        return instance;
    }

    public void inject(EditTodoActivity activity) {
        applicationComponent = ((TodoApplication) activity.getApplication()).getApplicationComponent();
        injectView(activity);
        injectObject(activity);
    }

    private void injectView(EditTodoActivity activity) {
        activity.progressDialog = new ProgressDialog(activity);
        activity.progressDialog.setIndeterminate(true);
    }

    private void injectObject(EditTodoActivity activity) {
        ViewModelFactory viewModelFactory = new ViewModelFactory(applicationComponent);
        navigator = viewModelFactory.getNavigator();
        navigator.setActivity(activity);
        activity.viewModel = new ViewModelProvider(activity, viewModelFactory).get(EditTodoViewModel.class);
    }


    public void destroy() {
        navigator.clear();
        navigator = null;
        instance = null;
    }

}
