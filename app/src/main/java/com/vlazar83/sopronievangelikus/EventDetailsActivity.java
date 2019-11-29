package com.vlazar83.sopronievangelikus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
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

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sopron and move the camera
        LatLng sydney = new LatLng(47.685276, 16.589422);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sopron"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }
}
