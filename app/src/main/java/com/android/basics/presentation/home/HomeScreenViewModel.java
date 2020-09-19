package com.android.basics.presentation.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.basics.core.SingleLiveEvent;
import com.android.basics.core.presentation.Resource;
import com.android.basics.domain.interactor.todo.GetTodoListInteractor;
import com.android.basics.domain.model.Todo;
import com.android.basics.domain.model.User;
import com.android.basics.presentation.SessionManager;
import com.android.basics.presentation.TodoCoordinator;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.subscribers.DisposableSubscriber;

public class HomeScreenViewModel extends ViewModel {

    private final GetTodoListInteractor getTodoListInteractor;
    private HomeScreenContract.Navigator navigator;
    private final SessionManager sessionManager;

    private final MutableLiveData<Resource<List<Todo>>> state = new MutableLiveData<>();
    private final SingleLiveEvent<Void> loggedOutEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<String> welcomeMessageEvent = new SingleLiveEvent<>();

    private User user;

    @Inject
    public HomeScreenViewModel(
            GetTodoListInteractor getTodoListInteractor,
            TodoCoordinator navigator,
            SessionManager sessionManager
    ) {
        this.getTodoListInteractor = getTodoListInteractor;
        this.navigator = navigator;
        this.sessionManager = sessionManager;
    }

    public void onLoadTodoList() {

        user =  this.sessionManager.getUserCache().getUser();

        welcomeMessageEvent.setValue("Welcome " + user.getUserName());

        state.postValue(Resource.loading());
        getTodoListInteractor.execute(new GetTodoObserver(), GetTodoListInteractor.Params.forUser(user.getUserId()));
    }

    public void onLogout() {
        //loggedOutEvent.call();
        this.sessionManager.logout();
    }

    public void logout() {
        //navigator.goToLoginScreen();
    }

    public void onAddTodo() {
        navigator.gotoAddTodoScreen();
    }

    public SingleLiveEvent<String> getWelcomeMessageEvent() {
        return welcomeMessageEvent;
    }

    public MutableLiveData<Resource<List<Todo>>> getState() {
        return state;
    }

    private final class GetTodoObserver extends DisposableSubscriber<List<Todo>> {

        @Override
        public void onNext(List<Todo> todoList) {
            if (todoList != null && todoList.size() > 0) {

                //session.setTodoList(todoList);

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

    @Override
    protected void onCleared() {
        super.onCleared();
        getTodoListInteractor.dispose();
        navigator = null;
    }
}
