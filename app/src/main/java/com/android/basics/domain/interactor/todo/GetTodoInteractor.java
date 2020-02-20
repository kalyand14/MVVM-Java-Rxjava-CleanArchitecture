package com.android.basics.domain.interactor.todo;

import androidx.core.util.Preconditions;

import com.android.basics.core.domain.executor.PostExecutionThread;
import com.android.basics.core.domain.executor.ThreadExecutor;
import com.android.basics.core.domain.interactor.FlowableUseCase;
import com.android.basics.domain.model.Todo;
import com.android.basics.domain.repository.TodoRepository;

import io.reactivex.Flowable;

public class GetTodoInteractor extends FlowableUseCase<Todo, Integer> {

    private TodoRepository todoRepository;

    public GetTodoInteractor(TodoRepository todoRepository, ThreadExecutor threadExecutor,
                             PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.todoRepository = todoRepository;
    }


    @Override
    public Flowable<Todo> buildUseCaseObservable(Integer params) {
        Preconditions.checkNotNull(params);
        return todoRepository.getTodo(params);
    }
}
