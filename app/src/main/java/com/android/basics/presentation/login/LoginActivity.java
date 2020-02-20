package com.android.basics.presentation.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.basics.R;
import com.android.basics.core.presentation.ResourceState;
import com.android.basics.di.UserComponent;
import com.android.basics.domain.model.User;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    LoginViewModel viewModel;

    ProgressDialog progressDialog;

    Button btnLogin;
    Button btnRegister;

    EditText edtUserName;
    EditText edtPassword;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btn_add_todo);
        btnRegister = findViewById(R.id.btn_signup);
        edtUserName = findViewById(R.id.edt_todo_name);
        edtPassword = findViewById(R.id.edt_todo_description);
        builder = new AlertDialog.Builder(this);

        btnLogin.setOnClickListener(view -> viewModel.OnLoginClick(edtUserName.getText().toString(), edtPassword.getText().toString()));
        btnRegister.setOnClickListener(view -> viewModel.onRegisterClick());

        UserComponent.getInstance().end();

        LoginInjector.getInstance().inject(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissProgressDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoginInjector.getInstance().destroy();
    }

    @Override
    public void showProgressDialog() {
        progressDialog.setMessage("Logging in");
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        progressDialog.dismiss();
        progressDialog.cancel();
    }

    @Override
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

    private void handleState(ResourceState state, User user, String errorMessage) {
        switch (state) {
            case SUCCESS:
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
