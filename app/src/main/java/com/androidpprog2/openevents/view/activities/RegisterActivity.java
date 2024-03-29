package com.androidpprog2.openevents.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.persistance.api.APIUser;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * REGISTER ACTIVITY CLASS
 */
public class RegisterActivity extends AppCompatActivity {
    private Button registerButton;
    private EditText name, lastName, email, pass, image, passAgain;

    /**
     * Setting the essential layout parameters.
     *
     * @param savedInstanceState reference to a Bundle object that is passed into the onCreate.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = findViewById(R.id.btn_register);
        registerButton.setOnClickListener(view -> {
            loadViews();
            checkData();
        });
    }

    /**
     * Method used to set the views.
     */
    private void loadViews() {
        name = findViewById(R.id.r_name);
        lastName = findViewById(R.id.r_last_name);
        email = findViewById(R.id.r_email);
        pass = findViewById(R.id.r_password);
        image = findViewById(R.id.r_image);
        passAgain = findViewById(R.id.r_password_again);
    }

    /**
     * Method used to check the data entered in the editTexts, by calling the API. If the data is
     * correct, the user is successfully registered, otherwise not.
     */
    private void checkData() {
        Context context = this;
        if (pass.getText().toString().equals(passAgain.getText().toString())) {
            User u = new User(name.getText().toString(), lastName.getText().toString(), email.getText().toString(), pass.getText().toString(), image.getText().toString());
            APIUser api = APIUser.getInstance();
            api.addUser(u, new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.body() != null) {
                        DynamicToast.makeSuccess(context, "Register Successful").show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    } else {
                        DynamicToast.makeError(context, "Error with the inputs").show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    DynamicToast.makeError(context, "Error while connecting to the API").show();
                }
            });
        } else {
            DynamicToast.makeError(context, "The password is not the same in both inputs").show();
        }
    }
}

