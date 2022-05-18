package com.androidpprog2.openevents.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.CollapsibleActionView;
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
import com.androidpprog2.openevents.api.APIUser;
import com.androidpprog2.openevents.business.Token;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.view.UsersAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersFragment extends Fragment implements CollapsibleActionView {
    private APIUser apiUser;
    private List<User> userList = new ArrayList<>();
    private RecyclerView usersRecyclerView;
    private UsersAdapter usersAdapter;
    private LinearLayoutManager linearLayoutManager;
    private List<User> filteredList;
    private SearchView searchUserView;
    private View view;
    private static final String TAG = "UsersFragment";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
    }


    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_users, container, false);

        apiCall();

                // TEMPORAL
                for (User u : userList)
                    Log.d("User list:", u.getName());

        //searchUserView = new SearchView(getContext());
        searchUserView = view.findViewById(R.id.search_bar);
        searchUserView.clearFocus();

        searchUsers();

        return view;
    }


    private void apiCall() {
        apiUser = APIUser.getInstance();
        apiUser.getListUsers(Token.getToken(getContext()), new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                try {
                    if (response.isSuccessful()) {
                        userList = response.body();
                        usersRecyclerView = view.findViewById(R.id.recycler_view_users);
                        usersAdapter = new UsersAdapter(userList);
                        usersRecyclerView.setLayoutManager(linearLayoutManager);
                        usersRecyclerView.setAdapter(usersAdapter);
                    }
                } catch (Exception exception) {
                    Log.e("TAG", exception.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("onFailure:", "Fallo de lectura API");
            }
        });
    }


    private void searchUsers() {
        searchUserView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterUsersList(s);
                return true;
            }
        });
    }

    private void filterUsersList(String s) {
        // Llamada a la API
        apiUser.getUserSearch(Token.getToken(getContext()), s, new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                try {
                    if (response.isSuccessful()) {
                        userList.clear();
                        userList.addAll(response.body());
                        usersAdapter.notifyDataSetChanged();
                    }
                } catch (Exception exception) {
                    Log.e("TAG", exception.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("onFailure:", "Fallo de lectura API filteredList");
            }
        });

//        if (!filteredList.isEmpty()) usersAdapter.setFilteredList(filteredList);
    }

    @Override
    public void onActionViewExpanded() {

    }

    @Override
    public void onActionViewCollapsed() {

    }
}
