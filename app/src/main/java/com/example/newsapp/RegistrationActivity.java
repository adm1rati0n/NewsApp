package com.example.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {
    Button btnRegistration;
    Button btnAuthorization;

    EditText editLastname;
    EditText editName;
    EditText editLogin;
    EditText editPassword;
    CheckBox rbIsAdmin;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        btnRegistration = findViewById(R.id.btnRegistration);
        btnAuthorization = findViewById(R.id.btnAuth);
        editLastname = findViewById(R.id.editLastname);
        editName = findViewById(R.id.editName);
        editLogin = findViewById(R.id.editLogin);
        editPassword = findViewById(R.id.editPassword);
        rbIsAdmin = findViewById(R.id.cbIsAdmin);

        databaseHelper = new DatabaseHelper(this);

        btnRegistration.setOnClickListener(view -> {
            if (isValid()) {
                boolean checkInsertData = databaseHelper.insertUser(
                        editLastname.getText().toString(),
                        editName.getText().toString(),
                        rbIsAdmin.isChecked() ? 1 : 0,
                        editLogin.getText().toString(),
                        editPassword.getText().toString()
                );
                if (checkInsertData) {
                    Toast.makeText(getApplicationContext(),
                            "Решистрация прошла успешно!", Toast.LENGTH_SHORT).show();
                    if (rbIsAdmin.isChecked()) {
                        Intent intent = new Intent(this, AdminActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else
                    Toast.makeText(getApplicationContext(),
                            "Ошибка при регистрации!", Toast.LENGTH_SHORT).show();
            }
        });

        btnAuthorization.setOnClickListener(view -> {
            Intent intent = new Intent(this, AuthorizationActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private boolean isValid() {
        String error = "Это обязательное поле!";
        boolean isValid = false;

        if (editLastname.getText().toString().equals(""))
            editLastname.setError(error);
        else if (editName.getText().toString().isEmpty())
            editName.setError(error);
        else if (editLogin.getText().toString().isEmpty())
            editLogin.setError(error);
        else if (editPassword.getText().toString().isEmpty())
            editPassword.setError(error);
        else if (!databaseHelper.isFreeLogin(editLogin.getText().toString().trim()))
            editLogin.setError("Данный логин уже занят");
        else isValid = true;

        return isValid;
    }
}
