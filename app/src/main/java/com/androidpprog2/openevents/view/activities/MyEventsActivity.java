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

public class MyEventsActivity extends AppCompatActivity {
    private Intent intent;
    private List<Event> myEventList = new ArrayList<>();
    private RecyclerView myEventsRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyEventsAdapter myEventsAdapter;
    private ExtendedFloatingActionButton createEvent_fab;
    private TextView textView_noEvents;


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

        // TODO TEMPORAL
        for (Event e : myEventList)
            Log.d("My Event List: ", e.getName());

        createEvent_fab = findViewById(R.id.create_event_floating_button);
        createEvent_fab.setOnClickListener(view -> {
            startActivity(new Intent(this, CreateEventActivity.class));
        });

        myEventsRecyclerView = findViewById(R.id.my_events_recycler_view);
        myEventsAdapter = new MyEventsAdapter(myEventList, this);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

        myEventsRecyclerView.setAdapter(myEventsAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
