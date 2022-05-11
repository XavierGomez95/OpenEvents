package com.androidpprog2.openevents.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.Event;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private List<Event> eventList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomAdapter(List<Event> mDataSet, Context context) {
        this.eventList = mDataSet;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public CustomAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item, viewGroup, false);
        return new CustomAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");
        final Event event = eventList.get(position);

        viewHolder.eventName.setText(event.getName());
        //viewHolder.iconImage.setImageBitmap(event.getImage());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    // TEMPORAL
    public void setItems(List<Event> eventList) {
        this.eventList = eventList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImage;
        Bitmap bitmap;
        TextView eventName;
        RadioButton radioButton;


        public ViewHolder(View itemView) {
            super(itemView);
            eventName = (TextView) itemView.findViewById(R.id.event_name_text_id);
            iconImage = (ImageView) itemView.findViewById(R.id.icon_image_event_id);
            radioButton = (RadioButton) itemView.findViewById(R.id.status_event_id);

        }
    }
}