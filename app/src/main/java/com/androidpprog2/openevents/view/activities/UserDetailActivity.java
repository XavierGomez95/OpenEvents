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

/**
 * USER DETAIL ACTIVITY CLASS
 */
public class UserDetailActivity extends AppCompatActivity {
    private TextView stats, num_friends, name, email;
    private Button friendRequest_btn;
    private ImageView imageView;
    private String imageURL;
    private User user;


    /**
     * Setting the essential layout parameters.
     * load views, load image, setting name, last name, email, status and number of friends.
     *
     * @param savedInstanceState reference to a Bundle object that is passed into the onCreate.
     */
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

        loadViews();
        loadImg();
        setTexts();
        setStats();
        setNumFriends();

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

    /**
     * Method used to set the views.
     */
    private void loadViews() {
        name = findViewById(R.id.user_name);
        email = findViewById(R.id.user_email);
        stats = findViewById(R.id.stats);
        num_friends = findViewById(R.id.numFriends);
        friendRequest_btn = findViewById(R.id.friendRequestbtn);
        imageView = findViewById(R.id.user_image);
    }

    /**
     * Method used to set the textViews of the current user profile.
     */
    private void setTexts() {
        name.setText(user.getName() + " " + user.getLast_name());
        email.setText(user.getEmail());
    }

    /**
     * Method called to load the image shown in the details of an user.
     */
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

        Picasso.with(getApplicationContext()).load(imageURL).into(imageView);
    }

    /**
     * Method called to set the information stats in the
     */
    private void setStats() {
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

    /**
     * Call APIUser to get the quantity of friends, and set it on the textView.
     */
    private void setNumFriends() {
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

    /**
     * Call APIUser accept or decline the friend request.
     */
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
