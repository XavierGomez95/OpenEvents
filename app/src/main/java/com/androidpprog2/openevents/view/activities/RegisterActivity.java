package com.androidpprog2.openevents.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.api.APIUser;
import com.androidpprog2.openevents.business.User;


public class RegisterActivity extends AppCompatActivity {
    private Button registerButton;
    private EditText name;
    private EditText lastName;
    private EditText email;
    private EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = findViewById(R.id.btn_register);

        registerButton.setOnClickListener(view -> {
            checkData();
        });
    }

    private void checkData() {
        name = findViewById(R.id.r_name);
        lastName = findViewById(R.id.r_last_name);
        email = findViewById(R.id.r_email);
        pass = findViewById(R.id.r_password);
        EditText passAgain = findViewById(R.id.r_password_again);
        if (pass.getText().toString().equals(passAgain.getText().toString())) {
            User u = new User(name.getText().toString(), lastName.getText().toString(), email.getText().toString(), pass.getText().toString(), "Commons_Quality_images.htm");
            APIUser api = APIUser.getInstance();
            api.addUser(u, new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User u= response.body();
                    Log.d("RESPONSE","TRUEEEEE"+u.getEmail());
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d("RESPONSEEEEE",t.toString());
                }
            });
        }
    }
}

