package com.androidpprog2.openevents.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.Token;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.persistance.api.APIUser;
import com.androidpprog2.openevents.view.adapters.UsersAdapter;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * USERS FRAGMENT CLASS
 */
public class UsersFragment extends Fragment {
    private APIUser apiUser;
    private List<User> userList = new ArrayList<>();
    private RecyclerView usersRecyclerView;
    private UsersAdapter usersAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SearchView searchUserView;
    private View view;
    private static final String TAG = "UsersFragment: ";


    /**
     * Setting the essential layout parameters.
     *
     * @param savedInstanceState reference to a Bundle object that is passed into the onCreate.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
    }


    /**
     * Inflating the layout of the UsersFragment.
     * Loading views, api calls and search views.
     *
     * @param inflater object used to inflate any views in the fragment.
     * @param container used to generate the LayoutParams of the view.
     * @param savedInstanceState not used.
     * @return UserFragment view.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                          @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_users, container, false);

        loadViews();

        apiCall();

        searchUserView.clearFocus();
        searchUsers();

        return view;
    }


    /**
     * Method used to set the views.
     */
    private void loadViews() {
        searchUserView = view.findViewById(R.id.search_bar);
    }


    /**
     * Method used to get the users list from the API.
     */
    private void apiCall() {
        apiUser = APIUser.getInstance();
        apiUser.getListUsers(Token.getToken(getContext()), new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                try {
                    if (response.isSuccessful()) {
                        userList = response.body();
                        usersRecyclerView = view.findViewById(R.id.recycler_view_users);
                        usersAdapter = new UsersAdapter(userList, getContext());
                        usersRecyclerView.setLayoutManager(linearLayoutManager);
                        usersRecyclerView.setAdapter(usersAdapter);
                    }
                } catch (Exception exception) {
                    DynamicToast.makeError(getContext(), exception.getMessage()).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                DynamicToast.makeError(getContext(), "Error on response API").show();
            }
        });
    }


    /**
     * Method used to search users by e-mail.
     */
    private void searchUsers() {
        searchUserView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterUsersList(s);
                return false;
            }
        });
    }


    /**
     * Method used to filter the information by e-mail.
     *
     * @param incomingString text typed in the SearchView.
     */
    private void filterUsersList(String incomingString) {
        // Llamada a la API
        apiUser.getUserSearch(Token.getToken(getContext()), incomingString, new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                try {
                    if (response.isSuccessful()) {
                        usersAdapter = new UsersAdapter(response.body(), getContext());
                        usersRecyclerView.setAdapter(usersAdapter);
                    }
                } catch (Exception exception) {
                    DynamicToast.makeError(getContext(), exception.getMessage()).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                DynamicToast.makeError(getContext(), "Error on response API").show();
            }
        });
    }
}
