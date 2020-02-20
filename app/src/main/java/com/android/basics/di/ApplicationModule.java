package com.android.basics.di;

import android.app.Application;

import com.android.basics.core.domain.executor.PostExecutionThread;
import com.android.basics.core.domain.executor.ThreadExecutor;
import com.android.basics.core.navigation.BundleFactory;
import com.android.basics.core.navigation.IntentFactory;
import com.android.basics.core.navigation.NativeBundleFactory;
import com.android.basics.core.navigation.NativeIntentFactory;
import com.android.basics.core.navigation.Navigator;
import com.android.basics.data.component.DaoExecutor;
import com.android.basics.data.mapper.TodoListMapper;
import com.android.basics.data.mapper.TodoMapper;
import com.android.basics.data.mapper.UserMapper;
import com.android.basics.data.repository.TodoDataRespository;
import com.android.basics.data.repository.UserDataRespository;
import com.android.basics.data.source.TodoDatabase;
import com.android.basics.data.source.dao.TodoDao;
import com.android.basics.data.source.dao.UserDao;
import com.android.basics.domain.repository.TodoRepository;
import com.android.basics.domain.repository.UserRepository;
import com.android.basics.presentation.components.JobExecutor;
import com.android.basics.presentation.components.UiThread;

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

    public Navigator provideNavigator(IntentFactory intentFactory, BundleFactory bundleFactory) {
        return new Navigator(intentFactory, bundleFactory);
    }

    public UserRepository provideUserRepository(DaoExecutor daoExecutor, UserDao userDao, UserMapper userMapper) {
        return new UserDataRespository(daoExecutor, userDao, userMapper);
    }

    public TodoRepository provideTodoRepository(DaoExecutor daoExecutor, TodoDao userDao, TodoListMapper todoListMapper, TodoMapper todoMapper) {
        return new TodoDataRespository(daoExecutor, userDao, todoListMapper, todoMapper);
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

    public TodoDao provideTodoDao(TodoDatabase database) {
        return database.todoDao();
    }

    public UserMapper provideUserMapper() {
        return new UserMapper();
    }

    public TodoListMapper provideTodoListMapper() {
        return new TodoListMapper();
    }

    public TodoMapper provideTodoMapper() {
        return new TodoMapper();
    }

    public Application getApplication() {
        return application;
    }

    public ThreadExecutor provideThreadExecutor() {
        return new JobExecutor();
    }

    public PostExecutionThread ProvidePostExecutionThread() {
        return new UiThread();
    }

}
