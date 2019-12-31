package com.vlazar83.sopronievangelikus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class EventDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Event eventObject;
    private GoogleMap mMap;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";

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

        TextView eventTypeTextView = findViewById(R.id.event_type);
        eventTypeTextView.setText(eventObject.getTypeOfEvent());

        TextView pastorNameTextView = findViewById(R.id.event_pastor_name);
        pastorNameTextView.setText(eventObject.getPastorName());

        TextView languageTextView = findViewById(R.id.event_language);
        languageTextView.setText(eventObject.getLanguage());

        CheckBox withCommunionCheckBox = findViewById(R.id.event_with_communion);
        withCommunionCheckBox.setChecked(eventObject.getWithCommunion());

        TextView commentsTextView = findViewById(R.id.event_comments);
        commentsTextView.setText(eventObject.getComments());

        long eventDateAndTimeSeconds = eventObject.getEventDateAndTimeSeconds();
        int eventDateAndTimeNanoSeconds = eventObject.getEventDateAndTimeNanoSeconds();

        TextView eventDateAndTimeTextView = findViewById(R.id.event_date_and_time);
        eventDateAndTimeTextView.setText(Utils.convertTimeDetailsToTimestamp(eventDateAndTimeSeconds, eventDateAndTimeNanoSeconds));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sopron and move the camera
        LatLng eventLocation = new LatLng(eventObject.getLatitude(), eventObject.getLongitude());
        mMap.addMarker(new MarkerOptions().position(eventLocation).title("Event Location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(eventLocation, 18));


    }
}
