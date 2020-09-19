package com.android.basics.dagger.main;

import com.android.basics.presentation.TodoCoordinator;
import com.android.basics.presentation.components.TodoCache;
import com.android.basics.presentation.components.UserCache;
import com.android.basics.presentation.home.components.TodoListAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class MainModule {

    @Provides
    static TodoListAdapter provideTodoListAdapter(TodoCoordinator navigator, UserCache userCache) {
        return new TodoListAdapter(navigator, userCache);
    }


}
