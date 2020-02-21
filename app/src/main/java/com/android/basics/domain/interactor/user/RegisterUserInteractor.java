package com.android.basics.domain.interactor.user;

import androidx.core.util.Preconditions;

import com.android.basics.core.domain.executor.PostExecutionThread;
import com.android.basics.core.domain.executor.ThreadExecutor;
import com.android.basics.core.domain.interactor.CompletableUseCase;
import com.android.basics.domain.repository.UserRepository;

import io.reactivex.Completable;

public class RegisterUserInteractor extends CompletableUseCase<RegisterUserInteractor.Params> {

    private UserRepository userRepository;

    public RegisterUserInteractor(UserRepository userRepository, ThreadExecutor threadExecutor,
                                  PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.userRepository = userRepository;
    }

    @Override
    protected Completable buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        return this.userRepository.register(params.userName, params.password);
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
