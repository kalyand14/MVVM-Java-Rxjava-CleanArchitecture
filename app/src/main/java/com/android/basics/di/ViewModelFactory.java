package com.android.basics.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.basics.core.navigation.Navigator;
import com.android.basics.presentation.TodoCoordinator;
import com.android.basics.presentation.components.TodoSession;
import com.android.basics.presentation.components.UserSession;
import com.android.basics.presentation.home.HomeScreenViewModel;
import com.android.basics.presentation.splash.SplashViewModel;
import com.android.basics.presentation.todo.add.AddTodoViewModel;
import com.android.basics.presentation.todo.edit.EditTodoViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private ApplicationComponent applicationComponent;
    private PresentationModule presentationModule;
    private Navigator navigator;
    private TodoCoordinator todoCoordinator;

    public ViewModelFactory(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
        this.presentationModule = applicationComponent.getPresentationModule();
        this.navigator = applicationComponent.navigator();
        this.todoCoordinator = new TodoCoordinator(navigator);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
      /*  if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            return (T) provideSplashViewModel();
        } else if (modelClass.isAssignableFrom(HomeScreenViewModel.class)) {
            return (T) provideHomeScreenViewModel();
        } else if (modelClass.isAssignableFrom(AddTodoViewModel.class)) {
            return (T) provideAddTodoViewModel();
        } else if (modelClass.isAssignableFrom(EditTodoViewModel.class)) {
            return (T) provideEditTodoViewModel();
        }*/
        //noinspection unchecked
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

    /*public RegistrationViewModel provideRegistrationViewModel() {
        return new RegistrationViewModel(
                presentationModule.provideRegisterUser(),
                presentationModule.provideAuthenticateUser(),
                todoCoordinator,
                UserSession.getInstance()
        );
    }*/

   /* public LoginViewModel provideLoginViewModel() {
        return new LoginViewModel(
                todoCoordinator,
                presentationModule.provideAuthenticateUser(),
                UserSession.getInstance()
        );
    }*/

    public SplashViewModel provideSplashViewModel() {
        return new SplashViewModel(
                todoCoordinator
        );
    }

    /*public HomeScreenViewModel provideHomeScreenViewModel() {
        return new HomeScreenViewModel(
                presentationModule.provideGetTodoList(),
                todoCoordinator,
                UserSession.getInstance(),
                UserComponent.getInstance()
        );
    }*/

   /* public AddTodoViewModel provideAddTodoViewModel() {
        return new AddTodoViewModel(
                todoCoordinator,
                presentationModule.provideAddTodo(),
                UserSession.getInstance()
        );
    }

    public EditTodoViewModel provideEditTodoViewModel() {
        return new EditTodoViewModel(
                presentationModule.provideEditTodoInteractor(),
                presentationModule.provideDeleteTodoInteractor(),
                todoCoordinator,
                TodoSession.getInstance()
        );
    }*/

    public Navigator getNavigator() {
        return navigator;
    }

    public TodoCoordinator getTodoCoordinator() {
        return todoCoordinator;
    }

}
