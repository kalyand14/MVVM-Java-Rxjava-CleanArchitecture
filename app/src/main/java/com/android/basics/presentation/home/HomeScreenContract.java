package com.android.basics.presentation.home;

import com.android.basics.core.mvp.BasePresenter;
import com.android.basics.domain.model.Todo;

import java.util.List;

public interface HomeScreenContract {

    interface View {
        void showProgressDialog();

        void dismissProgressDialog();

        void showErrorLayout(boolean display);

        void showList(boolean display);

        void loadTodoList(List<Todo> todoList);

        void setWelcomeMessage(String message);
    }

    interface Presenter extends BasePresenter<View> {
        void onLoadTodoList(int userId);

        void onLogout();

        void onAddTodo();
    }

    interface Navigator {

        void goToEditTodoScreen(int todoId);

        void gotoAddTodoScreen();

        void goToLoginScreen();
    }
}
