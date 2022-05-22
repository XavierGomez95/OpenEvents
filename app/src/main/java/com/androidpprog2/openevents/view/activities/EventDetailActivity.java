package com.androidpprog2.openevents.view.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.persistance.api.APIEvents;
import com.androidpprog2.openevents.business.AssistanceRequest;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.business.Token;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * EVENT DETAIL ACTIVITY CLASS
 */
public class EventDetailActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView tittleDescriptionTextView;
    private TextView descriptionTextView;
    private TextView dateStart;
    private TextView dateEnd;
    private TextView location;
    private Button attendButton;
    private Event event;


    /**
     * Setting the essential layout parameters.
     * Manage the functions of assistance or no assistance.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        List<Event> eventList = (List<Event>) getIntent().getSerializableExtra("eventlist");
        int position = (int) getIntent().getSerializableExtra("position");

        event = eventList.get(position);

        loadViews();
        loadImg();
        setTexts(eventList, position);

        attendButton.setOnClickListener(view -> {
            if (attendButton.getText().equals("ATTEND")) {
                APIEvents api = APIEvents.getInstance();
                api.addEventAssistance(Token.getToken(this), event.getId(), new Callback<AssistanceRequest>() {
                    @Override
                    public void onResponse(Call<AssistanceRequest> call, Response<AssistanceRequest> response) {
                        attendButton.setText("UNATTEND");
                        // TODO: CAMBIAR POR OTRO AVISO
                        Log.d("IRIS", "TRUEEE CREATE" + response.body());
                    }

                    @Override
                    public void onFailure(Call<AssistanceRequest> call, Throwable t) {
                        // TODO: CAMBIAR POR OTRO AVISO
                        Log.d("IRIS", "FALSE");
                    }
                });
            } else {
                APIEvents api = APIEvents.getInstance();
                api.deleteEventAssistance(Token.getToken(this), event.getId(), new Callback<AssistanceRequest>() {
                    @Override
                    public void onResponse(Call<AssistanceRequest> call, Response<AssistanceRequest> response) {
                        attendButton.setText("ATTEND");
                        // TODO: CAMBIAR POR OTRO AVISO
                        Log.d("IRIS", "TRUEEE DELETE" + response.body());
                    }

                    @Override
                    public void onFailure(Call<AssistanceRequest> call, Throwable t) {
                        // TODO: CAMBIAR POR OTRO AVISO
                        Log.d("IRIS", "FALSE");
                    }
                });
            }
        });
    }

    /**
     * Method called to set all description text in the event details.
     *
     * @param eventList contains a list of events.
     * @param position contains the current position of an event.
     */
    private void setTexts(List<Event> eventList, Integer position) {
        tittleDescriptionTextView.setText(eventList.get(position).getName());
        descriptionTextView.setText(event.getDescription() + " participators" + event.getN_participators());
        dateStart.setText(event.getEventStart_date());
        dateEnd.setText(event.getEventEnd_date());
        location.setText(event.getLocation());
    }

    /**
     * Method called to load the views.
     */
    private void loadViews() {
        imageView = findViewById(R.id.event_description_image);
        tittleDescriptionTextView = findViewById(R.id.tittleDescriptionTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        dateStart = findViewById(R.id.dateStartTextView);
        dateEnd = findViewById(R.id.dateEndTextView);
        location = findViewById(R.id.locationTextView);
        attendButton = findViewById(R.id.btn_attend);
    }

    /**
     * Method called to load the image shown in the events details screen.
     */
    private void loadImg() {
        String imageURL, image = event.getImage();

        if (image != null) {
            if ((image.startsWith("http") || image.startsWith("https"))
                    && (image.endsWith(".jpg") || image.endsWith(".png")
                    || image.endsWith(".jpeg") || image.endsWith(".JPG")
                    || image.endsWith(".PNG") || image.endsWith(".JPEG")))
                imageURL = image;
            else
                imageURL = "https://t4.ftcdn.net/jpg/04/70/29/97/360_F_470299797_UD0eoVMMSUbHCcNJCdv2t8B2g1GVqYgs.jpg";
        } else
            imageURL = "https://t4.ftcdn.net/jpg/04/70/29/97/360_F_470299797_UD0eoVMMSUbHCcNJCdv2t8B2g1GVqYgs.jpg";

        // TODO: ELIMINAR CUANDO TODO FUNCIONE
        Log.d("EVENT NAME : ", event.getName());
        Log.d("URL : ", image);

        Picasso.with(getApplicationContext()).load(imageURL).into(imageView);
    }
}
