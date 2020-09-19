package com.android.basics.domain.interactor.todo;

import androidx.core.util.Preconditions;

import com.android.basics.core.domain.executor.PostExecutionThread;
import com.android.basics.core.domain.executor.ThreadExecutor;
import com.android.basics.core.domain.interactor.CompletableUseCase;
import com.android.basics.domain.repository.TodoRepository;

import javax.inject.Inject;

import io.reactivex.Completable;

public class DeleteTodoInteractor extends CompletableUseCase<Integer> {

    private TodoRepository todoRepository;

    @Inject
    public DeleteTodoInteractor(TodoRepository todoRepository, ThreadExecutor threadExecutor,
                                PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.todoRepository = todoRepository;
    }

    @Override
    public Completable buildUseCaseObservable(Integer params) {
        Preconditions.checkNotNull(params);
        return todoRepository.deleteTodo(params);
    }
}
