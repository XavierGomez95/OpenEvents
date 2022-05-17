package com.androidpprog2.openevents.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.api.APIEvents;
import com.androidpprog2.openevents.api.APIUser;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.business.Token;
import com.androidpprog2.openevents.business.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateEventActivity extends AppCompatActivity {
    private Button btn_add_event;
    private EditText name;
    private EditText image;
    private EditText location;
    private EditText description;
    private EditText eventStart_date;
    private EditText eventEnd_date;
    private EditText n_participators;
    private EditText type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        btn_add_event = findViewById(R.id.btn_add_event);

        btn_add_event.setOnClickListener(view -> {
            Log.d("IRIS", "TRUEEEEE");
            checkData();
        });
    }

    private void checkData() {
        name = findViewById(R.id.ce_name);
        image = findViewById(R.id.ce_image);
        location = findViewById(R.id.ce_location);
        description=findViewById(R.id.ce_description);
        eventStart_date = findViewById(R.id.ce_startDate);
        eventEnd_date = findViewById(R.id.ce_endDate);
        n_participators = findViewById(R.id.ce_participators);
        type = findViewById(R.id.ce_event_type);

        int num=Integer.parseInt(n_participators.getText().toString());
        Event e = new Event(name.getText().toString(), image.getText().toString(), location.getText().toString(), description.getText().toString(), "2022-01-20T12:00:00.000Z", "2022-01-20T15:00:00.000Z", 60, "Education");

        APIEvents api = APIEvents.getInstance();
        api.addEvent(Token.getToken(this),e, new Callback<Event>() {
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
}

