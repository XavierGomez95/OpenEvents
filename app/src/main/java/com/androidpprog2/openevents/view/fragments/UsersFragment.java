package com.androidpprog2.openevents.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.api.APIUser;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.view.EventsAdapter;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {
    private APIUser apiUser;
    private List<User> userList = new ArrayList<>();
    private RecyclerView usersRecyclerView;
    private EventsAdapter usersAdapter;
    private static final String TAG = "UsersFragment";
/*

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiCall();

        */
/*usersRecyclerView = usersRecyclerView.findViewById(R.id.recyclerViewEvents);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        usersAdapter = new CustomAdapter(userList, getContext());
        usersRecyclerView.setAdapter(usersAdapter);*//*



    }

    private void apiCall() {
        apiUser = APIUser.getInstance();
        apiUser.getListUsers(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                userList = response.body();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d(TAG, "usersList FAIL");
                Log.d(TAG, t.toString());
            }
        });
    }

*/

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_users, container, false);
    }
}
