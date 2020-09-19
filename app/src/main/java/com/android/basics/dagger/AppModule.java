package com.android.basics.dagger;

import android.app.Application;

import com.android.basics.core.domain.executor.PostExecutionThread;
import com.android.basics.core.domain.executor.ThreadExecutor;
import com.android.basics.core.navigation.BundleFactory;
import com.android.basics.core.navigation.IntentFactory;
import com.android.basics.core.navigation.NativeBundleFactory;
import com.android.basics.core.navigation.NativeIntentFactory;
import com.android.basics.core.navigation.Navigator;
import com.android.basics.dagger.main.MainScope;
import com.android.basics.data.mapper.TodoListMapper;
import com.android.basics.data.mapper.TodoMapper;
import com.android.basics.data.mapper.UserMapper;
import com.android.basics.data.repository.TodoDataRespository;
import com.android.basics.data.repository.UserDataRespository;
import com.android.basics.data.source.TodoDatabase;
import com.android.basics.domain.repository.TodoRepository;
import com.android.basics.domain.repository.UserRepository;
import com.android.basics.presentation.TodoCoordinator;
import com.android.basics.presentation.components.JobExecutor;
import com.android.basics.presentation.components.UiThread;
import com.android.basics.presentation.components.UserCache;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    static UserCache providerUserCache() {
        return new UserCache();
    }

    @Singleton
    @Provides
    static PostExecutionThread providePostExecutionThread() {
        return new UiThread();
    }

    @Singleton
    @Provides
    static ThreadExecutor providerThreadExecutor() {
        return new JobExecutor();
    }

    @Singleton
    @Provides
    static TodoMapper providerTodoMapper() {
        return new TodoMapper();
    }

    @Singleton
    @Provides
    static TodoListMapper provideTodoListMapper() {
        return new TodoListMapper();
    }

    @Singleton
    @Provides
    static UserMapper provideUserMapper() {
        return new UserMapper();
    }

    @Singleton
    @Provides
    static TodoDatabase provideTodoDatabase(Application application) {
        return TodoDatabase.getDatabase(application);
    }

    @Singleton
    @Provides
    static TodoRepository provideTodoRepository(TodoDatabase database, TodoListMapper todoListMapper, TodoMapper todoMapper) {
        return new TodoDataRespository(database.todoDao(), todoListMapper, todoMapper);
    }

    @Singleton
    @Provides
    static UserRepository provideUserRepository(TodoDatabase database, UserMapper userMapper) {
        return new UserDataRespository(database.userDao(), userMapper);
    }

    @Singleton
    @Provides
    static IntentFactory provideIntentFactory() {
        return new NativeIntentFactory();
    }

    @Singleton
    @Provides
    static BundleFactory provideBundleFactory() {
        return new NativeBundleFactory();
    }

    @Singleton
    @Provides
    static Navigator provideNavigator(IntentFactory intentFactory, BundleFactory bundleFactory) {
        return new Navigator(intentFactory, bundleFactory);
    }


    @Singleton
    @Provides
    static TodoCoordinator provideTodoCoordinator(Navigator navigator) {
        return new TodoCoordinator(navigator);
    }
}
