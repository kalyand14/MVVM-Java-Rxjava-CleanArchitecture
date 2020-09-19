package com.android.basics.presentation.registration;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.android.basics.R;
import com.android.basics.core.presentation.AuthResource;
import com.android.basics.domain.model.User;
import com.android.basics.presentation.BaseActivity;

public class RegisterUserActivity extends BaseActivity<RegistrationViewModel> {

    private ProgressDialog progressDialog;

    private Button btnLogin;
    private Button btnRegister;

    private EditText edtUserName;
    private EditText edtPassword;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnLogin = findViewById(R.id.btn_add_todo);
        btnRegister = findViewById(R.id.btn_signup);
        edtUserName = findViewById(R.id.edt_todo_name);
        edtPassword = findViewById(R.id.edt_todo_description);
        builder = new AlertDialog.Builder(this);

        btnRegister.setOnClickListener(view -> viewModel.onRegisterClick(edtUserName.getText().toString(), edtPassword.getText().toString()));
        btnLogin.setOnClickListener(view -> viewModel.onLoginClick());

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

    }

    @Override
    protected Integer getContentViewId() {
        return R.layout.activity_register_user;
    }

    @Override
    protected Class<? extends RegistrationViewModel> getViewModel() {
        return RegistrationViewModel.class;
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
                showRegistrationSuccess();
                break;
            case ERROR:
                dismissProgressDialog();
                showRegistrationError();
                break;
            case LOADING:
                showProgressDialog();
                break;
        }
    }

    public void showProgressDialog() {
        progressDialog.setMessage("Registering ...");
        progressDialog.show();
    }


    public void dismissProgressDialog() {
        progressDialog.dismiss();
        progressDialog.cancel();
    }


    public void showRegistrationError() {

        edtUserName.setText("");
        edtPassword.setText("");

        //Setting message manually and performing action on button click
        builder.setMessage("There was a problem. could not able to register with details.")
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, id) -> {
                    dialog.dismiss();
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setTitle("Error");
        alert.show();

    }


    public void showRegistrationSuccess() {
        //Setting message manually and performing action on button click
        builder.setMessage("you've successfully registered.")
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, id) -> {
                    dialog.dismiss();
                    viewModel.onRegistrationSuccess();
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setTitle("Congrats");
        alert.show();

    }
}
