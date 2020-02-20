package com.android.basics.presentation.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.basics.core.SingleLiveEvent;
import com.android.basics.core.presentation.Resource;
import com.android.basics.di.UserComponent;
import com.android.basics.domain.interactor.todo.GetTodoListInteractor;
import com.android.basics.domain.model.Todo;
import com.android.basics.presentation.components.UserSession;

import java.util.List;

import io.reactivex.subscribers.DisposableSubscriber;

public class HomeScreenViewModel extends ViewModel {

    private final GetTodoListInteractor getTodoListInteractor;
    private final HomeScreenContract.Navigator navigator;
    private final UserSession session;
    private final UserComponent userComponent;

    private final MutableLiveData<Resource<List<Todo>>> state = new MutableLiveData<>();
    private final SingleLiveEvent<Void> loggedOutEvent = new SingleLiveEvent<>();

    public HomeScreenViewModel(
            GetTodoListInteractor getTodoListInteractor,
            HomeScreenContract.Navigator navigator,
            UserSession session,
            UserComponent userComponent
    ) {
        this.getTodoListInteractor = getTodoListInteractor;
        this.navigator = navigator;
        this.session = session;
        this.userComponent = userComponent;
    }

    public void onLoadTodoList(int userId) {
        state.postValue(Resource.loading());
        getTodoListInteractor.execute(new GetTodoObserver(), GetTodoListInteractor.Params.forUser(userId));
    }

    public void onLogout() {
        loggedOutEvent.call();
    }

    public void logout() {
        userComponent.end();
        navigator.goToLoginScreen();
    }

    public void onAddTodo() {
        navigator.gotoAddTodoScreen();
    }

    public MutableLiveData<Resource<List<Todo>>> getState() {
        return state;
    }

    private final class GetTodoObserver extends DisposableSubscriber<List<Todo>> {

        @Override
        public void onNext(List<Todo> todoList) {
            if (todoList != null && todoList.size() > 0) {
                session.setTodoList(todoList);
                state.postValue(Resource.success(todoList));
            } else {
                state.postValue(Resource.error("No data found"));
            }
        }

        @Override
        public void onError(Throwable e) {
            state.postValue(Resource.error(e.getMessage()));
        }

        @Override
        public void onComplete() {

        }
    }

    public SingleLiveEvent<Void> getLoggedOutEvent() {
        return loggedOutEvent;
    }
}
