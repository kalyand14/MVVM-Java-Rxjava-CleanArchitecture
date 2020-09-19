package com.android.basics.domain.interactor.todo;

import androidx.core.util.Preconditions;

import com.android.basics.core.domain.executor.PostExecutionThread;
import com.android.basics.core.domain.executor.ThreadExecutor;
import com.android.basics.core.domain.interactor.CompletableUseCase;
import com.android.basics.domain.model.Todo;
import com.android.basics.domain.repository.TodoRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class EditTodoInteractor extends CompletableUseCase<Todo> {

    private TodoRepository todoRepository;

    @Inject
    public EditTodoInteractor(TodoRepository todoRepository, ThreadExecutor threadExecutor,
                              PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.todoRepository = todoRepository;
    }

    @Override
    public Completable buildUseCaseObservable(Todo params) {
        Preconditions.checkNotNull(params);
        return todoRepository.editTodo(params);
    }
}
