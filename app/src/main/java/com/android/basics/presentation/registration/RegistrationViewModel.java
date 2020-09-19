package com.android.basics.presentation.registration;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.basics.core.presentation.AuthResource;
import com.android.basics.domain.interactor.user.AuthenticateUserInteractor;
import com.android.basics.domain.interactor.user.RegisterUserInteractor;
import com.android.basics.domain.model.User;
import com.android.basics.presentation.SessionManager;
import com.android.basics.presentation.TodoCoordinator;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;

public class RegistrationViewModel extends ViewModel {

    private RegisterUserInteractor registerUserInteractor;
    private AuthenticateUserInteractor authenticateUserInteractor;

    private RegisterUserContract.Navigator coordinator;
    private SessionManager sessionManager;

    @Inject
    public RegistrationViewModel(RegisterUserInteractor registerUserInteractor,
                                 AuthenticateUserInteractor authenticateUserInteractor,
                                 TodoCoordinator coordinator,
                                 SessionManager sessionManager) {
        this.registerUserInteractor = registerUserInteractor;
        this.authenticateUserInteractor = authenticateUserInteractor;
        this.coordinator = coordinator;
        this.sessionManager = sessionManager;
    }

    public LiveData<AuthResource<User>> getState() {
        return sessionManager.getUserAuthState();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        this.registerUserInteractor.dispose();
        this.authenticateUserInteractor.dispose();
        this.coordinator = null;
    }

    @SuppressLint("CheckResult")

    public void onRegisterClick(String userName, String password) {
        sessionManager.update(AuthResource.loading());
        registerUserInteractor.execute(new RegisterObserver(), RegisterUserInteractor.Params.forUser(userName, password))
                .andThen(authenticateUserInteractor.execute(new AuthenticateObserver(), AuthenticateUserInteractor.Params.forUser(userName, password)));
    }

    public void onLoginClick() {
        coordinator.goToLoginScreen();
    }


    public void onRegistrationSuccess() {
        coordinator.goToHomeScreen();
    }

    private final class RegisterObserver extends DisposableCompletableObserver {
        @Override
        public void onComplete() {

        }

        @Override
        public void onError(Throwable e) {

        }
    }

    private final class AuthenticateObserver extends DisposableSingleObserver<User> {
        @Override
        public void onSuccess(User user) {
            sessionManager.update(AuthResource.authenticated(user));
        }

        @Override
        public void onError(Throwable e) {
            sessionManager.update(AuthResource.error(e.getMessage()));
        }
    }


}
