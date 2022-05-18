package com.androidpprog2.openevents.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.User;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    private List<User> userList;

    public UsersAdapter(List<User> userList) {
        this.userList = userList;
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
        Button addUser;

        /**
         *
         */
        ViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.user_item_tittle_textView);
            //lastNameTextView = view.findViewById(R.id.);
        }

        /**
         *
         */
        public void bind(int pos) {
            nameTextView.setText(userList.get(pos).getName());
            //lastNameTextView.setText(userList.get(pos).getLast_name());

            //imageView.setImage //LO QUE PASO IRIS
        }

        public void deleteItem(int pos) {

        }

        /**
         * @param pos
         */
        public void editItem(int pos) {

        }
    }
}
