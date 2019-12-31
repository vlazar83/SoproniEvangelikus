package com.vlazar83.sopronievangelikus;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SendNotificationActivity extends AppCompatActivity {

    private Spinner targetSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_send_notification);

        // setup the Spinner in the UI
        targetSpinner = (Spinner) findViewById(R.id.send_notification_dropdown_list);
        ArrayAdapter<CharSequence> locationAdapter = ArrayAdapter.createFromResource(this, R.array.notificationTargets, android.R.layout.simple_spinner_item);
        targetSpinner.setAdapter(locationAdapter);

        Button sendNotificationButton = (Button) findViewById(R.id.send_notification_button);
        sendNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

    }
}
