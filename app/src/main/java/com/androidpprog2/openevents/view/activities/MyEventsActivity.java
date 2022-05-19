package com.androidpprog2.openevents.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.view.MyEventsAdapter;

import java.util.List;

public class MyEventsActivity extends AppCompatActivity {
    private List<Event> myEventList;
    private RecyclerView myEventsRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyEventsAdapter myEventsAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_my_events);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        myEventList = (List<Event>) bundle.getSerializable("MyEventList");

        myEventsRecyclerView = findViewById(R.id.recycler_view_events);
        myEventsAdapter = new MyEventsAdapter(myEventList, this);
        myEventsRecyclerView.setAdapter(myEventsAdapter);

        myEventsRecyclerView = findViewById(R.id.my_events_recycler_view);
    }
}
