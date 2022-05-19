package com.androidpprog2.openevents.view.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.api.APIEvents;
import com.androidpprog2.openevents.business.AssistanceRequest;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.business.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailActivity extends AppCompatActivity {
    private TextView tittleDescriptionTextView;
    private TextView descriptionTextView;
    private TextView dateStart;
    private TextView dateEnd;
    private TextView location;
    private Button attendButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        List<Event> eventList = (List<Event>) getIntent().getSerializableExtra("eventlist");
        int position = (int) getIntent().getSerializableExtra("position");
        Event event = eventList.get(position);
        tittleDescriptionTextView = findViewById(R.id.tittleDescriptionTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        dateStart = findViewById(R.id.dateStartTextView);
        dateEnd = findViewById(R.id.dateEndTextView);
        location = findViewById(R.id.locationTextView);
        attendButton = findViewById(R.id.btn_attend);


        tittleDescriptionTextView.setText(eventList.get(position).getName());
        descriptionTextView.setText(event.getDescription() + " participators" + event.getN_participators());
        dateStart.setText(event.getEventStart_date());
        dateEnd.setText(event.getEventEnd_date());
        location.setText(event.getLocation());
        attendButton.setOnClickListener(view -> {
            if (attendButton.getText().equals("ATTEND")) {
                APIEvents api = APIEvents.getInstance();
                api.addEventAssistance(Token.getToken(this), event.getId(), new Callback<AssistanceRequest>() {
                    @Override
                    public void onResponse(Call<AssistanceRequest> call, Response<AssistanceRequest> response) {
                        attendButton.setText("UNATTEND");
                        Log.d("IRIS", "TRUEEE CREATE" + response.body());
                    }

                    @Override
                    public void onFailure(Call<AssistanceRequest> call, Throwable t) {
                        Log.d("IRIS", "FALSE");

                    }
                });

            } else {
                APIEvents api = APIEvents.getInstance();
                api.deleteEventAssistance(Token.getToken(this), event.getId(), new Callback<AssistanceRequest>() {
                    @Override
                    public void onResponse(Call<AssistanceRequest> call, Response<AssistanceRequest> response) {
                        attendButton.setText("ATTEND");
                        Log.d("IRIS", "TRUEEE DELETE" + response.body());
                    }

                    @Override
                    public void onFailure(Call<AssistanceRequest> call, Throwable t) {
                        Log.d("IRIS", "FALSE");

                    }
                });


            }

        });

    }
}
