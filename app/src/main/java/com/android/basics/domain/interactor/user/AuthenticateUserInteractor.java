package com.android.basics.domain.interactor.user;

import androidx.core.util.Preconditions;

import com.android.basics.core.domain.executor.PostExecutionThread;
import com.android.basics.core.domain.executor.ThreadExecutor;
import com.android.basics.core.domain.interactor.FlowableUseCase;
import com.android.basics.core.domain.interactor.SingleUseCase;
import com.android.basics.domain.model.User;
import com.android.basics.domain.repository.UserRepository;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class AuthenticateUserInteractor extends SingleUseCase<User, AuthenticateUserInteractor.Params> {

    private UserRepository userRepository;

    @Inject
    public AuthenticateUserInteractor(UserRepository userRepository, ThreadExecutor threadExecutor,
                                      PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    protected Single<User> buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        return this.userRepository.authenticate(params.userName, params.password);
    }


    public static final class Params {
        private String userName;
        private String password;

        private Params(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        public static Params forUser(String userName, String password) {
            return new Params(userName, password);
        }
    }
}
