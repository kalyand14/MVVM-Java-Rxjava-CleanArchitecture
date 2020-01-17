package com.android.basics.domain.repository;

import com.android.basics.core.Callback;
import com.android.basics.domain.model.User;

public interface UserRepository {
    void authenticate(User user, Callback<User> callback);

    void register(User user, Callback<User> callback);
}
