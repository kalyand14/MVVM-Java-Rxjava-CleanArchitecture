package com.android.basics.presentation.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.basics.core.presentation.AuthResource;
import com.android.basics.domain.interactor.user.AuthenticateUserInteractor;
import com.android.basics.domain.model.User;
import com.android.basics.presentation.SessionManager;
import com.android.basics.presentation.TodoCoordinator;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

public class LoginViewModel extends ViewModel {


    private LoginContract.Navigator coordinator;
    private AuthenticateUserInteractor authenticateUserInteractor;
    private SessionManager sessionManager;


    @Inject
    public LoginViewModel(TodoCoordinator coordinator, AuthenticateUserInteractor authenticateUserInteractor, SessionManager sessionManager) {
        this.coordinator = coordinator;
        this.authenticateUserInteractor = authenticateUserInteractor;
        this.sessionManager = sessionManager;
    }

    public LiveData<AuthResource<User>> getState() {
        return sessionManager.getUserAuthState();
    }

    public void OnLoginClick(String userName, String password) {
        sessionManager.update(AuthResource.loading());
        authenticateUserInteractor.execute(new AuthenticateObserver(), AuthenticateUserInteractor.Params.forUser(userName, password));
    }

    public void onRegisterClick() {
        coordinator.goToRegisterScreen();
    }

    private final class AuthenticateObserver extends DisposableSingleObserver<User> {

        @Override
        public void onSuccess(User user) {
            sessionManager.update(AuthResource.authenticated(user));
            coordinator.goToHomeScreen();
        }

        @Override
        public void onError(Throwable e) {
            sessionManager.update(AuthResource.error(e.getMessage()));
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        authenticateUserInteractor.dispose();
        coordinator = null;
    }
}
