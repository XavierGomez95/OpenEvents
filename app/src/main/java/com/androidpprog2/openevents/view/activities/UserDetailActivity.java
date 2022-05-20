package com.androidpprog2.openevents.view.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.persistance.api.APIUser;
import com.androidpprog2.openevents.business.FriendRequest;
import com.androidpprog2.openevents.business.Stats;
import com.androidpprog2.openevents.business.Token;
import com.androidpprog2.openevents.business.User;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailActivity extends AppCompatActivity {
    private TextView stats;
    private TextView num_friends;
    private Button friendRequest_btn;
    private ImageView imageView;
    private String imageURL;
    private User user;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        List<User> userList = (List<User>) getIntent().getSerializableExtra("userList");
        int position = getIntent().getIntExtra("position", -1);
        boolean friend = getIntent().getBooleanExtra("friend", false);
        boolean request = getIntent().getBooleanExtra("request", false);

        user = userList.get(position);

        TextView name = findViewById(R.id.user_name);
        TextView email = findViewById(R.id.user_email);
        stats = findViewById(R.id.stats);
        num_friends = findViewById(R.id.numFriends);
        friendRequest_btn = findViewById(R.id.friendRequestbtn);
        imageView = findViewById(R.id.user_image);

        loadImg();
        name.setText(user.getName() + " " + user.getLast_name());
        email.setText(user.getEmail());
        getStats();
        getNumFriends();
        if (friend) {
            friendRequest_btn.setText("FRIEND");
            friendRequest_btn.setEnabled(false);
        } else if (request) {
            friendRequest_btn.setText("ACCEPT REQUEST");
            acceptRequest();
        }
        friendRequest_btn.setOnClickListener(view -> {
            if (!friendRequest_btn.getText().equals("REQUESTED")) {
                APIUser api = APIUser.getInstance();
                api.addFriendRequest(Token.getToken(this), user.getId(), new Callback<FriendRequest>() {
                    @Override
                    public void onResponse(@NonNull Call<FriendRequest> call, @NonNull Response<FriendRequest> response) {
                        friendRequest_btn.setText("REQUESTED");
                        friendRequest_btn.setEnabled(false);
                        DynamicToast.makeSuccess(getApplicationContext(), "Successful friend request").show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<FriendRequest> call, @NonNull Throwable t) {
                        Log.d("IRIS", "FALSE");

                    }
                });

            }
        });

    }

    private void loadImg() {
        String imageURL, image = user.getImage();

        if (image != null) {
            if ((image.startsWith("http") || image.startsWith("https"))
                    && (image.endsWith(".jpg") || image.endsWith(".png")
                    || image.endsWith(".jpeg") || image.endsWith(".JPG")
                    || image.endsWith(".PNG") || image.endsWith(".JPEG")))
                imageURL = image;
            else
                imageURL = "https://t4.ftcdn.net/jpg/04/70/29/97/360_F_470299797_UD0eoVMMSUbHCcNJCdv2t8B2g1GVqYgs.jpg";
        } else
            imageURL = "https://t4.ftcdn.net/jpg/04/70/29/97/360_F_470299797_UD0eoVMMSUbHCcNJCdv2t8B2g1GVqYgs.jpg";

        Log.d("EVENT NAME : ", user.getName());
        Log.d("URL : ", image);

        Picasso.with(getApplicationContext()).load(imageURL).into(imageView);
    }

    private void getStats() {
        stats = findViewById(R.id.stats);
        APIUser.getInstance().getUserStats(Token.getToken(this), user.getId(), new Callback<Stats>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<Stats> call, @NonNull Response<Stats> response) {
                assert response.body() != null;
                stats.setText("Comments:" + response.body().getNum_comments() + "\nAverage score:" + response.body().getAvg_score() + "\nPercentage comments:" + response.body().getPercentage_commenters_below());
            }

            @Override
            public void onFailure(@NonNull Call<Stats> call, @NonNull Throwable t) {
                DynamicToast.makeError(getApplicationContext(), "Error loading stadistics").show();


            }
        });
    }

    private void getNumFriends() {
        num_friends = findViewById(R.id.numFriends);
        APIUser.getInstance().getFriends(Token.getToken(this), user.getId(), new Callback<List<User>>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                assert response.body() != null;
                response.body().size();
                num_friends.setText(response.body().size() + " friends");
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                DynamicToast.makeError(getApplicationContext(), "Error loading friends").show();

            }
        });
    }

    private void acceptRequest() {
        Context context = this;
        friendRequest_btn.setOnClickListener(view -> {
            APIUser.getInstance().addFriendRequest(Token.getToken(context), user.getId(), new Callback<FriendRequest>() {
                @Override
                public void onResponse(Call<FriendRequest> call, Response<FriendRequest> response) {
                    DynamicToast.makeSuccess(context, "REQUEST ACCEPTED").show();
                    friendRequest_btn.setText("FRIEND");
                    friendRequest_btn.setEnabled(false);
                }

                @Override
                public void onFailure(Call<FriendRequest> call, Throwable t) {
                    DynamicToast.makeError(context, "API ERROR").show();
                }
            });
        });
    }
}
