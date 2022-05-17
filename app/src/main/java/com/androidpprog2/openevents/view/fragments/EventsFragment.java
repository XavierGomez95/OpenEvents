package com.androidpprog2.openevents.view.fragments;

import static com.androidpprog2.openevents.view.activities.NavigationActivity.myUser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.api.APIEvents;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.view.EventsAdapter;
import com.androidpprog2.openevents.view.activities.CreateEventActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsFragment extends Fragment {
    private APIEvents apiEvents;
    private List<Event> eventList = new ArrayList<>();
    private RecyclerView eventsRecyclerView;
    private EventsAdapter eventsAdapter;
    private static final String TAG = "EventFragment";
    private View view;

    private Button createEvent_btn;
    private User user = myUser;


    public EventsFragment() {
        apiCall();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //eventsAdapter.setItems(eventList);

        //recyclerViewCall();
    }

    //private void recyclerViewCall() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_events, container, false);
        apiCall();


        createEvent_btn = view.findViewById(R.id.create_event_btn);

        createEvent_btn.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), CreateEventActivity.class));
        });
        return view;
    }

    private void apiCall() {
        final boolean success = false;
        apiEvents = APIEvents.getInstance();
        apiEvents.getListEvents(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                try {
                    if (response.isSuccessful()) {
                        eventList = response.body();
                        eventsRecyclerView = view.findViewById(R.id.recycler_view_events);
                        eventsAdapter = new EventsAdapter(eventList, getContext());
                        eventsRecyclerView.setAdapter(eventsAdapter);
                    }
                } catch (Exception exception) {
                    Log.e("TAG", exception.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.e(TAG, "Connection Error on eventList");
                Log.e(TAG, t.toString());
            }

        });


        // TEMPORAL
        for (Event e : eventList)
            Log.d("EventsFragment List", e.getName());


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    public List<Event> getEventList() {
        return eventList;
    }
}
