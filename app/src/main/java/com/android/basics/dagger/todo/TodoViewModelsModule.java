package com.android.basics.dagger.todo;

import androidx.lifecycle.ViewModel;

import com.android.basics.dagger.ViewModelKey;
import com.android.basics.presentation.todo.add.AddTodoViewModel;
import com.android.basics.presentation.todo.edit.EditTodoViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class TodoViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(AddTodoViewModel.class)
    public abstract ViewModel bindAddTodoViewModel(AddTodoViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EditTodoViewModel.class)
    public abstract ViewModel bindEditTodoViewModel(EditTodoViewModel viewModel);
}
