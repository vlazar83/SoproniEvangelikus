package com.vlazar83.sopronievangelikus;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class SettingsActivity extends AppCompatActivity {

    private static final String LOG_TAG = SettingsActivity.class.getName();
    private Switch switchForFamilySubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchForFamilySubscription = findViewById(R.id.switchForFamilySubscription);
        Button saveSettingsButton = findViewById(R.id.saveSettingsButton);

        saveSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(switchForFamilySubscription.isChecked()){

                    FirebaseMessaging.getInstance().subscribeToTopic("familyEventNotifications")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    String msg = "Subscribed to familyEventNotifications";
                                    if (!task.isSuccessful()) {
                                        msg = "Subscription failed";
                                    }
                                    Log.d(LOG_TAG, msg);
                                    Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    storeSettings();
                                }
                            });

                } else if (!switchForFamilySubscription.isChecked()){

                    FirebaseMessaging.getInstance().unsubscribeFromTopic("familyEventNotifications")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    String msg = "UnSubscribed from familyEventNotifications";
                                    if (!task.isSuccessful()) {
                                        msg = "UnSubscription failed";
                                    }
                                    Log.d(LOG_TAG, msg);
                                    Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT).show();
                                    storeSettings();
                                }
                            });
                }

                // Always subscribe to All topic
                FirebaseMessaging.getInstance().subscribeToTopic("All")
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                String msg = "Subscribed to All topic";
                                if (!task.isSuccessful()) {
                                    msg = "Subscription to All topic failed";
                                }
                                Log.d(LOG_TAG, msg);
                                // Toast.makeText(SettingsActivity.this, msg, Toast.LENGTH_SHORT).show();
                                // storeSettings();
                            }
                        });

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        if(preferences.getString("subscriptionToFamilyEventNotifications", "").equals("true")){
            switchForFamilySubscription.setChecked(true);
        } else {
            switchForFamilySubscription.setChecked(false);
        }
    }

    private void storeSettings(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();  // Put the values from the UI
        editor.putString("subscriptionToFamilyEventNotifications", String.valueOf(switchForFamilySubscription.isChecked()));
        editor.commit();
    }

}
