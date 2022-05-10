package com.androidpprog2.openevents.view.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.Token;
import com.androidpprog2.openevents.databinding.ActivityTabBarBinding;
import com.androidpprog2.openevents.view.fragments.EventsFragment;
import com.androidpprog2.openevents.view.fragments.ProfileFragment;
import com.androidpprog2.openevents.view.fragments.UsersFragment;


public class NavigationActivity extends AppCompatActivity {
    private static final String TAG = "NavigationActivity";
    private Token token;
    private Bundle bundle = new Bundle();
    private ActivityTabBarBinding binding;
    private ProfileFragment profileFragment = new ProfileFragment();
    private EventsFragment eventsFragment = new EventsFragment();
    private UsersFragment usersFragment = new UsersFragment();
//    private AppCompatButton logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTabBarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //bundle.putString("tokenStr", token.getAccessToken());
        //eventsFragment.setArguments(bundle);

        // Predefined fragment
        selectFragment(eventsFragment);

//        logoutButton = (AppCompatButton) findViewById(R.id.logout_id);

        binding.bottomNavigationView.setSelectedItemId(R.id.events); // Predefined button
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.search_users:
                    selectFragment(usersFragment);
//                    Log.e("3", "---- OPTION 1 !!!!");
                    break;

                case R.id.events:
                    selectFragment(eventsFragment);
//                    Log.e("2", "---- OPTION 2 !!!!");
                    break;

                case R.id.profile:
                    selectFragment(profileFragment);

                    /*token = new Token(getIntent().getStringExtra("tokenStr"));
                    Log.e(TAG, "Profile token: " + token.getAccessToken()); // TEMPORAL*/

                    /*logoutButton.setOnClickListener(v -> {
                        onClickLogout();
                    });*/
//                  Log.e("3", "---- OPTION 3 !!!!");
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + item.getItemId());
            }
            return true;
        });
    }

    /*private void onClickLogout() {
        // Reset accessToken to null
        token.setAccessTokenToNull();

        // Deleting the saved info
        SharedPreferences sharedPreferences = getSharedPreferences
                ("credenciales", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear();
        sharedPreferences.edit().commit();

        // Set new activity and pass the new null accessToken to the intent
        Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
        intent.putExtra("tokenStr", token.getAccessToken());

        // Start LoginActivity and finish this one
        startActivity(intent);
        finish();
    }*/

    public void selectFragment (Fragment incomingFragment) {
        //bundle.putString("tokenStr", token.getAccessToken());
        //incomingFragment.setArguments(bundle);

        FragmentManager fragment = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragment.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, incomingFragment);
        fragmentTransaction.commit();
    }
}
