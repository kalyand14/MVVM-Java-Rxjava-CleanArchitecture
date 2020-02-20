package com.android.basics.domain.repository;

import com.android.basics.domain.model.Todo;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface TodoRepository {
    Flowable<List<Todo>> getTodoList(final int userId);

    Flowable<Todo> getTodo(int todoId);

    Completable editTodo(Todo todo);

    Completable addTodo(int userId, String name, String desctiption, String date);

    Completable deleteTodo(final int todoId);
}
