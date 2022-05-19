package com.androidpprog2.openevents.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.androidpprog2.openevents.api.APIUser;
import com.androidpprog2.openevents.business.FriendRequest;
import com.androidpprog2.openevents.business.Token;
import com.androidpprog2.openevents.business.User;

import com.androidpprog2.openevents.view.activities.UserDetailActivity;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
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


    /**
     * VIEW HOLDER CLASS
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, lastNameTextView;
        ImageView imageView;
        ImageButton addUser;
        RelativeLayout row_fragment;

        /**
         *
         */
        ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.user_item_tittle_textView);
            addUser=view.findViewById(R.id.btn_add_user);
            row_fragment = view.findViewById(R.id.row_fragment);
            //lastNameTextView = view.findViewById(R.id.);
        }

        /**
         *
         */
        public void bind(int pos) {
            nameTextView.setText(userList.get(pos).getName()+" "+userList.get(pos).getLast_name());

//            delete_btn.setOnClickListener(v -> deleteItem(pos));
//            edit_btn.setOnClickListener(v -> editItem(pos));
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
                    }

                    @Override
                    public void onFailure(Call<FriendRequest> call, Throwable t) {

                    }
                });
            });

        }

    }
}
