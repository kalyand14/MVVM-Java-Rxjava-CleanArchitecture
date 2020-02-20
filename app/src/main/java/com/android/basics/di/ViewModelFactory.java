package com.android.basics.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.basics.core.navigation.Navigator;
import com.android.basics.presentation.TodoCoordinator;
import com.android.basics.presentation.components.UserSession;
import com.android.basics.presentation.home.HomeScreenViewModel;
import com.android.basics.presentation.login.LoginViewModel;
import com.android.basics.presentation.registration.RegistrationViewModel;
import com.android.basics.presentation.splash.SplashViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private ApplicationComponent applicationComponent;
    private PresentationModule presentationModule;
    private Navigator navigator = applicationComponent.navigator();


    private TodoCoordinator todoCoordinator = new TodoCoordinator(navigator);

    public ViewModelFactory(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
        this.presentationModule = applicationComponent.getPresentationModule();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegistrationViewModel.class)) {
            return (T) provideRegistrationViewModel();
        } else if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            return (T) provideSplashViewModel();
        } else if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) provideLoginViewModel();
        } else if (modelClass.isAssignableFrom(HomeScreenViewModel.class)) {
            return (T) provideHomeScreenViewModel();
        }
        //noinspection unchecked
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

    public RegistrationViewModel provideRegistrationViewModel() {
        return new RegistrationViewModel(
                presentationModule.provideRegisterUser(),
                presentationModule.provideAuthenticateUser(),
                todoCoordinator,
                UserSession.getInstance()
        );
    }

    public LoginViewModel provideLoginViewModel() {
        return new LoginViewModel(
                todoCoordinator,
                presentationModule.provideAuthenticateUser(),
                UserSession.getInstance()
        );
    }

    public SplashViewModel provideSplashViewModel() {
        return new SplashViewModel(
                todoCoordinator
        );
    }

    public HomeScreenViewModel provideHomeScreenViewModel() {
        return new HomeScreenViewModel(
                presentationModule.provideGetTodoList(),
                todoCoordinator,
                UserSession.getInstance(),
                UserComponent.getInstance()
        );
    }

    public Navigator getNavigator() {
        return navigator;
    }

    public TodoCoordinator getTodoCoordinator() {
        return todoCoordinator;
    }

}
