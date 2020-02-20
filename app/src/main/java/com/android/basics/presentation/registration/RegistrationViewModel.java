package com.android.basics.presentation.registration;

import android.annotation.SuppressLint;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.basics.core.presentation.Resource;
import com.android.basics.domain.interactor.user.AuthenticateUserInteractor;
import com.android.basics.domain.interactor.user.RegisterUserInteractor;
import com.android.basics.domain.model.User;
import com.android.basics.presentation.components.UserSession;

import io.reactivex.observers.DisposableSingleObserver;

public class RegistrationViewModel extends ViewModel {

    private RegisterUserInteractor registerUserInteractor;
    private AuthenticateUserInteractor authenticateUserInteractor;

    private RegisterUserContract.Navigator coordinator;
    private UserSession userSession;


    private MutableLiveData<Resource<User>> state = new MutableLiveData<>();

    public RegistrationViewModel(RegisterUserInteractor registerUserInteractor,
                                 AuthenticateUserInteractor authenticateUserInteractor,
                                 RegisterUserContract.Navigator coordinator,
                                 UserSession userSession) {
        this.registerUserInteractor = registerUserInteractor;
        this.authenticateUserInteractor = authenticateUserInteractor;
        this.coordinator = coordinator;
        this.userSession = userSession;
    }

    public MutableLiveData<Resource<User>> getState() {
        return state;
    }

    @Override
    protected void onCleared() {
        this.registerUserInteractor.dispose();
        super.onCleared();
    }

    @SuppressLint("CheckResult")

    public void onRegisterClick(String userName, String password) {
        state.postValue(Resource.loading());
        registerUserInteractor.execute(RegisterUserInteractor.Params.forUser(userName, password))
                .andThen(authenticateUserInteractor.execute(new AuthenticateObserver(), AuthenticateUserInteractor.Params.forUser(userName, password)));
    }

    public void onLoginClick() {
        coordinator.goToLoginScreen();
    }


    public void onRegistrationSuccess() {
        coordinator.goToHomeScreen();
    }

    private final class AuthenticateObserver extends DisposableSingleObserver<User> {

        @Override
        public void onSuccess(User user) {
            userSession.setUser(user);
            state.postValue(Resource.success(user));
        }

        @Override
        public void onError(Throwable e) {
            state.postValue(Resource.error(e.getMessage()));
        }
    }

}
