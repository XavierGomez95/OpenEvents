package com.androidpprog2.openevents.view.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

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

        User u = new User(email.getText().toString(), pass.getText().toString());
        APIUser api = APIUser.getInstance();
        api.loginUser(u, new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Token token = response.body();
                Log.d("TOKENN", token.getAccessToken());

                //if (token.getAccessToken() != null) {
                // Saving the token
                safeToken(token);

                // Initialize the new Activity (Navigation Activity)
                Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                //intent.putExtra("tokenStr", token.getAccessToken()); // Casting to Parcelable
                intent.putExtra("email", email.getText().toString());

                startActivity(intent);
                //}
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
                Log.d("TOKENN", "LOGIN FAIL");
                Log.d("TOKENN", t.toString());
            }
        });
    }
}
