package com.android.basics.dagger.todo;

import com.android.basics.dagger.main.MainScope;
import com.android.basics.presentation.components.TodoCache;
import com.android.basics.presentation.components.UserCache;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class TodoModule {

    @Provides
    @TodoScope
    static TodoCache providerTodoCache() {
        return new TodoCache();
    }

}
