package com.android.basics.data.repository;

import com.android.basics.data.component.DaoExecutor;
import com.android.basics.data.mapper.TodoListMapper;
import com.android.basics.data.mapper.TodoMapper;
import com.android.basics.data.source.dao.TodoDao;
import com.android.basics.domain.model.Todo;
import com.android.basics.domain.repository.TodoRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class TodoDataRespository implements TodoRepository {

    private TodoDao todoDao;
    private TodoListMapper todoListMapper;
    private TodoMapper todoMapper;

    public TodoDataRespository(DaoExecutor daoExecutor, TodoDao todoDao, TodoListMapper todoListMapper, TodoMapper todoMapper) {
        this.todoDao = todoDao;
        this.todoListMapper = todoListMapper;
        this.todoMapper = todoMapper;
    }

    @Override
    public Flowable<List<Todo>> getTodoList(int userId) {
        return todoDao.getAllTodo(userId).map(this.todoListMapper::convert);
    }

    @Override
    public Flowable<Todo> getTodo(int todoId) {
        return todoDao.getTodo(todoId).map(this.todoMapper::convert);
    }

    @Override
    public Completable editTodo(Todo todo) {
        return todoDao.update(this.todoMapper.invert(todo));
    }

    @Override
    public Completable addTodo(int userId, String name, String desctiption, String date) {
        return todoDao.insert(userId, name, desctiption, date, false);
    }

    @Override
    public Completable deleteTodo(int todoId) {
        return todoDao.delete(todoId);
    }


}
