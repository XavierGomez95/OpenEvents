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

/**
 * EVENTS ADAPTER CLASS
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    private List<Event> eventList;
    private Context context;
    private Activity activity;

    /**
     * Constructor.
     *
     * @param list A list of events (class Event).
     * @param context EventsFragment context.
     */
    public EventsAdapter(List<Event> list, Context context) {
        this.eventList = list;
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_event_element, parent, false);
        return new EventsAdapter.ViewHolder(itemView);
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
     * @return eventsList size.
     */
    @Override
    public int getItemCount() {
        return eventList.size();
    }


    /**
     * VIEW HOLDER CLASS
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
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
         * Set the events image and name, and manage the onClickListener of the cardView.
         * Calls {@link ViewHolder #loadImg(int)}
         *
         * @param pos Element position in the data set.
         */
        public void bind(int pos) {
            textView.setText(eventList.get(pos).getName());
            loadImg(pos);

            flameLayoutclic1.setOnClickListener(v -> {
                    Intent intent = new Intent(activity, EventDetailActivity.class);
                    intent.putExtra("position", pos);
                    intent.putExtra("eventlist", (Serializable) eventList);

                    context.startActivity(intent);
            });

        }


        /**
         * Method used to load the events profile images.
         *
         * @param pos position of the recycle view.
         */
        private void loadImg(int pos) {
            String imageURL, image = eventList.get(pos).getImage();

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

            //TODO TEMPORAL
            Log.d("EVENT NAME : ", eventList.get(pos).getName());
            Log.d("EVENT TYPE : ", eventList.get(pos).getType());
            Log.d("URL : ", image);

            Picasso.with(context).load(imageURL).into(imageView);
        }


        /**
         * Method called to delete a item of the eventsList.
         *
         * @param pos item position.
         */
        public void deleteItem(int pos) {

        }

        /**
         * Method called to edit a item of the eventsList.
         *
         * @param pos item position.
         */
        public void editItem(int pos) {

        }
    }
}

