package com.android.basics.presentation.todo.edit;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.basics.core.SingleLiveEvent;
import com.android.basics.core.presentation.Resource;
import com.android.basics.domain.interactor.todo.DeleteTodoInteractor;
import com.android.basics.domain.interactor.todo.EditTodoInteractor;
import com.android.basics.domain.model.Todo;
import com.android.basics.presentation.components.TodoSession;

import io.reactivex.observers.DisposableCompletableObserver;

public class EditTodoViewModel extends ViewModel {

    private EditTodoInteractor editTodoInteractor;
    private DeleteTodoInteractor deleteTodoInteractor;
    private EditTodoContract.Navigator navigator;
    private TodoSession session;


    private final SingleLiveEvent<Void> ShowDatePickerEvent = new SingleLiveEvent<>();
    private final SingleLiveEvent<Todo> LoadTodoEvent = new SingleLiveEvent<>();
    private MutableLiveData<Resource<Void>> editTodoState = new MutableLiveData<>();
    private MutableLiveData<Resource<Void>> deleteTodoState = new MutableLiveData<>();


    public EditTodoViewModel(EditTodoInteractor editTodoInteractor,
                             DeleteTodoInteractor deleteTodoInteractor,
                             EditTodoContract.Navigator navigator,
                             TodoSession session) {
        this.editTodoInteractor = editTodoInteractor;
        this.deleteTodoInteractor = deleteTodoInteractor;
        this.navigator = navigator;
        this.session = session;
    }

    public SingleLiveEvent<Void> getShowDatePickerEvent() {
        return ShowDatePickerEvent;
    }

    public SingleLiveEvent<Todo> getLoadTodoEvent() {
        return LoadTodoEvent;
    }

    public MutableLiveData<Resource<Void>> getEditTodoState() {
        return editTodoState;
    }

    public MutableLiveData<Resource<Void>> getDeleteTodoState() {
        return deleteTodoState;
    }

    public void loadTodo() {
        Todo todo = TodoSession.getInstance().getTodo();
        LoadTodoEvent.setValue(todo);
    }

    public void onSubmit(String name, String desc, String date) {
        editTodoState.postValue(Resource.loading());
        Todo todo = TodoSession.getInstance().getTodo();
        todo.setName(name);
        todo.setDescription(desc);
        todo.setDueDate(date);
        editTodoInteractor.execute(new EditTodoObserver(), todo);
    }

    public void navigate() {
        navigator.goToHomeScreen();
    }

    public void OnCancel() {
        navigator.goToHomeScreen();
    }

    public void onDelete() {
        deleteTodoState.postValue(Resource.loading());
        deleteTodoInteractor.execute(new DeleteTodoObserver(), session.getTodo().getTodoId());
    }

    public void onSelectDate() {
        ShowDatePickerEvent.call();
    }

    private final class EditTodoObserver extends DisposableCompletableObserver {
        @Override
        public void onComplete() {
            editTodoState.postValue(Resource.success(null));
        }

        @Override
        public void onError(Throwable e) {
            editTodoState.postValue(Resource.error(e.getMessage()));
        }
    }

    private final class DeleteTodoObserver extends DisposableCompletableObserver {
        @Override
        public void onComplete() {
            deleteTodoState.postValue(Resource.success(null));
        }

        @Override
        public void onError(Throwable e) {
            deleteTodoState.postValue(Resource.error(e.getMessage()));
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        navigator = null;
        editTodoInteractor.dispose();
        deleteTodoInteractor.dispose();
    }
}
