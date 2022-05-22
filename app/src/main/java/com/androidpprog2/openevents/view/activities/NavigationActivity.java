package com.androidpprog2.openevents.view.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.persistance.api.APIUser;
import com.androidpprog2.openevents.business.Token;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.databinding.ActivityTabBarBinding;
import com.androidpprog2.openevents.view.fragments.EventsFragment;
import com.androidpprog2.openevents.view.fragments.ProfileFragment;
import com.androidpprog2.openevents.view.fragments.UsersFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * NAVIGATION ACTIVITY CLASS
 */
public class NavigationActivity extends AppCompatActivity {
    private static final String TAG = "NavigationActivity";
    private Bundle bundle = new Bundle();
    private ActivityTabBarBinding binding;
    private ProfileFragment profileFragment = new ProfileFragment();
    private EventsFragment eventsFragment = new EventsFragment();
    private UsersFragment usersFragment = new UsersFragment();
    public static User user = null;

    /**
     * Setting the essential layout parameters.
     * Manage of the fragments.
     *
     * @param savedInstanceState reference to a Bundle object that is passed into the onCreate.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTabBarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        selectFragment(eventsFragment); // Predefined fragment
        searchUser(this);
        binding.bottomNavigationView.setSelectedItemId(R.id.events); // Predefined button
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.search_users:
                    selectFragment(usersFragment);
                    break;

                case R.id.events:
                    selectFragment(eventsFragment);
                    break;

                case R.id.profile:
                    selectFragment(profileFragment);
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + item.getItemId());
            }
            return true;
        });
    }

    /**
     *
     * @param incomingFragment object if an specific fragment.
     */
    public void selectFragment(Fragment incomingFragment) {
        FragmentManager fragment = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragment.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, incomingFragment);
        fragmentTransaction.commit();
    }

    /**
     *
     * @param context of this activity.
     * @return user.
     */
    public static User searchUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        final String[] email = {sharedPreferences.getString("email", "Error, information does not exist.")};
        APIUser.getInstance().getListUsers(Token.getToken(context), new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                for (User u : response.body()) {
                    if (u.getEmail().equals(email[0])) {
                        user = u;
                        //profileFragment.loadImg();
                        Log.d("IRIS", "USER" + user.getEmail());
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("IRIS", "FAIL");
            }
        });
        return user;
    }
}

