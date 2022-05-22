package com.androidpprog2.openevents.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.business.Token;
import com.androidpprog2.openevents.persistance.api.APIEvents;
import com.androidpprog2.openevents.view.activities.CreateEditEventActivity;
import com.androidpprog2.openevents.view.activities.EventDetailActivity;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MyY EVENTS ADAPTER CLASS
 */
public class MyEventsAdapter extends RecyclerView.Adapter<MyEventsAdapter.ViewHolder> {
    private List<Event> myEventsList;
    private Context context;
    private Activity activity;


    /**
     * Constructor.
     *
     * @param list    of events.
     * @param context MyEventsActivity context.
     */
    public MyEventsAdapter(List<Event> list, Context context) {
        this.myEventsList = list;
        this.context = context;
        this.activity = (Activity) context;
    }


    /**
     * Called by the recyclerView when it needs to represent a new item.
     * calls {@link EventsAdapter.ViewHolder #bind(int)}
     *
     * @param parent   ViewGroup into which the new View will be added.
     * @param viewType the View type of the new View.
     * @return a new ViewHolder of the viewType.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_my_events_element, parent, false);
        return new MyEventsAdapter.ViewHolder(itemView);
    }


    /**
     * Called by the recyclerView to display the data at the specified position.
     * Calls {@link ViewHolder #bind(int)}
     *
     * @param holder   represent the contents of the item at the given position in the data set.
     * @param position Element position in the data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }


    /**
     * @return myEventsList size
     */
    @Override
    public int getItemCount() {
        return myEventsList.size();
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
         * Initializes checkbox and buttons finding by id.
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
         * @param pos Element position in the data set.
         */
        public void bind(int pos) {
            textView.setText(myEventsList.get(pos).getName());
            delete_btn.setOnClickListener(v -> deleteItem(pos));
            edit_btn.setOnClickListener(v -> editItem(pos));
            loadImg(pos);

            linearLayoutclic1.setOnClickListener(v -> {
                Intent intent = new Intent(activity, EventDetailActivity.class);
                intent.putExtra("position", pos);
                intent.putExtra("eventlist", (Serializable) myEventsList);

                context.startActivity(intent);
            });

        }

        /**
         * Method used to load the own events profile images.
         *
         * @param pos position of the recycle view.
         */
        private void loadImg(int pos) {
            String imageURL, image = myEventsList.get(pos).getImage();

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
            Picasso.with(context).load(imageURL).into(imageView);
        }

        /**
         * Method called to delete an owned event.
         *
         * @param pos item position.
         */
        public void deleteItem(int pos) {
            APIEvents apiEvents = new APIEvents();
            apiEvents.deleteEvent(Token.getToken(context), myEventsList.get(pos).getId(), new Callback<Event>() {
                @Override
                public void onResponse(@NonNull Call<Event> call, @NonNull Response<Event> response) {
                    DynamicToast.makeSuccess(context, "The " + myEventsList.get(pos).getName() + " event has been deleted").show();
                    myEventsList.remove(pos);
                    notifyItemRemoved(pos);
                    notifyItemRangeChanged(pos, myEventsList.size());
                }

                @Override
                public void onFailure(@NonNull Call<Event> call, @NonNull Throwable t) {
                    DynamicToast.makeError(context, "Error API connection").show();
                }
            });


        }

        /**
         * Method called to edit an owned event.
         *
         * @param pos item position.
         */
        public void editItem(int pos) {
            Intent intent = new Intent(activity, CreateEditEventActivity.class);
            intent.putExtra("event", myEventsList.get(pos));
            context.startActivity(intent);
        }

    }


}
