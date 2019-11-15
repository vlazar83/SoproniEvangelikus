package com.vlazar83.sopronievangelikus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EventDetailsActivity extends AppCompatActivity {

    private Event eventObject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_details);

        Intent intent = getIntent();

        eventObject = (Event) intent.getSerializableExtra(MainActivity.INTENT_EVENT_DETAILS);

        TextView nameTextView = findViewById(R.id.event_name);
        nameTextView.setText(eventObject.getName());

        TextView fullNameTextView = findViewById(R.id.event_full_name);
        fullNameTextView.setText(eventObject.getFullName());

    }
}
