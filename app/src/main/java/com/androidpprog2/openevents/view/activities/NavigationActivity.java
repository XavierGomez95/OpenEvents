package com.androidpprog2.openevents.view.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.databinding.ActivityTabBarBinding;
import com.androidpprog2.openevents.view.fragments.EventsFragment;
import com.androidpprog2.openevents.view.fragments.ProfileFragment;
import com.androidpprog2.openevents.view.fragments.UsersFragment;


public class NavigationActivity extends AppCompatActivity {
    private static final String TAG = "NavigationActivity";
    private Bundle bundle = new Bundle();
    private ActivityTabBarBinding binding;
    private ProfileFragment profileFragment = new ProfileFragment();
    private EventsFragment eventsFragment = new EventsFragment();
    private UsersFragment usersFragment = new UsersFragment();
    public static String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        binding = ActivityTabBarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Predefined fragment
        selectFragment(eventsFragment);

        binding.bottomNavigationView.setSelectedItemId(R.id.events); // Predefined button
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.search_users:
                    selectFragment(usersFragment);

//                  Log.e("3", "---- OPTION 1 !!!!");
                    break;

                case R.id.events:
                    selectFragment(eventsFragment);


//                  Log.e("2", "---- OPTION 2 !!!!");
                    break;

                case R.id.profile:
                    selectFragment(profileFragment);

//                  Log.e("3", "---- OPTION 3 !!!!");
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + item.getItemId());
            }
            return true;
        });
    }

    public void selectFragment(Fragment incomingFragment) {
        FragmentManager fragment = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragment.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, incomingFragment);
        fragmentTransaction.commit();
    }
}
