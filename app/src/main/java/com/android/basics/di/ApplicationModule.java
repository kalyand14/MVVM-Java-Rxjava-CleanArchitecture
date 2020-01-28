package com.android.basics.di;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

import com.android.basics.core.navigation.BundleFactory;
import com.android.basics.core.navigation.IntentFactory;
import com.android.basics.core.navigation.NativeBundleFactory;
import com.android.basics.core.navigation.NativeIntentFactory;
import com.android.basics.core.navigation.Navigator;
import com.android.basics.data.component.DaoExecutor;
import com.android.basics.data.mapper.UserMapper;
import com.android.basics.data.repository.UserDataRespository;
import com.android.basics.data.source.TodoDatabase;
import com.android.basics.data.source.dao.UserDao;
import com.android.basics.domain.repository.UserRepository;

public class ApplicationModule {


    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    public IntentFactory provideIntentFactory() {
        return new NativeIntentFactory();
    }

    public BundleFactory provideBundleFactory() {
        return new NativeBundleFactory();
    }

    public Navigator provideNavigator(AppCompatActivity activity, IntentFactory intentFactory, BundleFactory bundleFactory) {
        return new Navigator(activity, intentFactory, bundleFactory);
    }



    public UserRepository provideUserRepository(DaoExecutor daoExecutor, UserDao userDao, UserMapper userMapper) {
        return new UserDataRespository(daoExecutor, userDao, userMapper);
    }

    public DaoExecutor provideDaoExecutor() {
        return new DaoExecutor();
    }

    public TodoDatabase provideTodoDatabase(Application application) {
        return TodoDatabase.getDatabase(application.getApplicationContext());
    }

    public UserDao provideUserDao(TodoDatabase database) {
        return database.userDao();
    }

    public UserMapper provideUserMapper() {
        return new UserMapper();
    }

    public Application getApplication() {
        return application;
    }
}
