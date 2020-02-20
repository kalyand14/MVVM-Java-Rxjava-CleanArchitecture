package com.android.basics.presentation.todo.add;

import android.app.ProgressDialog;

import com.android.basics.core.TodoApplication;
import com.android.basics.di.ApplicationComponent;
import com.android.basics.domain.interactor.todo.AddTodoInteractor;
import com.android.basics.presentation.TodoCoordinator;
import com.android.basics.presentation.components.UserSession;

public class AddTodoInjector {

    private ApplicationComponent applicationComponent;
    private static AddTodoInjector instance = null;

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
        activity.presenter = new AddTodoPresenter(provideNavigator(activity), provideAddTodo(), UserSession.getInstance());
    }

    private AddTodoContract.Navigator provideNavigator(AddTodoActivity activity) {
        return new TodoCoordinator(applicationComponent.navigator(activity));
    }

    private AddTodoInteractor provideAddTodo() {
        return new AddTodoInteractor(applicationComponent.todoRepository());
    }

    public void destroy() {
        instance = null;
    }

}
