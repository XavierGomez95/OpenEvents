package com.androidpprog2.openevents.view.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.persistance.api.APIEvents;
import com.androidpprog2.openevents.business.AssistanceRequest;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.business.Token;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;
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
    private Context c = this;


    /**
     * Setting the essential layout parameters.
     * Manage the functions of assistance or no assistance.
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
            if (attendButton.getText().equals(R.string.Attend)) {

                APIEvents api = APIEvents.getInstance();
                api.addEventAssistance(Token.getToken(this), event.getId(), new Callback<AssistanceRequest>() {
                    @Override
                    public void onResponse(@NonNull Call<AssistanceRequest> call, @NonNull Response<AssistanceRequest> response) {
                        attendButton.setText(R.string.unattend);
                        DynamicToast.makeSuccess(c, "You will attend to the event!").show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<AssistanceRequest> call, @NonNull Throwable t) {
                        DynamicToast.makeError(c, "Error while connecting to the API").show();

                    }
                });
            } else {
                APIEvents api = APIEvents.getInstance();
                api.deleteEventAssistance(Token.getToken(this), event.getId(), new Callback<AssistanceRequest>() {
                    @Override
                    public void onResponse(@NonNull Call<AssistanceRequest> call, @NonNull Response<AssistanceRequest> response) {
                        attendButton.setText(R.string.Attend);
                        DynamicToast.make(c, "Your attendance have been canceled ").show();
                    }

                    @Override
                    public void onFailure(@NonNull Call<AssistanceRequest> call, @NonNull Throwable t) {
                        DynamicToast.makeError(c, "Error while connecting to the API").show();
                    }
                });
            }
        });
    }

    /**
     * Method called to set all description text in the event details.
     *
     * @param eventList contains a list of events.
     * @param position  contains the current position of an event.
     */
    private void setTexts(List<Event> eventList, Integer position) {
        tittleDescriptionTextView.setText(eventList.get(position).getName());
        descriptionTextView.setText(event.getDescription() + "\nparticipators: " + event.getN_participators());
        Log.d("TAG", "EVENT DATE" + event.getEventStart_date());
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
        Picasso.with(getApplicationContext()).load(imageURL).into(imageView);
    }
}
