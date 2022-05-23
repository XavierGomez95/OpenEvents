package com.androidpprog2.openevents.view.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.business.Token;
import com.androidpprog2.openevents.persistance.api.APIEvents;
import com.androidpprog2.openevents.view.adapters.MyEventsAdapter;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * MY EVENTS ACTIVITY CLASS
 */
public class MyEventsActivity extends AppCompatActivity {
    private Intent intent;
    private List<Event> myEventList = new ArrayList<>();
    private Integer id;
    private RecyclerView myEventsRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyEventsAdapter myEventsAdapter;
    private ExtendedFloatingActionButton createEvent_fab;
    private TextView textView_noEvents;
    private APIEvents apiEvents;


    /**
     * Setting the essential layout parameters.
     * Manage the functions of the create event button.
     *
     * @param savedInstanceState reference to a Bundle object that is passed into the onCreate.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        setContentView(R.layout.activity_my_events);

        apiEvents = APIEvents.getInstance();

        loadViews();

        if (myEventList.isEmpty()) textView_noEvents.setText("No event created yet, Add an event!");

        createEvent_fab.setOnClickListener(view -> {
            Intent editIntent = new Intent(getApplicationContext(), CreateEditEventActivity.class);
            startActivity(editIntent);
        });

        intent = getIntent();
        id = intent.getIntExtra("id", -1);
        apiMyEventsCall();
    }

    @Override
    public void onResume() {
        super.onResume();
        apiMyEventsCall();

    }

    /**
     * Method called to load the views.
     */
    private void loadViews() {
        textView_noEvents = findViewById(R.id.textView_noEvents);
        createEvent_fab = findViewById(R.id.create_event_floating_button);
        myEventsRecyclerView = findViewById(R.id.my_events_recycler_view);
    }

    /**
     * Method used to get a list of all the events created by the user with the session started
     * from the API.
     */
    private void apiMyEventsCall() {
        Context c = this;

        apiEvents.getMyEvents(Token.getToken(this), id, new Callback<List<Event>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<List<Event>> call, @NonNull Response<List<Event>> response) {
                try {
                    if (response.code() == 200) {
                        myEventList = response.body();
                        if (myEventList != null && !myEventList.isEmpty()) {
                            myEventsAdapter = new MyEventsAdapter(myEventList, c);
                            myEventsRecyclerView.setAdapter(myEventsAdapter);
                            textView_noEvents.setText("");
                        } else {
                            textView_noEvents.setText("No event created yet, Add an event!");
                        }
                    }
                } catch (Exception exception) {
                    DynamicToast.makeError(c, "Error on response API").show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Event>> call, @NonNull Throwable t) {
                DynamicToast.makeError(c, "Error API connection").show();

            }
        });
    }


}
