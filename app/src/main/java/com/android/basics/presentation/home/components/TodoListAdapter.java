package com.android.basics.presentation.home.components;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.basics.R;
import com.android.basics.di.TodoComponent;
import com.android.basics.domain.model.Todo;
import com.android.basics.presentation.components.BaseViewHolder;
import com.android.basics.presentation.components.TodoSession;
import com.android.basics.presentation.home.HomeScreenContract;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Todo> todoList;

    private HomeScreenContract.Navigator navigator;

    private TodoComponent scope;

    private TodoSession session;

    public TodoListAdapter(List<Todo> todoList, HomeScreenContract.Navigator navigator, TodoSession todoSession, TodoComponent scope) {
        this.todoList = todoList;
        this.navigator = navigator;
        this.scope = scope;
        this.session = todoSession;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void addItems(List<Todo> todoList) {
        this.todoList.addAll(todoList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends BaseViewHolder {

        TextView txtName;
        TextView txtDate;
        TextView txtDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_name);
            txtDate = itemView.findViewById(R.id.txt_date);
            txtDescription = itemView.findViewById(R.id.txt_description);
        }

        public void onBind(int position) {
            super.onBind(position);
            final Todo todo = todoList.get(position);

            txtName.setText(todo.getName());
            txtDate.setText(todo.getDueDate());
            txtDescription.setText(todo.getDescription());

            itemView.setOnClickListener(view -> {
                scope.end();
                TodoSession.getInstance().setTodo(todo);
                navigator.goToEditTodoScreen();
            });
        }

        @Override
        protected void clear() {
            txtName.setText("");
            txtDate.setText("");
            txtDescription.setText("");
        }
    }
}
