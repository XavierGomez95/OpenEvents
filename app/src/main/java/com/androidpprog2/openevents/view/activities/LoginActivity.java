package com.androidpprog2.openevents.view.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.persistance.api.APIUser;
import com.androidpprog2.openevents.business.Token;
import com.androidpprog2.openevents.business.User;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

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
        });

        goRegisterButton.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    /**
     * get inputs email and pass editTexts and calls the api waiting the response of a token
     */
    private void checkData() {
        //if (email != null && pass != null) {
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        Context context = this;

        User u = new User(email.getText().toString(), pass.getText().toString());
        APIUser api = APIUser.getInstance();
        api.loginUser(u, new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Token token = response.body();
                if (response.body() != null) {
                    if (token.getAccessToken() != null) {
                        // Saving the token
                        safeToken(token);
                        DynamicToast.makeSuccess(context, "Successful Login").show();

                        Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                        startActivity(intent);
                    }

                } else {
                    DynamicToast.makeError(context, "Wrong email or password").show();
                }
            }

            private void safeToken(Token token) {
                SharedPreferences sharedPreferences = getSharedPreferences
                        ("credenciales", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.apply();
                editor.putString("token", token.getAccessToken());
                editor.putString("email", u.getEmail());

                editor.commit();
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                DynamicToast.makeError(context, "Error while connecting to the API").show();
            }


        });
    }
}
