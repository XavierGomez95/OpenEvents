package com.androidpprog2.openevents.view.fragments;

import static com.androidpprog2.openevents.view.activities.NavigationActivity.searchUser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.persistance.api.APIEvents;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.business.Token;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.view.adapters.EventsAdapter;
import com.androidpprog2.openevents.view.activities.MyEventsActivity;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsFragment extends Fragment {
    private APIEvents apiEvents;
    private List<Event> eventList, myEventList;
    private RecyclerView eventsRecyclerView;
    private EventsAdapter eventsAdapter;
    private static final String TAG = "EventFragment";
    private View view;

    private ExtendedFloatingActionButton myEvents_fab;
    private User user = null;


    public EventsFragment() {
        apiEventsCall();
    }

  /*  @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
*/


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_events, container, false);

        user = searchUser(getContext());
        apiEventsCall();


        myEvents_fab = view.findViewById(R.id.my_events);
        myEvents_fab.setOnClickListener(view -> {
            user = searchUser(getContext());
            apiMyEventsCall();


        });


        return view;
    }

    private void apiMyEventsCall() {
        apiEvents = APIEvents.getInstance();
        apiEvents.getMyEvents(Token.getToken(getContext()), user.getId(), new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                Intent intent = new Intent(getActivity(), MyEventsActivity.class);
                try {

                    if (response.isSuccessful()) {
                        Log.d("HOLAAA", "AA" + response.body().get(0).getName());
                        //myEventList.addAll(eventList);
                        myEventList = response.body();
                        Bundle args = new Bundle();
                        args.putSerializable("MyEventList", (Serializable) myEventList); // myEventList CAMBIAR LA LISTA
                        intent.putExtra("BUNDLE", args);
                        // TODO TEMPORAL
                        for (Event e : eventList)
                            Log.d("Event List: ", e.getName());
                    }

                } catch (Exception exception) {
                    Log.e("TAG", exception.getMessage());
                }
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {

            }
        });
    }

    private void apiEventsCall() {
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


        /*// TODO: DELETE TEMPORAL
        for (Event e : eventList)
            Log.d("EventsFragment List", e.getName());*/


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    public List<Event> getEventList() {
        return eventList;
    }
}
