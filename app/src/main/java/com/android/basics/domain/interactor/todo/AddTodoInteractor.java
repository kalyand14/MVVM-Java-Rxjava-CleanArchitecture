package com.android.basics.domain.interactor.todo;

import androidx.core.util.Preconditions;

import com.android.basics.core.domain.executor.PostExecutionThread;
import com.android.basics.core.domain.executor.ThreadExecutor;
import com.android.basics.core.domain.interactor.CompletableUseCase;
import com.android.basics.domain.repository.TodoRepository;

import io.reactivex.Completable;

public class AddTodoInteractor extends CompletableUseCase<AddTodoInteractor.Params> {

    private TodoRepository todoRepository;

    public AddTodoInteractor(TodoRepository todoRepository, ThreadExecutor threadExecutor,
                             PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.todoRepository = todoRepository;
    }

    @Override
    public Completable buildUseCaseObservable(Params params) {
        Preconditions.checkNotNull(params);
        return todoRepository.addTodo(params.userId, params.name, params.description, params.date);
    }


    public static final class Params {
        private int userId;
        private String name;
        private String description;
        private String date;

        private Params(int userId, String name, String description, String date) {
            this.userId = userId;
            this.name = name;
            this.description = description;
            this.date = date;
        }

        public static AddTodoInteractor.Params forTodo(int userId, String name, String description, String date) {
            return new AddTodoInteractor.Params(userId, name, description, date);
        }
    }
}
