package com.android.basics.presentation.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.basics.core.presentation.Resource;
import com.android.basics.domain.interactor.user.AuthenticateUserInteractor;
import com.android.basics.domain.model.User;
import com.android.basics.presentation.components.UserSession;

import io.reactivex.observers.DisposableSingleObserver;

public class LoginViewModel extends ViewModel {
    private LoginContract.Navigator coordinator;
    private AuthenticateUserInteractor authenticateUserInteractor;
    private UserSession userSession;

    private MutableLiveData<Resource<User>> state = new MutableLiveData<>();

    public LoginViewModel(LoginContract.Navigator coordinator, AuthenticateUserInteractor authenticateUserInteractor, UserSession session) {
        this.coordinator = coordinator;
        this.authenticateUserInteractor = authenticateUserInteractor;
        this.userSession = session;
    }

    public MutableLiveData<Resource<User>> getState() {
        return state;
    }

    public void OnLoginClick(String userName, String password) {
        state.postValue(Resource.loading());
        authenticateUserInteractor.execute(new AuthenticateObserver(), AuthenticateUserInteractor.Params.forUser(userName, password));
    }

    public void onRegisterClick() {
        coordinator.goToRegisterScreen();
    }

    private final class AuthenticateObserver extends DisposableSingleObserver<User> {

        @Override
        public void onSuccess(User user) {
            userSession.setUser(user);
            state.postValue(Resource.success(user));
            coordinator.goToHomeScreen();
        }

        @Override
        public void onError(Throwable e) {
            state.postValue(Resource.error(e.getMessage()));
        }
    }
}
