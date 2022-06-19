package com.example.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AuthorizationActivity extends AppCompatActivity {
    Button btnAuthorization;
    Button btnRegistration;

    EditText editLogin;
    EditText editPassword;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        databaseHelper = new DatabaseHelper(this);
        btnAuthorization = findViewById(R.id.btnEnter);
        btnRegistration = findViewById(R.id.btnReg);
        editLogin = findViewById(R.id.editLoginAuth);
        editPassword = findViewById(R.id.editPasswordAuth);
        btnAuthorization.setOnClickListener(view -> {
            if (isValid()) {
                if (isExist()) {
                    int role = databaseHelper.isUserExist(
                            editLogin.getText().toString().trim(),
                            editPassword.getText().toString().trim());

                    if (role == Authorization._successUser) {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (role == Authorization._successAdmin) {
                        Intent intent = new Intent(this, AdminActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else Toast.makeText(getApplicationContext(), "Ошибка при авторизации", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnRegistration.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
            finish();
        });
    }

    /**
     * Проверяет поля для ввода на отсутствие пустых полей
     */
    private boolean isValid() {
        String error = "Это обязательное поле!";
        boolean isValid = false;

        if (editLogin.getText().toString().isEmpty())
            editLogin.setError(error);
        else if (editPassword.getText().toString().isEmpty())
            editPassword.setError(error);
        else isValid = true;

        return isValid;
    }

    /**
     * Проверяет логин и пароль, введенные пользователем
     */
    private boolean isExist() {
        String errorLogin = "Пользователь с таким логином не найден";
        String errorPassword = "Неверный пароль";
        boolean isExist = false;

        //Код результата о наличии пользователя в системе
        int result = databaseHelper.isUserExist(editLogin.getText().toString().trim(),
                editPassword.getText().toString().trim());
        if (result == Authorization._invalidLogin)
            editLogin.setError(errorLogin);
        else if (result == Authorization._invalidPassword)
            editPassword.setError(errorPassword);
        else isExist = true;

        //TODO: Проверять возвращаемое значение при входе на то, является пользователь админом или нет.
        //TODO: И в зависимости от этого перенаправляться на разные страницы

        return isExist;
    }

}
