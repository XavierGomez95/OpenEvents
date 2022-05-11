package com.androidpprog2.openevents.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.api.APIEvents;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.view.CustomAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsFragment extends Fragment {
    private APIEvents apiEvents;
    private List<Event> eventList = new ArrayList<>();
    private RecyclerView eventsRecyclerView;
    private CustomAdapter eventsAdapter;
    private static final String TAG = "EventFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //eventsAdapter.setItems(eventList);

        //recyclerViewCall();
    }

    //private void recyclerViewCall() { }

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        //android.view.View root = inflater.inflate(R.layout.fragment_events, container, false);
        android.view.View root =
                inflater.inflate(R.layout.list_event_element, container, false);
        eventsRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_view_events);

        if (eventList.size() == 0) apiCall();

        eventsAdapter = new CustomAdapter(eventList, getContext());
//        eventsRecyclerView.setLayoutManager(new LinearLayoutManager
//                (getContext(), LinearLayoutManager.VERTICAL, false));
//        eventsRecyclerView.setAdapter(eventsAdapter);

        return root;
    }

    private void apiCall() {
        apiEvents = APIEvents.getInstance();
        apiEvents.getListEvents(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                try {
                    if (response.isSuccessful()) {
                        eventList = response.body();
                        eventsRecyclerView.setAdapter(eventsAdapter);
                    }
                } catch (Exception exception) {
                    Log.e("TAG", exception.getMessage());
                }

                // TEMPORAL
                for (Event list: eventList)
                    Log.e("EventsFragment List", list.getName());
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.d(TAG, "Connection Error on eventList");
                Log.d(TAG, t.toString());
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }
}
