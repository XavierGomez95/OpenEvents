package com.androidpprog2.openevents.view.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.persistance.api.APIUser;
import com.androidpprog2.openevents.business.Stats;
import com.androidpprog2.openevents.business.Token;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.view.activities.LoginActivity;
import com.androidpprog2.openevents.view.activities.MyFriendsActivity;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * PROFILE FRAGMENT CLASS
 */
public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    private TextView logout, textViewEmail, textViewName, textViewLastName, textViewStats, numFriends;
    private ImageView imageView;
    private AlertDialog dialogEmail, dialogName, dialogLastName;
    private EditText editTextEmail, editTextName, editTextLastName;
    private View view;
    private User myUser = null;
    private APIUser apiUser;
    private Button btnfriends;
    private int id;


    /**
     * Inflating the layout of the EventsFragment.
     * Manage the functions related to edited texts, dialogs and buttons.
     *
     * @param inflater object used to inflate any views in the fragment.
     * @param container used to generate the LayoutParams of the view.
     * @param savedInstanceState not used.
     * @return ProfileFragment view.
     */
    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                          @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        dialogEmail = new AlertDialog.Builder(getActivity()).create();
        dialogName = new AlertDialog.Builder(getActivity()).create();
        dialogLastName = new AlertDialog.Builder(getActivity()).create();

        editTextEmail = new EditText(getActivity());
        editTextName = new EditText(getActivity());
        editTextLastName = new EditText(getActivity());

        loadViews();
        searchUser();

        btnfriends.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), MyFriendsActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        });

        // This piece of code allows the logged-in user to edit its own e-mail.
        dialogEmail.setTitle(" Edit Email ");
        dialogEmail.setView(editTextEmail);
        dialogEmail.setButton(DialogInterface.BUTTON_POSITIVE, "SAFE", (dialogInterface, i) -> {
            apiUser = APIUser.getInstance();
            String oldEmail = myUser.getEmail();
            Activity activity = this.getActivity();
            if (!oldEmail.equals(editTextEmail.getText().toString())) {
                User editedUser = new User(null, null, editTextEmail.getText().toString());
                apiUser.editUser(Token.getToken(getContext()), editedUser, new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.body() != null) {
                            myUser = response.body();
                            textViewEmail.setText(myUser.getEmail());
                            DynamicToast.makeSuccess(getContext(), "Email edited successfully").show();
                            SharedPreferences sharedPreferences = activity.getSharedPreferences
                                    ("credenciales", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.apply();
                            editor.putString("email", myUser.getEmail());

                            editor.commit();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        myUser.setEmail(oldEmail);
                        DynamicToast.makeError(getContext(), "Error editing email").show();
                    }
                });
            }
        });

        // This piece of code allows the logged-in user to edit its own name.
        dialogName.setTitle(" Edit Name ");
        dialogName.setView(editTextName);
        dialogName.setButton(DialogInterface.BUTTON_POSITIVE, "SAFE", (dialogInterface, i) -> {
            apiUser = APIUser.getInstance();
            String oldName = myUser.getName();
            if (!oldName.equals(editTextName.getText().toString())) {
                User editedUser = new User(editTextName.getText().toString(), null, null);
                apiUser.editUser(Token.getToken(getContext()), editedUser, new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.body() != null) {
                            myUser = response.body();
                            textViewName.setText(myUser.getName());
                            DynamicToast.makeSuccess(getContext(), "Name edited successfully").show();
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        myUser.setName(oldName);
                        DynamicToast.makeError(getContext(), "Error editing name").show();
                    }
                });
            }
        });

        // This piece of code allows the logged-in user to edit its own last name.
        dialogLastName.setTitle(" Edit Last Name ");
        dialogLastName.setView(editTextLastName);
        dialogLastName.setButton(DialogInterface.BUTTON_POSITIVE, "SAFE", (dialogInterface, i) -> {
            apiUser = APIUser.getInstance();
            String oldLastName = myUser.getLast_name();
            if (!oldLastName.equals(editTextLastName.getText().toString())) {

                User editedUser = new User(null, editTextLastName.getText().toString(), null);

                apiUser.editUser(Token.getToken(getContext()), editedUser, new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.body() != null) {
                            myUser = response.body();
                            textViewLastName.setText(myUser.getLast_name());
                            DynamicToast.makeSuccess(getContext(), "Last Name edited successfully").show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        myUser.setEmail(oldLastName);
                        DynamicToast.makeError(getContext(), "Error editing Last Name").show();
                    }
                });
            }
        });

        textViewEmail.setOnClickListener(view -> {
            editTextEmail.setText(textViewEmail.getText());
            dialogEmail.show();
        });

        textViewName.setOnClickListener(view -> {
            editTextName.setText(textViewName.getText());
            dialogName.show();
        });

        textViewLastName.setOnClickListener(view -> {
            editTextLastName.setText(textViewLastName.getText());
            dialogLastName.show();
        });

        logout.setOnClickListener(v -> {
            onClickLogout();
        });

        return view;
    }

    /**
     * Method used to load the image of the profile.
     */
    public void loadImg() {
        String imageURL, image = myUser.getImage();

        if (image != null) {
            if ((image.startsWith("http") || image.startsWith("https"))
                    && (image.endsWith(".jpg") || image.endsWith(".png")
                    || image.endsWith(".JPG") || image.endsWith(".PNG")))
                imageURL = image;
            else
                imageURL = "https://t4.ftcdn.net/jpg/04/70/29/97/360_F_470299797_UD0eoVMMSUbHCcNJCdv2t8B2g1GVqYgs.jpg";
        } else
            imageURL = "https://t4.ftcdn.net/jpg/04/70/29/97/360_F_470299797_UD0eoVMMSUbHCcNJCdv2t8B2g1GVqYgs.jpg";

        Picasso.with(getContext()).load(imageURL).into(imageView);
    }


    /**
     * Method used to set the views.
     */
    private void loadViews() {
        btnfriends = view.findViewById(R.id.friends_btn);
        logout = view.findViewById(R.id.profile_logout_id);
        imageView = view.findViewById(R.id.profile_image);
        textViewEmail = view.findViewById(R.id.profile_email);
        textViewName = view.findViewById(R.id.profile_name);
        textViewLastName = view.findViewById(R.id.profile_last_name);
        textViewStats = view.findViewById(R.id.stats);
        numFriends = view.findViewById(R.id.numFriends);
    }


    /**
     * Method used to set the textViews of the current user profile.
     */
    private void setText() {
        textViewEmail.setText(myUser.getEmail());
        textViewName.setText(myUser.getName());
        textViewLastName.setText(myUser.getLast_name());
        getStats();
        getNumFriends();
    }


    /**
     * Method used to get the stats of the current user.
     */
    private void getStats() {
        APIUser.getInstance().getUserStats(Token.getToken(getContext()), myUser.getId(), new Callback<Stats>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Stats> call, Response<Stats> response) {
                textViewStats.setText("Comments:" + response.body().getNum_comments() + "\nAveragescore:"
                        + response.body().getAvg_score() + "\nPercentage comments:"
                        + response.body().getPercentage_commenters_below());
            }

            @Override
            public void onFailure(Call<Stats> call, Throwable t) {
                DynamicToast.makeError(getContext(), "Error loading stadistics").show();
            }
        });
    }


    /**
     * Method used to get the number of friends of the current user.
     */
    private void getNumFriends() {
        APIUser.getInstance().getFriends(Token.getToken(getContext()), myUser.getId(), new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                response.body().size();
                numFriends.setText(response.body().size() + " friends");
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                DynamicToast.makeError(getContext(), "Error loading friends").show();
            }
        });
    }


    /**
     * Method used to search the information of the current user.
     */
    private void searchUser() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences
                                                        ("credenciales", Context.MODE_PRIVATE);
        String sPEmail = sharedPreferences.getString("email", "Error, information does not exist.");
        APIUser.getInstance().getListUsers(Token.getToken(getContext()), new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                for (User u : response.body()) {
                    if (u.getEmail().equals(sPEmail)) {
                        myUser = u;
                        id = myUser.getId();
                        setText();
                        loadImg();
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                DynamicToast.makeError(getContext(), "Error on response API").show();
            }
        });
    }


    /**
     * Method used to logout from the current session.
     */
    private void onClickLogout() {
        // Deleting the saved info
        SharedPreferences sharedPreferences = getContext().getSharedPreferences
                ("credenciales", Context.MODE_PRIVATE);
        sharedPreferences.edit().remove("token").clear().apply();
        sharedPreferences.edit().remove("email").clear().apply();
        sharedPreferences.edit().putString("token", null).apply();
        sharedPreferences.edit().putString("email", null).apply();

        // Start LoginActivity and finish this one
        startActivity(new Intent(getContext(), LoginActivity.class));
    }
}
