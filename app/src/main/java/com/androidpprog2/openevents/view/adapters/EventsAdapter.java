package com.androidpprog2.openevents.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.view.activities.EventDetailActivity;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;


public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    /**
     * VIEW HOLDER CLASS
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
//        ImageButton delete_btn;
//        ImageButton edit_btn;
        ImageView imageView;
        TextView textView;
        FrameLayout flameLayoutclic1;

        /**
         * Initializes checkbox and buttons finding by id
         *
         * @param view View
         */
        ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textView);
            imageView = view.findViewById(R.id.icon_image_event_id);
            flameLayoutclic1 = view.findViewById(R.id.row_fragment);
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
            loadImg(pos);
            flameLayoutclic1.setOnClickListener(v -> {
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
    private ImageView imageView;


    public EventsAdapter(List<Event> list, Context context) {
        this.list = list;
        this.context = context;
        this.activity = (Activity) context;
    }


    /**
     * Methods that calls an item of a task ui (fragment_task.xml)
     *
     * @param parent   Viewgroup
     * @param viewType int
     * @return itemview
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_event_element, parent, false);
        return new ViewHolder(itemView);
    }

    /**
     * Function that manages the position of each task
     * calls {@link ViewHolder #bind(int)}
     *
     * @param holder   view
     * @param position integer of tasks
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }


    /**
     * Returns the list size
     *
     * @return size of items in list
     */
    @Override
    public int getItemCount() {
        return list.size();
    }


}

