package com.android.basics.presentation.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.basics.R;
import com.android.basics.core.presentation.Resource;
import com.android.basics.domain.model.Todo;
import com.android.basics.presentation.BaseActivity;
import com.android.basics.presentation.UserActivity;
import com.android.basics.presentation.home.components.TodoListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;

public class HomeActivity extends UserActivity<HomeScreenViewModel> {

    private ProgressDialog progressDialog;

    private RecyclerView recyclerView;

    @Inject
    TodoListAdapter todoListAdapter;

    private LinearLayoutManager layoutManager;
    private TextView txtError;
    private FloatingActionButton floatingActionButton;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        builder = new AlertDialog.Builder(this);

        floatingActionButton = findViewById(R.id.fab);
        txtError = findViewById(R.id.tv_message);
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging in");

        recyclerView.setAdapter(todoListAdapter);

        viewModel.onLoadTodoList();

        floatingActionButton.setOnClickListener(view -> viewModel.onAddTodo());
    }

    @Override
    protected Integer getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    protected Class<? extends HomeScreenViewModel> getViewModel() {
        return HomeScreenViewModel.class;
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.getWelcomeMessageEvent().observe(this, this::setWelcomeMessage);
        viewModel.getState().observe(this, it -> {
            if (it != null) {
                handleState(it.getState(), it.getData(), it.getMessage());
            }
        });
        viewModel.getLoggedOutEvent().observe(this, it -> {
            showLogoutConfirmationDialog();
        });
    }

    private void handleState(Resource.ResourceState state, List<Todo> todoList, String errorMessage) {
        switch (state) {
            case SUCCESS:
                dismissProgressDialog();
                showList(true);
                showErrorLayout(false);
                loadTodoList(todoList);
                break;
            case ERROR:
                dismissProgressDialog();
                showList(false);
                showErrorLayout(true);
                break;
            case LOADING:
                showProgressDialog();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissProgressDialog();
    }


    public void showProgressDialog() {
        progressDialog.setMessage("Loading todo list");
        progressDialog.show();
    }


    public void dismissProgressDialog() {
        progressDialog.dismiss();
        progressDialog.cancel();
    }


    public void showErrorLayout(boolean display) {
        txtError.setVisibility(display ? View.VISIBLE : View.GONE);
    }


    public void showList(boolean display) {
        recyclerView.setVisibility(display ? View.VISIBLE : View.GONE);
    }


    public void loadTodoList(List<Todo> todoList) {
        todoListAdapter.addItems(todoList);
    }


    public void setWelcomeMessage(String message) {
        setTitle(message);
    }


    public void showLogoutConfirmationDialog() {
        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to log out?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    dialog.dismiss();
                    viewModel.logout();
                })
                .setNegativeButton("No", (dialog, id) -> {
                    dialog.dismiss();
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setTitle("Logout");
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                viewModel.onLogout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
