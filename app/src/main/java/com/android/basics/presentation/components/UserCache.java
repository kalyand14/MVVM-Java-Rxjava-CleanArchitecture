package com.android.basics.presentation.components;

import com.android.basics.domain.model.Todo;
import com.android.basics.domain.model.User;

import java.util.List;


public class UserCache {

    private User user;

    private List<Todo> todoList;

    public int getSelectedTodoId() {
        return selectedTodoId;
    }

    public void setSelectedTodoId(int selectedTodoId) {
        this.selectedTodoId = selectedTodoId;
    }

    private int selectedTodoId;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public List<Todo> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<Todo> todoList) {
        this.todoList = todoList;
    }

    public Todo getTodoById(int selectedTodoId) {
        for (Todo todo : todoList) {
            if (todo.getTodoId() == selectedTodoId)
                return todo;
        }
        return null;
    }

    public void clear(){
        this.user = null;
        this.todoList = null;
        this.selectedTodoId = -1;
    }
}
