package com.androidpprog2.openevents.view.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.persistance.api.APIEvents;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.business.Token;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateEventActivity extends AppCompatActivity {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        btn_add_event = findViewById(R.id.btn_add_event);

        btn_add_event.setOnClickListener(view -> {
            checkData();
        });
    }

    private void checkData() {
        name = findViewById(R.id.ce_name);
        image = findViewById(R.id.ce_image);
        location = findViewById(R.id.ce_location);
        description = findViewById(R.id.ce_description);
        startDatePicker = findViewById(R.id.start_date);
        startDatePicker = findViewById(R.id.start_date);
        startTimePicker = findViewById(R.id.start_time);
        endDatePicker = findViewById(R.id.end_date);
        endTimePicker = findViewById(R.id.end_time);
        type = findViewById(R.id.ce_event_type);

        String startDate = getStringDate(startDatePicker, startTimePicker);
        String endDate = getStringDate(endDatePicker, endTimePicker);
        Log.d("IRIS", "date" + startDate);
        Log.d("IRIS", "date" + endDate);


        n_participators = findViewById(R.id.ce_participators);
        type = findViewById(R.id.ce_event_type);

        int num = Integer.parseInt(n_participators.getText().toString());
        Event e = new Event(name.getText().toString(), image.getText().toString(), location.getText().toString(), description.getText().toString(), startDate, endDate, num, type.getText().toString());

        APIEvents api = APIEvents.getInstance();
        api.addEvent(Token.getToken(this), e, new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                Event e = response.body();
                Log.d("IRIS", "TRUEEEEE" + response.body());
                finish();
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                Log.d("IRIS", "FALSEEE" + t.toString());
            }
        });

    }

    private String getStringDate(DatePicker datePicker, TimePicker timePicker) {
        Log.d("IRIS", "ENTRA");
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        return year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":00.000Z";
    }
}

