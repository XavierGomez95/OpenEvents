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
import com.androidpprog2.openevents.business.FriendRequest;
import com.androidpprog2.openevents.business.Token;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.persistance.api.APIUser;
import com.androidpprog2.openevents.view.activities.UserDetailActivity;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * FRIENDS ADAPTER CLASS
 */
public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private List<User> userList;
    private Context context;
    private Activity activity;

    /**
     * Constructor.
     *
     * @param userList A list of friends (class User).
     * @param context MyFriendsActivity context.
     */
    public FriendsAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
        this.activity = (Activity) context;
    }

    /**
     * Called by the recyclerView when it needs to represent a new item.
     *
     * @param parent ViewGroup into which the new View will be added.
     * @param viewType the View type of the new View.
     * @return a new ViewHolder of the viewType.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_friends_element, parent, false);
        return new FriendsAdapter.ViewHolder(itemView);
    }

    /**
     * Called by the recyclerView to display the data at the specified position.
     * Calls {@link MyEventsAdapter.ViewHolder #bind(int)}
     *
     * @param holder represent the contents of the item at the given position in the data set.
     * @param position Element position in the data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    /**
     *
     *
     * @return userList size
     */
    @Override
    public int getItemCount() {
        return userList.size();
    }



    /**
     * VIEW HOLDER CLASS
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        RelativeLayout row_fragment;
        ImageView imageView;

        /**
         * Initializes checkbox and buttons finding by id.
         *
         * @param view
         */
        ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.textView);
            imageView = view.findViewById(R.id.icon_image_user_id);
            row_fragment = view.findViewById(R.id.row_fragment);
        }

        /**
         * Set the friend image, name and last name, and manage the onClickListener of the cardView.
         *
         * @param pos Element position in the data set.
         */
        public void bind(int pos) {
            nameTextView.setText(userList.get(pos).getName() + " " + userList.get(pos).getLast_name());
            loadImg(pos);

            row_fragment.setOnClickListener(view -> {
                Intent intent = new Intent(activity, UserDetailActivity.class);
                intent.putExtra("position", pos);
                intent.putExtra("userList", (Serializable) userList);
                intent.putExtra("friend", true);

                context.startActivity(intent);
            });
        }

        /**
         * Method used to load the friends profile images.
         *
         * @param pos position of the recycle view.
         */
        private void loadImg(int pos) {
            String imageURL, image = userList.get(pos).getImage();

            if (image != null) {
                if ((image.startsWith("http") || image.startsWith("https"))
                        && (image.endsWith(".jpg") || image.endsWith(".png")
                        || image.endsWith(".JPG") || image.endsWith(".PNG")))
                    imageURL = image;
                else
                    imageURL = "https://t4.ftcdn.net/jpg/04/70/29/97/360_F_470299797_UD0eoVMMSUbHCcNJCdv2t8B2g1GVqYgs.jpg";
            } else
                imageURL = "https://t4.ftcdn.net/jpg/04/70/29/97/360_F_470299797_UD0eoVMMSUbHCcNJCdv2t8B2g1GVqYgs.jpg";

            Picasso.with(context).load(imageURL).into(imageView);
        }
    }
}
