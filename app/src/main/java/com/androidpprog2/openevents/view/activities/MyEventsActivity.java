package com.androidpprog2.openevents.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.view.adapters.MyEventsAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * MY EVENTS ACTIVITY CLASS
 */
public class MyEventsActivity extends AppCompatActivity {
    private Intent intent;
    private List<Event> myEventList = new ArrayList<>();
    private RecyclerView myEventsRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyEventsAdapter myEventsAdapter;
    private ExtendedFloatingActionButton createEvent_fab;
    private TextView textView_noEvents;


    /**
     * Setting the essential layout parameters.
     * Manage the functions of the create event button.
     *
     * @param savedInstanceState reference to a Bundle object that is passed into the onCreate.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        intent = getIntent();

        Bundle bundle = intent.getBundleExtra("BUNDLE");
        if (bundle != null) {
            myEventList.addAll((List<Event>) bundle.getSerializable("MyEventList"));

        } else {
            textView_noEvents = findViewById(R.id.textView_noEvents);
            textView_noEvents.setText("No event created yet, Add an event!");
        }

        // TODO ELIMINAR AL FINAL
        for (Event e : myEventList)
            Log.d("My Event List: ", e.getName());

        createEvent_fab = findViewById(R.id.create_event_floating_button);
        createEvent_fab.setOnClickListener(view -> {
            startActivity(new Intent(this, CreateEventActivity.class));
        });

        myEventsRecyclerView = findViewById(R.id.my_events_recycler_view);
        myEventsAdapter = new MyEventsAdapter(myEventList, this);
        myEventsRecyclerView.setAdapter(myEventsAdapter);
    }
}
