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

/**
 * LOGIN ACTIVITY CLASS
 */
public class LoginActivity extends AppCompatActivity {
    private Button loginButton, goRegisterButton;
    private EditText email;
    private EditText pass;


    /**
     * Setting the essential layout parameters.
     * Manage the login and the register button functions.
     *
     * @param savedInstanceState reference to a Bundle object that is passed into the onCreate.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loadViews();

        loginButton.setOnClickListener(view -> {
            checkData();
        });

        goRegisterButton.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    /**
     * Method called to load the views.
     */
    private void loadViews() {
        loginButton = findViewById(R.id.btn_login);
        goRegisterButton = findViewById(R.id.btn_go_register);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
    }

    /**
     * Get inputs email and pass editTexts and calls the api waiting the response of a token.
     * Also safes the token for maintaining the session initialized.
     */
    private void checkData() {
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
                        saveToken(token);
                        DynamicToast.makeSuccess(context, "Successful Login").show();

                        Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
                        startActivity(intent);
                    }

                } else {
                    DynamicToast.makeError(context, "Wrong email or password").show();
                }
            }

            private void saveToken(Token token) {
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
