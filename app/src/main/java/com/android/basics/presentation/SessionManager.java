package com.android.basics.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.basics.core.presentation.AuthResource;
import com.android.basics.domain.model.User;
import com.android.basics.presentation.components.UserCache;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {

    private MutableLiveData<AuthResource<User>> cachedUser = new MutableLiveData<>();

    public UserCache getUserCache() {
        return userCache;
    }

    public void setUserCache(UserCache userCache) {
        this.userCache = userCache;
    }

    private UserCache userCache;

    @Inject
    public SessionManager(UserCache userCache) {
        this.userCache = userCache;
    }

    public void update(final AuthResource<User> user) {
        cachedUser.setValue(user);

        if(user.getState() == AuthResource.AuthResourceState.AUTHENTICATED){
          this.userCache.setUser(user.getData());
        }

    }

    public void logout() {
        this.userCache.clear();
        cachedUser.setValue(AuthResource.logout());
    }

    public LiveData<AuthResource<User>> getUserAuthState() {
        return cachedUser;
    }

}
