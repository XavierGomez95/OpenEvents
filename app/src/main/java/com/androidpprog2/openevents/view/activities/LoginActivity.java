package com.androidpprog2.openevents.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.api.APIUser;
import com.androidpprog2.openevents.business.Token;
import com.androidpprog2.openevents.business.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Button loginButton, goRegisterButton;
    private EditText email;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.btn_login);
        goRegisterButton = findViewById(R.id.btn_go_register);

        loginButton.setOnClickListener(view -> {
            checkData();
            startActivity(new Intent(LoginActivity.this, NavigationActivity.class));
        });

        goRegisterButton.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }


    private void checkData() {
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        User u = new User(email.getText().toString(), pass.getText().toString());
        APIUser api = APIUser.getInstance();
        api.loginUser(u, new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Token token = response.body();
                Log.d("TOKENN", token.getAccessToken());
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.d("TOKENN", "LOGIN FAIL");
                Log.d("TOKENN", t.toString());
            }
        });
    }
}