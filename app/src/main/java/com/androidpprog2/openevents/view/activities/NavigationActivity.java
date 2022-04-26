package com.androidpprog2.openevents.view.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.view.fragments.EventsFragment;
import com.androidpprog2.openevents.view.fragments.ProfileFragment;
import com.androidpprog2.openevents.view.fragments.UsersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;



public class NavigationActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private ProfileFragment profileFragment = new ProfileFragment();
    private EventsFragment eventsFragment = new EventsFragment();
    private UsersFragment usersFragment = new UsersFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_bar);

        bottomNavigationView = findViewById(R.id.bottomNavigationView); // activity_tab_bar id
        bottomNavigationView.setSelectedItemId(R.id.search_users); // Predefined fragment
        //bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected (@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.usersFragmentId:
                selectFragment(usersFragment);
                // SOME STUFF
                return true;

            case R.id.eventsFragmentId:
                selectFragment(eventsFragment);
                // SOME STUFF
                return true;

            case R.id.profileFragmentId:
                selectFragment(profileFragment);
                // SOME STUFF
                return true;
        }
        return false;
    }

    public void selectFragment (Fragment incomingFragment) {
        FragmentManager fragment = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragment.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, incomingFragment);
        fragmentTransaction.commit();
    }
}
