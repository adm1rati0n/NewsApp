package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import java.util.concurrent.Executor;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {
    Button btnReg, btnAuth;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        btnAuth = findViewById(R.id.btnGoToAuth);
        btnReg = findViewById(R.id.btnGoToReg);
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(StartActivity.this,
                executor, new BiometricPrompt.AuthenticationCallback(){
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString){
                super.onAuthenticationError(errorCode, errString);
                Intent intent = new Intent(StartActivity.this, AuthorizationActivity.class);
                startActivity(intent);
                finish();
            }
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result){
                super.onAuthenticationSucceeded(result);
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
            @Override
            public void onAuthenticationFailed(){
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(),"Не удалось войти с помощью отпечатка пальца", Toast.LENGTH_SHORT).show();
            }
        });
        btnReg.setOnClickListener(view -> {
            Intent intent = new Intent(StartActivity.this, RegistrationActivity.class);
            startActivity(intent);
            finish();
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Авторизация")
                .setSubtitle("Прислоните палец")
                .setNegativeButtonText("Войти с помощью логина и пароля")
                .build();


        btnAuth.setOnClickListener(view -> {
            biometricPrompt.authenticate(promptInfo);
        });
    }
}
