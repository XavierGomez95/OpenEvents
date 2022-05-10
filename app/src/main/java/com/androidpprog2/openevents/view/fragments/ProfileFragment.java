package com.androidpprog2.openevents.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.view.activities.LoginActivity;

public class ProfileFragment extends Fragment {
    private Button logoutButton;
    //private Token token;


    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        android.view.View root = inflater.inflate(R.layout.fragment_profile, container, false);
        logoutButton = root.findViewById(R.id.logout_id);

//        token = new Token(savedInstanceState.getString("tokenStr")); // Como pasar el objeto token
        //Log.e("TAG", "Profile token: " + token.getAccessToken()); // TEMPORAL

        logoutButton.setOnClickListener(v -> {
            onClickLogout();
        });

        return root;
    }

    private void onClickLogout() {
        // Reset accessToken to null
//        token.setAccessTokenToNull();

        // Deleting the saved info
        SharedPreferences sharedPreferences = getContext().getSharedPreferences
                ("credenciales", Context.MODE_PRIVATE);
        sharedPreferences.edit().remove("token").clear().apply();
        sharedPreferences.edit().putString("token", null).apply();
        Log.e("LogOut", sharedPreferences.getString("token", "Non existing token"));

        // Set new activity and pass the new null accessToken to the intent
        /*Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.putExtra("tokenStr", token.getAccessToken());*/

        // Start LoginActivity and finish this one
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

}
