package com.android.basics.domain.repository;

import com.android.basics.domain.model.User;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface UserRepository {
    Single<User> authenticate(String userName, String password);

    Completable register(String userName, String password);

}
