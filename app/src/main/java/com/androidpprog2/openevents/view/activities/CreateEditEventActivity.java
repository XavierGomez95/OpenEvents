package com.androidpprog2.openevents.view.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.persistance.api.APIEvents;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.business.Token;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * CREATED EVENT ACTIVITY CLASS
 */
public class CreateEditEventActivity extends AppCompatActivity {
    private Button btn_add_event;
    private EditText name;
    private EditText image;
    private EditText location;
    private EditText description;
    private DatePicker startDatePicker;
    private TimePicker startTimePicker;
    private DatePicker endDatePicker;
    private TimePicker endTimePicker;
    private EditText n_participators;
    private EditText type;
    private Event editEvent;
    private boolean edit = false;


    /**
     * Setting the essential layout parameters.
     * Load views and manage add event functions.
     *
     * @param savedInstanceState reference to a Bundle object that is passed into the onCreate.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        loadViews();
        Intent intent = getIntent();
        editEvent = (Event) intent.getSerializableExtra("event");
        if (editEvent != null) {
            edit = true;
            setEventText();
        }
        btn_add_event.setOnClickListener(view -> {
            if (edit) {
                editEventApi();
            } else {
                addEventApi();
            }

        });
    }

    /**
     * se text when you want to edit an event
     */
    private void setEventText() {
        name.setText(editEvent.getName());
        image.setText(editEvent.getImage());
        location.setText(editEvent.getLocation());
        description.setText(editEvent.getDescription());
        n_participators.setText(editEvent.getN_participators().toString());
        type.setText(editEvent.getType());
        Log.d("TEST", "DATE" + editEvent.getEventStart_date());
        String startDate = editEvent.getEventStart_date().split("T")[0];
        String[] ymd = startDate.split("-");
        Log.d("TEST", "DATE" + startDate);
        startDatePicker.updateDate(Integer.parseInt(ymd[0]), Integer.parseInt(ymd[1]) - 1, Integer.parseInt(ymd[2]));
//TODO ACABAR DE PONER LA ENDDATE DEL EVENT Y LAS HORAS Y TODO
    }

    /**
     * Method called to load all views.
     */
    private void loadViews() {
        name = findViewById(R.id.ce_name);
        image = findViewById(R.id.ce_image);
        location = findViewById(R.id.ce_location);
        description = findViewById(R.id.ce_description);
        startDatePicker = findViewById(R.id.start_date);
        startTimePicker = findViewById(R.id.start_time);
        endDatePicker = findViewById(R.id.end_date);
        endTimePicker = findViewById(R.id.end_time);
        type = findViewById(R.id.ce_event_type);
        btn_add_event = findViewById(R.id.btn_add_event);
        n_participators = findViewById(R.id.ce_participators);
        if (edit) btn_add_event.setText(R.string.edit_event);
    }

    /**
     * Method called to check the entrances of information and to add the event to the API.
     */
    private void addEventApi() {
        Context context = this;
        String startDate = getStringDate(startDatePicker, startTimePicker);
        String endDate = getStringDate(endDatePicker, endTimePicker);
        int num = Integer.parseInt(n_participators.getText().toString());
        // TODO: ELIMINAR CUANDO FUNCIONE TODO
        Log.d("IRIS", "date: " + startDate);


        Event e = new Event(name.getText().toString(), image.getText().toString(),
                location.getText().toString(), description.getText().toString(),
                startDate, endDate, num, type.getText().toString());

        APIEvents api = APIEvents.getInstance();
        api.addEvent(Token.getToken(this), e, new Callback<Event>() {
            @Override
            public void onResponse(@NonNull Call<Event> call, @NonNull Response<Event> response) {
                if (response.body() != null) {
                    DynamicToast.makeSuccess(context, "Event created successfully!").show();
                    finish();
                } else {
                    DynamicToast.makeError(context, "Error in input").show();

                }

            }

            @Override
            public void onFailure(@NonNull Call<Event> call, @NonNull Throwable t) {
                DynamicToast.makeError(context, "Error while connecting to the API").show();
            }
        });
    }

    /**
     * Method called to check the entrances of information and to add the event to the API.
     */
    private void editEventApi() {
        Log.d("EDIT", "EVENT");
        Context context = this;
        String startDate = getStringDate(startDatePicker, startTimePicker);
        String endDate = getStringDate(endDatePicker, endTimePicker);
        int num = Integer.parseInt(n_participators.getText().toString());

        Event e = new Event(name.getText().toString(), image.getText().toString(),
                location.getText().toString(), description.getText().toString(),
                startDate, endDate, num, type.getText().toString());
        APIEvents api = APIEvents.getInstance();
        Log.d("ID", "ID: " + editEvent.getId());
        api.editEvent(Token.getToken(this), editEvent.getId(), e, new Callback<Event>() {
            @Override
            public void onResponse(@NonNull Call<Event> call, @NonNull Response<Event> response) {
                if (response.body() != null) {
                    Event eventtt = response.body();
                    DynamicToast.makeSuccess(context, "Event edited successfully!").show();
                    finish();
                } else {
                    DynamicToast.makeError(context, "Error in input").show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<Event> call, @NonNull Throwable t) {
                DynamicToast.makeError(context, "Error while connecting to the API").show();

            }
        });

    }

    /**
     * Method called to convert the given date format to the required String type.
     *
     * @param datePicker contains the date in DatePicker format.
     * @param timePicker contains the date in TimePicker format.
     * @return a date String needed for the API.
     */
    private String getStringDate(DatePicker datePicker, TimePicker timePicker) {
        String day = String.valueOf(datePicker.getDayOfMonth());
        String month = String.valueOf(datePicker.getMonth() + 1);
        String year = String.valueOf(datePicker.getYear());
        String hour = String.valueOf(timePicker.getHour());
        String minute = String.valueOf(timePicker.getMinute());
        if (day.length() == 1) {
            day = "0" + day;
        }
        if (month.length() == 1) {
            month = "0" + month;
        }

        return year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":00.000Z";
    }
}

