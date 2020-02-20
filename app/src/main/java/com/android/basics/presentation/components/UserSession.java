package com.android.basics.presentation.components;

import com.android.basics.di.internal.ScopeObserver;
import com.android.basics.di.UserComponent;
import com.android.basics.domain.model.Todo;
import com.android.basics.domain.model.User;

import java.util.List;

public class UserSession implements ScopeObserver {

    private User user;

    private List<Todo> todoList;

    public static UserSession getInstance() {
        if (!UserComponent.getInstance().getContainer().has(UserSession.class)) {
            UserComponent.getInstance().getContainer().register(UserSession.class, new UserSession());
        }
        return UserComponent.getInstance().getContainer().get(UserSession.class);
    }

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

    @Override
    public void onScopeEnded() {
        user = null;
    }
}
