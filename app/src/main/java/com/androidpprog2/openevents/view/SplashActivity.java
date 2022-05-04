package com.androidpprog2.openevents.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.view.activities.LoginActivity;
import com.androidpprog2.openevents.view.activities.NavigationActivity;

public class SplashActivity extends AppCompatActivity {
    private String token;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        new Handler().postDelayed(() -> {
            if (getToken() == null) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            } else startActivity(new Intent(SplashActivity.this, NavigationActivity.class));
        }, 2000);
    }

    private String getToken() {
        SharedPreferences sharedPreferences = getSharedPreferences
                ("credenciales", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "Error, information does not exist.");

        return token;
    }
}
