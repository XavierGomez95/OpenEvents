package com.androidpprog2.openevents.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.androidpprog2.openevents.R;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton, goRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.btn_login);
        goRegisterButton = findViewById(R.id.btn_go_register);

        loginButton.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, NavigationActivity.class));
        });

        goRegisterButton.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }



}