package com.androidpprog2.openevents.view.fragments;

import static com.androidpprog2.openevents.view.activities.NavigationActivity.searchUser;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.CollapsibleActionView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventsFragment extends Fragment implements CollapsibleActionView {
    private APIEvents apiEvents;
    private List<Event> eventList, myEventList, filteredList = new ArrayList<>();
    private RecyclerView eventsRecyclerView;
    private EventsAdapter eventsAdapter;
    private LinearLayoutManager linearLayoutManager;
    private static final String TAG = "EventFragment";
    private View view;
    private ExtendedFloatingActionButton myEvents_fab;
    private User user = null;

    private Spinner spinner;
    private SearchView searchEventsView;
    private String[] spinnerListCategories = {"All events", "sports-grup7", "Excursió",
                                                "art-grup7", "music-grup7", "nightlife-grup7"};

    public EventsFragment() {
        apiEventsCall();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
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

//        user = searchUser(getContext());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, spinnerListCategories);

        apiEventsCall();

        searchEventsView = view.findViewById(R.id.search_bar_events);
        searchEventsView.clearFocus();
        searchEvents();


        spinner = view.findViewById(R.id.action_bar_spinner_events);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("ON ITEM SELECTED SPINNER: ", spinner.getSelectedItem().toString());
                /*for (Event event : eventList) {
                    if (event.getType().equals(spinner.getSelectedItem().toString())) {
                        Log.d("EVENTO: ", event.getName());
                        filteredList.add(event);
                    }
                }*/
                if (spinner.getSelectedItem().toString().equals("All events")) {
                    eventsAdapter = new EventsAdapter(eventList, getContext());
                    eventsRecyclerView.setAdapter(eventsAdapter);
                } else {
                    String type = spinner.getSelectedItem().toString();
                    filteredList.clear();
                    for (Event event : eventList) {
                        if (event.getType().equals(type)) filteredList.add(event);
                    }
                    eventsAdapter = new EventsAdapter(filteredList, getContext());
                    eventsRecyclerView.setAdapter(eventsAdapter);
//                getFilteredList();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        myEvents_fab = view.findViewById(R.id.my_events);
        myEvents_fab.setOnClickListener(view -> {
            user = searchUser(getContext());
            apiMyEventsCall();
        });


        return view;
    }

    public void getFilteredList() {
        String type = spinner.getSelectedItem().toString();
        for (Event event : eventList) {
            if (event.getType().equals(type)) filteredList.add(event);
        }
        eventsAdapter = new EventsAdapter(filteredList, getContext());
        eventsRecyclerView.setAdapter(eventsAdapter);
    }

    private void searchEvents() {
        searchEventsView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterEventsList(newText);
                return false;
            }
        });
    }


    private void filterEventsList(String s) {
        // Llamada a la API para filtrar lo del searcher
        apiEvents.getEventsSearch(Token.getToken(getContext()), s, s, s, new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                try {
                    if (response.isSuccessful()) {
                        eventList.clear();
                        eventList.addAll(response.body());
                        eventsAdapter.notifyDataSetChanged();
                    }
                } catch (Exception exception) {
                    Log.e("TAG", exception.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Log.d("onFailure:", "Fallo de lectura API filterEventsList");
            }
        });
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

    @Override
    public void onActionViewExpanded() {

    }

    @Override
    public void onActionViewCollapsed() {

    }
}
