package com.androidpprog2.openevents.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.persistance.api.APIUser;
import com.androidpprog2.openevents.business.Token;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.view.adapters.FriendsAdapter;
import com.androidpprog2.openevents.view.adapters.RequestsAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyFriendsActivity extends AppCompatActivity {
    private List<User> requests;
    private List<User> friends;
    private LinearLayoutManager linearLayoutManagerF, linearLayoutManagerR;
    private RecyclerView requestsRecyclerView;
    private RecyclerView friendsRecyclerView;
    private FriendsAdapter friendsAdapter;
    private RequestsAdapter requestsAdapter;
    private Context c = this;
    private User myUser;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        friendsRecyclerView = findViewById(R.id.recycler_view_friends);
        linearLayoutManagerF = new LinearLayoutManager(getApplicationContext());
        linearLayoutManagerF.setOrientation(RecyclerView.VERTICAL);

        requestsRecyclerView = findViewById(R.id.recycler_view_requests);
        linearLayoutManagerR = new LinearLayoutManager(getApplicationContext());
        linearLayoutManagerR.setOrientation(RecyclerView.VERTICAL);
        getListRequests();
        getListFriends();

    }

    private void getListRequests() {

        APIUser.getInstance().getFriendRequests(Token.getToken(this), new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    requests = response.body();
                    if (!requests.isEmpty()) {
                        Log.d("REQUEST", "" + requests.get(0).getEmail());
                        requestsAdapter = new RequestsAdapter(requests, c);
                        requestsRecyclerView.setLayoutManager(linearLayoutManagerR);
                        requestsRecyclerView.setAdapter(requestsAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("ERROR", "");

            }
        });

    }

    private void getListFriends() {

        APIUser.getInstance().getFriends(Token.getToken(this), id, new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    friends = response.body();
                    if (!friends.isEmpty()) {
                        Log.d("DENTRO", " " + friends.get(0).getEmail());
                        friendsAdapter = new FriendsAdapter(friends, c);
                        friendsRecyclerView.setLayoutManager(linearLayoutManagerF);
                        friendsRecyclerView.setAdapter(friendsAdapter);
                    }

                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("ERROR", "");

            }
        });

    }
}