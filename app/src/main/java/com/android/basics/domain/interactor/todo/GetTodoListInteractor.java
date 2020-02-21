package com.android.basics.domain.interactor.todo;

import androidx.core.util.Preconditions;

import com.android.basics.core.domain.executor.PostExecutionThread;
import com.android.basics.core.domain.executor.ThreadExecutor;
import com.android.basics.core.domain.interactor.FlowableUseCase;
import com.android.basics.domain.model.Todo;
import com.android.basics.domain.repository.TodoRepository;

import java.util.List;

import io.reactivex.Flowable;

public class GetTodoListInteractor extends FlowableUseCase<List<Todo>, GetTodoListInteractor.Params> {

    private TodoRepository todoRepository;

    public GetTodoListInteractor(TodoRepository todoRepository, ThreadExecutor threadExecutor,
                                 PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.todoRepository = todoRepository;
    }

    @Override
    public Flowable<List<Todo>> buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        return todoRepository.getTodoList(params.userId);
    }


    public static final class Params {

        private final int userId;

        private Params(int userId) {
            this.userId = userId;
        }

        public static Params forUser(int userId) {
            return new Params(userId);
        }
    }
}
