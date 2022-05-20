package com.androidpprog2.openevents.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.persistance.api.APIUser;
import com.androidpprog2.openevents.business.FriendRequest;
import com.androidpprog2.openevents.business.Token;
import com.androidpprog2.openevents.business.User;

import com.androidpprog2.openevents.view.activities.UserDetailActivity;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    /**
     * VIEW HOLDER CLASS
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageButton addUser;
        RelativeLayout row_fragment;
        ImageView imageView;

        /**
         *
         */
        ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.user_item_tittle_textView);
            imageView = view.findViewById(R.id.icon_image_user_id);
            addUser = view.findViewById(R.id.btn_add_user);
            row_fragment = view.findViewById(R.id.row_fragment);
        }

        /**
         *
         */
        public void bind(int pos) {
            nameTextView.setText(userList.get(pos).getName() + " " + userList.get(pos).getLast_name());
            loadImg(pos);
            addUser.setVisibility(View.VISIBLE);
            row_fragment.setOnClickListener(view -> {
                Intent intent = new Intent(activity, UserDetailActivity.class);
                intent.putExtra("position", pos);
                intent.putExtra("userList", (Serializable) userList);

                context.startActivity(intent);
            });
            addUser.setOnClickListener(view -> {
                APIUser.getInstance().addFriendRequest(Token.getToken(context), userList.get(pos).getId(), new Callback<FriendRequest>() {
                    @Override
                    public void onResponse(Call<FriendRequest> call, Response<FriendRequest> response) {
                        DynamicToast.makeSuccess(context, "Successful friend request").show();
                        addUser.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onFailure(Call<FriendRequest> call, Throwable t) {

                    }
                });
            });
        }

        private void loadImg(int pos) {
            String imageURL, image = userList.get(pos).getImage();

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

            Log.d("EVENT NAME : ", userList.get(pos).getName());
            Log.d("URL : ", image);

            Picasso.with(context).load(imageURL).into(imageView);
        }
    }

    private List<User> userList;
    private Context context;
    private Activity activity;

    public UsersAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
        this.activity = (Activity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_user_element, parent, false);
        return new UsersAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    /*public void setFilteredList(List<User> filteredList) {
        this.userList = filteredList;
    }*/





}
