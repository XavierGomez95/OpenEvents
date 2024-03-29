package com.androidpprog2.openevents.view.fragments;

import static com.androidpprog2.openevents.view.activities.NavigationActivity.searchUser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * EVENTS FRAGMENT CLASS
 */
public class EventsFragment extends Fragment {
    private APIEvents apiEvents;
    private List<Event> eventList;
    private RecyclerView eventsRecyclerView;
    private EventsAdapter eventsAdapter;
    private LinearLayoutManager linearLayoutManager;
    private View view;
    private ExtendedFloatingActionButton myEvents_fab;
    private Button bestEvents_btn, allEvents_btn;
    private User user = null;
    private Spinner spinner, searcherSpinner;
    private SearchView searchEventsView;
    private String filterType = "";
    private final String[] spinnerListCategories = {"Type Filter", "sports",
            "art", "music", "nightlife"};
    private final String[] spinnerListFilters = {"location", "keyword", "date"};


    /**
     * Setting the essential layout parameters.
     *
     * @param savedInstanceState reference to a Bundle object that is passed into the onCreate.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
    }


    /**
     * Inflating the layout of the EventsFragment.
     * Loading views, api calls, and management of buttons, spinners, and search views.
     *
     * @param inflater           object used to inflate any views in the fragment.
     * @param container          used to generate the LayoutParams of the view.
     * @param savedInstanceState not used.
     * @return EventsFragment view.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_events, container, false);
        apiEvents = APIEvents.getInstance();

        loadViews();
        apiEventsCall();


        ArrayAdapter<String> searcherSpinnerAdapter = new ArrayAdapter<>(getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, spinnerListFilters);
        searcherSpinner.setAdapter(searcherSpinnerAdapter);
        searcherSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                filterType = searcherSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                filterType = spinnerListFilters[0];
            }
        });


        searchEventsView.clearFocus();
        searchEventsView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                switch (filterType) {
                    case "location":
                        getFilteredListByLocation(newText);
                        break;
                    case "keyword":
                        getFilteredListByKeyword(newText);
                        break;
                    case "date":
                        getFilteredListByDate(newText);
                        break;
                }
                return false;
            }
        });


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, spinnerListCategories);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getFilteredListByCategory();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        myEvents_fab.setOnClickListener(view -> {
            user = searchUser(getContext());
            Intent intent = new Intent(getActivity(), MyEventsActivity.class);
            intent.putExtra("id", user.getId());
            startActivity(intent);
        });


        bestEvents_btn.setOnClickListener(view -> {
            bestEventsFilter();
            spinner.setSelection(0);
        });

        allEvents_btn.setOnClickListener(view -> {
            apiEventsCall();
            spinner.setSelection(0);
        });

        return view;
    }


    /**
     * Show the filtered list in the layout.
     *
     * @param location string wrote by the user in the SearchView.
     */
    private void getFilteredListByLocation(String location) {
        apiEvents.getEventsSearch(Token.getToken(getContext()), location, null,
                null, new Callback<List<Event>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Event>> call, @NonNull Response<List<Event>> response) {
                        try {
                            if (response.isSuccessful()) {
                                eventList = response.body();
                                eventsAdapter = new EventsAdapter(eventList, getContext());
                                eventsRecyclerView.setLayoutManager(linearLayoutManager);
                                eventsRecyclerView.setAdapter(eventsAdapter);
                            }
                        } catch (Exception exception) {
                            DynamicToast.makeError(getContext(), "Error API on response").show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Event>> call, Throwable t) {
                        DynamicToast.makeError(getContext(), "Error API connection").show();
                    }
                });
    }

    /**
     * Show the filtered list in the layout.
     *
     * @param keyword string wrote by the user in the SearchView.
     */
    private void getFilteredListByKeyword(String keyword) {
        apiEvents.getEventsSearch(Token.getToken(getContext()), null, keyword,
                null, new Callback<List<Event>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Event>> call, @NonNull Response<List<Event>> response) {
                        try {
                            if (response.isSuccessful()) {
                                eventList = response.body();
                                eventsAdapter = new EventsAdapter(eventList, getContext());
                                eventsRecyclerView.setLayoutManager(linearLayoutManager);
                                eventsRecyclerView.setAdapter(eventsAdapter);
                            }
                        } catch (Exception exception) {
                            DynamicToast.makeError(getContext(), "Error API on response").show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Event>> call, @NonNull Throwable t) {
                        DynamicToast.makeError(getContext(), "Error API connection").show();
                    }
                });
    }

    /**
     * Show the filtered list in the layout
     *
     * @param date string wrote by the user in the SearchView.
     */
    private void getFilteredListByDate(String date) {
        apiEvents.getEventsSearch(Token.getToken(getContext()), null, null,
                date, new Callback<List<Event>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Event>> call, @NonNull Response<List<Event>> response) {
                        try {
                            if (response.isSuccessful()) {
                                eventList = response.body();
                                eventsAdapter = new EventsAdapter(eventList, getContext());
                                eventsRecyclerView.setLayoutManager(linearLayoutManager);
                                eventsRecyclerView.setAdapter(eventsAdapter);
                            }
                        } catch (Exception exception) {
                            DynamicToast.makeError(getContext(), "Error API on response").show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Event>> call, @NonNull Throwable t) {
                        DynamicToast.makeError(getContext(), "Error API connection").show();
                    }
                });
    }


    /**
     * Method used to set the views.
     */
    private void loadViews() {
        eventsRecyclerView = view.findViewById(R.id.recycler_view_events);
        searchEventsView = view.findViewById(R.id.search_bar_events);
        searcherSpinner = view.findViewById(R.id.searcher_spinner);
        spinner = view.findViewById(R.id.action_bar_spinner_events);
        myEvents_fab = view.findViewById(R.id.my_events);
        bestEvents_btn = view.findViewById(R.id.best_events);
        allEvents_btn = view.findViewById(R.id.all_events);
    }


    /**
     * Method used to get the best events from the API.
     */
    private void bestEventsFilter() {
        apiEvents.getEventsBest(Token.getToken(getContext()), new Callback<List<Event>>() {
            @Override
            public void onResponse(@NonNull Call<List<Event>> call, @NonNull Response<List<Event>> response) {
                eventsAdapter = new EventsAdapter(response.body(), getContext());
                eventsRecyclerView.setAdapter(eventsAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<Event>> call, @NonNull Throwable t) {
                DynamicToast.makeError(getContext(), "Error API connection").show();
            }
        });

    }


    /**
     * Method used to filter events by a specific selection.
     */
    public void getFilteredListByCategory() {
        String type = spinner.getSelectedItem().toString();
        List<Event> copyList = new ArrayList<>();
        if (type.equals(spinnerListCategories[0])) {
            apiEventsCall();
        } else {
            for (Event event : eventList) {
                if (event.getType().equals(type + "-grup7")) copyList.add(event);
            }
            eventsAdapter = new EventsAdapter(copyList, getContext());
            eventsRecyclerView.setAdapter(eventsAdapter);
        }
    }


    /**
     * Method used to get a list of all the events from the API.
     */
    private void apiEventsCall() {
        Context c = getContext();
        apiEvents.getListEvents(Token.getToken(getContext()), new Callback<List<Event>>() {
            @Override
            public void onResponse(@NonNull Call<List<Event>> call, @NonNull Response<List<Event>> response) {
                try {
                    if (response.isSuccessful()) {
                        eventList = response.body();
                        eventsAdapter = new EventsAdapter(eventList, getContext());
                        eventsRecyclerView.setLayoutManager(linearLayoutManager);
                        eventsRecyclerView.setAdapter(eventsAdapter);
                    }
                } catch (Exception exception) {
                    DynamicToast.makeError(c, "Error API on response").show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Event>> call, @NonNull Throwable t) {
                DynamicToast.makeError(c, "Error API connection").show();
            }
        });
    }
}
