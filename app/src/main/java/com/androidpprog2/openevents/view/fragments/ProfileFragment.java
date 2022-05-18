package com.androidpprog2.openevents.view.fragments;

import static com.androidpprog2.openevents.view.activities.NavigationActivity.searchUser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.view.activities.LoginActivity;


public class ProfileFragment extends Fragment {
    private TextView logout, email, name;
    private View view;
    private User myUser = null;

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                          @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        myUser = searchUser(getContext());
        logout = view.findViewById(R.id.profile_logout_id);


        if (myUser != null) {
            loadText();
        }

//        token = new Token(savedInstanceState.getString("tokenStr")); // Como pasar el objeto token
        //Log.e("TAG", "Profile token: " + token.getAccessToken()); // TEMPORAL

        logout.setOnClickListener(v -> {
            onClickLogout();
        });


        return view;
    }

    private void loadText() {
        email = view.findViewById(R.id.profile_email);
        email.setText(myUser.getEmail());

        name = view.findViewById(R.id.profile_name);
        name.setText(myUser.getName() + "" + myUser.getLast_name());
    }


    private void onClickLogout() {
        // Reset accessToken to null
//        token.setAccessTokenToNull();

        // Deleting the saved info
        SharedPreferences sharedPreferences = getContext().getSharedPreferences
                ("credenciales", Context.MODE_PRIVATE);
        sharedPreferences.edit().remove("token").clear().apply();
        sharedPreferences.edit().remove("email").clear().apply();
        sharedPreferences.edit().putString("token", null).apply();
        sharedPreferences.edit().putString("email", null).apply();

        Log.e("LogOut", sharedPreferences.getString("token", "Non existing token"));

        // Start LoginActivity and finish this one
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

}
