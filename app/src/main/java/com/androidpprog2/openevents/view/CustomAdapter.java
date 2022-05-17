package com.androidpprog2.openevents.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.Event;

import java.util.List;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    /**
     * VIEW HOLDER CLASS
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageButton delete_btn;
        ImageButton edit_btn;
        TextView textView;

        /**
         * Initializes checkbox and buttons finding by id
         *
         * @param view View
         */
        ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textView);
            delete_btn = view.findViewById(R.id.btn_delete);
            edit_btn = view.findViewById(R.id.btn_save_edit);
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
            Log.d("IRIS", "LIST:" + list.get(2).getName());
            textView.setText(list.get(pos).getName());

            delete_btn.setOnClickListener(v -> deleteItem(pos));
            edit_btn.setOnClickListener(v -> editItem(pos));


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


    public CustomAdapter(List<Event> list) {
        this.list = list;
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

