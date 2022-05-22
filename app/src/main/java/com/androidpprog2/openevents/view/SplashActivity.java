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
    private String accessToken;

    /**
     * Setting the content view (splash screen).
     *
     * @param savedInstanceState reference to a Bundle object that is passed into the onCreate.
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        new Handler().postDelayed(() -> {
            if (getSharedPreferences("credenciales", Context.MODE_PRIVATE)
                    .getString("token", null) == null) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            } else {
                Intent intent = new Intent(SplashActivity.this, NavigationActivity.class);
                intent.putExtra("tokenStr", accessToken);
                startActivity(intent);
                // TODO: ELIMINAR EL sout
                System.out.println(getAccessToken());
                finish();
            }
        }, 2000);
    }

    private String getAccessToken() {
        SharedPreferences sharedPreferences = getSharedPreferences
                ("credenciales", Context.MODE_PRIVATE);
        accessToken = sharedPreferences.getString("token", "Error, information does not exist.");
        //accessToken = null; // TEMPORAL PARA QUE SALGA SIEMPRE EL LOGIN

        return accessToken;
    }
}
