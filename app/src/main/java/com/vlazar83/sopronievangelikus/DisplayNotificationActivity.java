package com.vlazar83.sopronievangelikus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_notification);

        TextView notificationTextView = findViewById(R.id.notificationTextView);

        Intent intent = getIntent();

        String title = intent.getStringExtra("TITLE");
        String body = intent.getStringExtra("BODY");

        String displayText = "Title:\\n" + title + "Body:\\n" + body;

        notificationTextView.setText(displayText);

    }
}
