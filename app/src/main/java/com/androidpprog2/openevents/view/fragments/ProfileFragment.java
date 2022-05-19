package com.androidpprog2.openevents.view.fragments;

import static com.androidpprog2.openevents.view.activities.NavigationActivity.searchUser;

import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.api.APIUser;
import com.androidpprog2.openevents.business.Stats;
import com.androidpprog2.openevents.business.Token;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.view.activities.LoginActivity;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {
    private TextView logout, textViewEmail, textViewName, textViewLastName, textViewStats, numFriends;
    private String imageURL;
    private CircleImageView circleImageView;
    private AlertDialog dialogEmail, dialogName, dialogLastName;
    private EditText editTextEmail, editTextName, editTextLastName;
    private View view;
    private User myUser = null;
    private APIUser apiUser;


    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                          @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        myUser = searchUser(getContext());
        logout = view.findViewById(R.id.profile_logout_id);

        circleImageView = view.findViewById(R.id.profile_image);

        dialogEmail = new AlertDialog.Builder(getActivity()).create();
        dialogName = new AlertDialog.Builder(getActivity()).create();
        dialogLastName = new AlertDialog.Builder(getActivity()).create();

        editTextEmail = new EditText(getActivity());
        editTextName = new EditText(getActivity());
        editTextLastName = new EditText(getActivity());

        if (myUser != null) {
            loadText();
        }

        // TODO: QUE SE CARGUE LA IMAGEN AL INICIAR EL FRAGMENT
        // SI LA PONGO EN LOS EDITORES DE TEXTO FUNCIONA
        loadImg();


        dialogEmail.setTitle(" Edit Email ");
        dialogEmail.setView(editTextEmail);

        dialogEmail.setButton(DialogInterface.BUTTON_POSITIVE, "SAFE",
                (dialogInterface, i) -> {
                    apiUser = APIUser.getInstance();
                    String oldEmail = myUser.getEmail();
                    myUser.setEmail(editTextEmail.getText().toString());
                    apiUser.editUser(Token.getToken(getContext()), myUser, new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            textViewEmail.setText(myUser.getEmail());
                            DynamicToast.makeSuccess(getContext(), "Email edited successfully").show();
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            myUser.setEmail(oldEmail);
                            DynamicToast.makeError(getContext(), "Error editing email").show();

                        }
                    });
                    loadImg(); // TODO: SI HAGO ESTO SE CARGA LA IMAGEN
                });


        dialogName.setTitle(" Edit Name ");
        dialogName.setView(editTextName);

        dialogName.setButton(DialogInterface.BUTTON_POSITIVE, "SAFE",
                (dialogInterface, i) -> {
                    apiUser = APIUser.getInstance();
                    String oldName = myUser.getName();
                    if (!oldName.equals(editTextName.getText().toString())) {
                        myUser.setName(editTextName.getText().toString());
                        apiUser.editUser(Token.getToken(getContext()), myUser, new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                textViewName.setText(myUser.getName());
                                DynamicToast.makeSuccess(getContext(), "Name edited successfully").show();
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                myUser.setName(oldName);
                                DynamicToast.makeError(getContext(), "Error editing name").show();

                            }
                        });
                    }
                    loadImg(); // TODO: SI HAGO ESTO SE CARGA LA IMAGEN
                });


        dialogLastName.setTitle(" Edit Last Name ");
        dialogLastName.setView(editTextLastName);

        dialogLastName.setButton(DialogInterface.BUTTON_POSITIVE, "SAFE",
                (dialogInterface, i) -> {
                    apiUser = APIUser.getInstance();
                    String oldLastName = myUser.getLast_name();
                    if (!oldLastName.equals(editTextLastName.getText().toString())) {

                        myUser.setLast_name(editTextLastName.getText().toString());
                        apiUser.editUser(Token.getToken(getContext()), myUser, new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                textViewLastName.setText(myUser.getLast_name());
                                DynamicToast.makeSuccess(getContext(), "Last Name edited successfully").show();
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                myUser.setEmail(oldLastName);
                                DynamicToast.makeError(getContext(), "Error editing Last Name").show();

                            }
                        });
                    }
                    loadImg(); // TODO: SI HAGO ESTO SE CARGA LA IMAGEN
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


//        token = new Token(savedInstanceState.getString("tokenStr")); // Como pasar el objeto token
//        Log.e("TAG", "Profile token: " + token.getAccessToken()); // TEMPORAL


        logout.setOnClickListener(v -> {
            onClickLogout();
        });


        return view;
    }

    private void loadImg() {
        if (myUser.getImage() != null) {
            if (myUser.getImage().startsWith("http") || myUser.getImage().startsWith("https"))
                imageURL = myUser.getImage();
            else imageURL = "http://puigmal.salle.url.edu/img/" + myUser.getImage();
        }

        Picasso.with(getActivity()).load(imageURL).into(circleImageView);
    }

    private void loadText() {
        textViewEmail = view.findViewById(R.id.profile_email);
        textViewEmail.setText(myUser.getEmail());

        textViewName = view.findViewById(R.id.profile_name);
        textViewName.setText(myUser.getName());

        textViewLastName = view.findViewById(R.id.profile_last_name);
        textViewLastName.setText(myUser.getLast_name());
        getStats();
        getNumFriends();
    }

    private void getStats() {
        textViewStats = view.findViewById(R.id.stats);
        APIUser.getInstance().getUserStats(Token.getToken(getContext()), myUser.getId(), new Callback<Stats>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<Stats> call, Response<Stats> response) {
                textViewStats.setText("Comments:" + response.body().getNum_comments() + "\nAveragescore:" + response.body().getAvg_score() + "\nPercentage comments:" + response.body().getPercentage_commenters_below());
            }

            @Override
            public void onFailure(Call<Stats> call, Throwable t) {
                DynamicToast.makeError(getContext(), "Error loading stadistics").show();


            }
        });
    }

    private void getNumFriends() {
        numFriends = view.findViewById(R.id.numFriends);
        APIUser.getInstance().getFiends(Token.getToken(getContext()), myUser.getId(), new Callback<List<User>>() {

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


    private void onClickLogout() {
        // Deleting the saved info
        SharedPreferences sharedPreferences = getContext().getSharedPreferences
                ("credenciales", Context.MODE_PRIVATE);
        sharedPreferences.edit().remove("token").clear().apply();
        sharedPreferences.edit().remove("email").clear().apply();
        sharedPreferences.edit().putString("token", null).apply();
        sharedPreferences.edit().putString("email", null).apply();

        Log.e("LogOut", sharedPreferences.getString("token", "Non existing token"));

        // Start LoginActivity and finish this one
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

}
