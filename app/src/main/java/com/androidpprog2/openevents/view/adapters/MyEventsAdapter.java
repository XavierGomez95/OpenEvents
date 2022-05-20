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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.view.activities.EventDetailActivity;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class MyEventsAdapter extends RecyclerView.Adapter<MyEventsAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_my_events_element, parent, false);
        return new MyEventsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * VIEW HOLDER CLASS
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageButton delete_btn;
        ImageButton edit_btn;
        TextView textView;
        LinearLayout linearLayoutclic1;

        /**
         * Initializes checkbox and buttons finding by id
         *
         * @param view View
         */
        ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.icon_image_event_id);
            textView = view.findViewById(R.id.textView);
            delete_btn = view.findViewById(R.id.myEvents_element_delete_btn);
            edit_btn = view.findViewById(R.id.myEvents_element_edit_btn);
            linearLayoutclic1 = view.findViewById(R.id.row_fragment);
        }

        /**
         * Sets the Task name in the checkbox
         * Knows if a task checkbox has been checked and updates the Task boolean
         * onClickListener of delete and edit buttons
         * sets the buttons invisible  for default tasks
         *
         * @param pos position of the recycle view
         */
        public void bind(int pos) {
            textView.setText(list.get(pos).getName());
            delete_btn.setOnClickListener(v -> deleteItem(pos));
            edit_btn.setOnClickListener(v -> editItem(pos));
            loadImg(pos);
            linearLayoutclic1.setOnClickListener(v -> {
                    Intent intent = new Intent(activity, EventDetailActivity.class);
                    intent.putExtra("position", pos);
                    intent.putExtra("eventlist", (Serializable) list);

                    context.startActivity(intent);
            });

        }

        private void loadImg(int pos) {
            String imageURL, image = list.get(pos).getImage();

            if (image != null) {
                if ((image.startsWith("http") || image.startsWith("https"))
                        && (image.endsWith(".jpg") || image.endsWith(".png")
                        || image.endsWith(".jpeg") || image.endsWith(".JPG")
                        || image.endsWith(".PNG") || image.endsWith(".JPEG")))
                    imageURL = image;
                else imageURL = "https://t4.ftcdn.net/jpg/04/70/29/97/360_F_470299797_UD0eoVMMSUbHCcNJCdv2t8B2g1GVqYgs.jpg";
            } else imageURL = "https://t4.ftcdn.net/jpg/04/70/29/97/360_F_470299797_UD0eoVMMSUbHCcNJCdv2t8B2g1GVqYgs.jpg";

            Log.d("EVENT NAME : ", image);
            Log.d("URL : ", image);

            Picasso.with(context).load(imageURL).into(imageView);
        }


        public void deleteItem(int pos) {

        }

        /**
         * @param pos
         */
        public void editItem(int pos) {


        }

    }


    /**
     * ADAPTER TASK CLASS
     */
    private List<Event> list;
    private Context context;
    private Activity activity;


    public MyEventsAdapter(List<Event> list, Context context) {
        this.list = list;
        this.context = context;
        this.activity = (Activity) context;
    }
}
