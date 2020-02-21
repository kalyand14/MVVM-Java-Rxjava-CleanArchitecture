package com.android.basics.presentation.registration;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.basics.R;
import com.android.basics.core.presentation.ResourceState;
import com.android.basics.domain.model.User;

public class RegisterUserActivity extends AppCompatActivity {

    RegistrationViewModel viewModel;
    ProgressDialog progressDialog;

    Button btnLogin;
    Button btnRegister;

    EditText edtUserName;
    EditText edtPassword;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        btnLogin = findViewById(R.id.btn_add_todo);
        btnRegister = findViewById(R.id.btn_signup);
        edtUserName = findViewById(R.id.edt_todo_name);
        edtPassword = findViewById(R.id.edt_todo_description);
        builder = new AlertDialog.Builder(this);

        btnRegister.setOnClickListener(view -> viewModel.onRegisterClick(edtUserName.getText().toString(), edtPassword.getText().toString()));
        btnLogin.setOnClickListener(view -> viewModel.onLoginClick());

        RegisterUserInjector.getInstance().inject(this);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RegisterUserInjector.getInstance().destroy();
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
