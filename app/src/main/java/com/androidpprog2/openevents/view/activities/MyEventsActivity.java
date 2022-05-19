package com.androidpprog2.openevents.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_my_events);

        intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        myEventList.addAll((List<Event>) bundle.getSerializable("MyEventList"));

        // TODO TEMPORAL
        for (Event e : myEventList)
            Log.d("My Event List: ", e.getName());

        /*createEvent_fab = findViewById(R.id.create_event_floating_button);
        createEvent_fab.setOnClickListener(view -> {
            startActivity(new Intent(this, CreateEventActivity.class));
        });*/

        myEventsRecyclerView = findViewById(R.id.my_events_recycler_view);
        myEventsAdapter = new MyEventsAdapter(myEventList, this);
        myEventsRecyclerView.setAdapter(myEventsAdapter);
    }
}
