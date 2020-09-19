package com.android.basics.presentation.todo.add;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.basics.core.SingleLiveEvent;
import com.android.basics.core.presentation.Resource;
import com.android.basics.domain.interactor.todo.AddTodoInteractor;
import com.android.basics.presentation.TodoCoordinator;
import com.android.basics.presentation.components.UserCache;
import com.android.basics.presentation.components.UserSession;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;

public class AddTodoViewModel extends ViewModel {

    private AddTodoContract.Navigator navigator;
    private AddTodoInteractor addTodoInteractor;


    private UserCache userCache;
    private final SingleLiveEvent<Void> ShowDatePickerEvent = new SingleLiveEvent<>();
    private MutableLiveData<Resource<Void>> state = new MutableLiveData<>();


    @Inject
    public AddTodoViewModel(TodoCoordinator navigator,
                            AddTodoInteractor addTodoInteractor,
                            UserCache userCache) {
        this.navigator = navigator;
        this.addTodoInteractor = addTodoInteractor;
        this.userCache = userCache;
    }

    public SingleLiveEvent<Void> getShowDatePickerEvent() {
        return ShowDatePickerEvent;
    }

    public MutableLiveData<Resource<Void>> getState() {
        return state;
    }

    public void onSubmit(String name, String desc, String date) {
        state.postValue(Resource.loading());
        addTodoInteractor.execute(new AddTodoObserver(), AddTodoInteractor.Params.forTodo(userCache.getUser().getUserId(), name, desc, date));
    }

    public void navigate() {
        navigator.goToHomeScreen();
    }

    public void OnCancel() {
        navigator.goToHomeScreen();
    }

    public void onSelectDate() {
        ShowDatePickerEvent.call();
    }

    private final class AddTodoObserver extends DisposableCompletableObserver {

        @Override
        public void onComplete() {
            state.postValue(Resource.success(null));
        }

        @Override
        public void onError(Throwable e) {
            state.postValue(Resource.error(e.getMessage()));
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        navigator = null;
        addTodoInteractor.dispose();
    }
}
