package com.android.basics.presentation.todo.edit;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.basics.R;
import com.android.basics.core.presentation.Resource;
import com.android.basics.presentation.BaseActivity;
import com.android.basics.presentation.UserActivity;

import java.util.Calendar;

public class EditTodoActivity extends UserActivity<EditTodoViewModel> {

    private ProgressDialog progressDialog;
    private AlertDialog.Builder builder;

    private EditText edtName;
    private  EditText edtDescription;
    private EditText edtDate;
    private Button btnSubmit;
    private Button btnDelete;
    private ImageButton btnDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Edit Todo");

        builder = new AlertDialog.Builder(this);

        edtName = findViewById(R.id.edt_todo_name);
        edtDescription = findViewById(R.id.edt_todo_description);
        edtDate = findViewById(R.id.edt_todo_date);

        btnSubmit = findViewById(R.id.btn_edit_todo);
        btnDelete = findViewById(R.id.btn_edit_delete);
        btnDate = findViewById(R.id.btn_edit_date);

        btnSubmit.setOnClickListener(view ->
                viewModel.onSubmit(edtName.getText().toString(), edtDescription.getText().toString(), edtDate.getText().toString()));

        btnDelete.setOnClickListener(view -> viewModel.onDelete());

        btnDate.setOnClickListener(view -> viewModel.onSelectDate());

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);

        viewModel.loadTodo();
    }

    @Override
    protected Integer getContentViewId() {
        return R.layout.activity_edit_todo;
    }

    @Override
    protected Class<? extends EditTodoViewModel> getViewModel() {
        return EditTodoViewModel.class;
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.getLoadTodoEvent().observe(this, todo -> {
            if (todo != null) {
                setName(todo.getName());
                setDescription(todo.getDescription());
                setDate(todo.getDueDate());
            }
        });

        viewModel.getShowDatePickerEvent().observe(this, it -> {
            showDatePickerDialog();
        });
        viewModel.getEditTodoState().observe(this, it -> {
            handleEditTodoState(it.getState(), it.getMessage());
        });
        viewModel.getDeleteTodoState().observe(this, it -> {
            handleDeleteTodoState(it.getState(), it.getMessage());
        });
    }

    private void handleEditTodoState(Resource.ResourceState state, String errorMessage) {
        switch (state) {
            case SUCCESS:
                dismissProgressDialog();
                showSuccessDialog("Record successfully updated.");
                break;
            case ERROR:
                dismissProgressDialog();
                showErrorDialog("There was a problem. could not able to update the record.");
                break;
            case LOADING:
                showProgressDialog("Updating todo");
                break;
        }
    }

    private void handleDeleteTodoState(Resource.ResourceState state, String errorMessage) {
        switch (state) {
            case SUCCESS:
                dismissProgressDialog();
                showSuccessDialog("Record successfully deleted.");
                break;
            case ERROR:
                dismissProgressDialog();
                showErrorDialog("Error deleting todo");
                break;
            case LOADING:
                showProgressDialog("Deleting todo");
                break;
        }
    }

    public void showProgressDialog(String message) {
        progressDialog.setMessage(message);
        progressDialog.show();
    }


    public void dismissProgressDialog() {
        progressDialog.dismiss();
        progressDialog.cancel();
    }


    public void showSuccessDialog(String message) {

        //Setting message manually and performing action on button click
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, id) -> {
                    dialog.dismiss();
                    viewModel.navigate();
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.show();

    }


    public void showErrorDialog(String message) {
        //Setting message manually and performing action on button click
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", (dialog, id) -> {
                    dialog.dismiss();
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setTitle("Error");
        alert.show();
    }


    public void showDatePickerDialog() {
        int mYear, mMonth, mDay, mHour, mMinute;
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePickerDialog.OnDateSetListener) (view, year, monthOfYear, dayOfMonth) ->
                        edtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year),
                mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    public void setName(String name) {
        edtName.setText(name);
    }


    public void setDescription(String description) {
        edtDescription.setText(description);
    }


    public void setDate(String date) {
        edtDate.setText(date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.form_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.cancel:
                viewModel.OnCancel();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
