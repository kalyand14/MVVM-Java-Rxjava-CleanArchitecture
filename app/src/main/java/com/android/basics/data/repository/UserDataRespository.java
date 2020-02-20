package com.android.basics.data.repository;

import com.android.basics.data.component.DaoExecutor;
import com.android.basics.data.mapper.UserMapper;
import com.android.basics.data.source.dao.UserDao;
import com.android.basics.domain.model.User;
import com.android.basics.domain.repository.UserRepository;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public class UserDataRespository implements UserRepository {

    private UserDao userDao;
    private UserMapper userMapper;

    public UserDataRespository(DaoExecutor daoExecutor, UserDao userDao, UserMapper userMapper) {
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    @Override
    public Single<User> authenticate(String userName, String password) {
        return userDao.getUser(userName, password).map(this.userMapper::convert);
    }

    @Override
    public Completable register(String userName, String password) {
        return userDao.insert(userName, password);
    }
}
