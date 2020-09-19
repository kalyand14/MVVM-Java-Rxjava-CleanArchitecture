package com.android.basics.presentation.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.android.basics.R;
import com.android.basics.core.presentation.AuthResource;
import com.android.basics.domain.model.User;
import com.android.basics.presentation.BaseActivity;

public class LoginActivity extends BaseActivity<LoginViewModel> {

    private ProgressDialog progressDialog;

    private Button btnLogin;
    private Button btnRegister;

    private EditText edtUserName;
    private EditText edtPassword;

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnLogin = findViewById(R.id.btn_add_todo);
        btnRegister = findViewById(R.id.btn_signup);
        edtUserName = findViewById(R.id.edt_todo_name);
        edtPassword = findViewById(R.id.edt_todo_description);
        builder = new AlertDialog.Builder(this);

        btnLogin.setOnClickListener(view -> viewModel.OnLoginClick(edtUserName.getText().toString(), edtPassword.getText().toString()));
        btnRegister.setOnClickListener(view -> viewModel.onRegisterClick());

        //UserComponent.getInstance().end();

        //LoginInjector.getInstance().inject(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging in");

    }

    @Override
    protected Integer getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected Class<? extends LoginViewModel> getViewModel() {
        return LoginViewModel.class;
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissProgressDialog();
    }

    public void showProgressDialog() {
        progressDialog.setMessage("Logging in");
        progressDialog.show();
    }


    public void dismissProgressDialog() {
        progressDialog.dismiss();
        progressDialog.cancel();
    }


    public void showAuthenticationError() {

        edtUserName.setText("");
        edtPassword.setText("");

        //Setting message manually and performing action on button click
        builder.setMessage("There was a problem logging in. Check your credentials or create an account.")
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, id) -> {
                    dialog.dismiss();
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setTitle("Error");
        alert.show();


    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.getState().observe(this, it -> {
            if (it != null) {
                handleState(it.getState(), it.getData(), it.getMessage());
            }
        });
    }

    private void handleState(AuthResource.AuthResourceState state, User user, String errorMessage) {
        switch (state) {
            case AUTHENTICATED:
                dismissProgressDialog();
                break;
            case ERROR:
                dismissProgressDialog();
                showAuthenticationError();
                break;
            case LOADING:
                showProgressDialog();
                break;
        }
    }
}
