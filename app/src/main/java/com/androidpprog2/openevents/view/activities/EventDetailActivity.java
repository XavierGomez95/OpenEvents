package com.androidpprog2.openevents.view.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.Event;

import java.util.List;

public class EventDetailActivity extends AppCompatActivity {
    private TextView tittleDescriptionTextView;
    private TextView descriptionTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        List<Event> eventList = (List<Event>) getIntent().getSerializableExtra("eventlist");
        int position = (int) getIntent().getSerializableExtra("position");

        tittleDescriptionTextView = findViewById(R.id.tittleDescriptionTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);

        tittleDescriptionTextView.setText(eventList.get(position).getName());
        descriptionTextView.setText(eventList.get(position).getDescription());
    }
}
