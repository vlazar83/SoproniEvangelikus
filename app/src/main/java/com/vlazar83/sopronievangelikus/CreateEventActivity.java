package com.vlazar83.sopronievangelikus;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateEventActivity extends AppCompatActivity implements FirebaseEventInterface{

    private final GeoPoint churchLocation = new GeoPoint(47.685276, 16.589422);
    private final GeoPoint congregationHouseLocation = new GeoPoint(47.685263, 16.588625);
    private Spinner locationSpinner, languageSpinner;
    public static final String LOG_TAG_FOR_DB_WRITE = "writeToFirestoreDB";
    private TextView selectedDateTextView, selectedTimeTextView;
    private EditText nameEditTextView, fullNameEditTextView, eventTypeEditTextView, pastorNameEditTextView, commentEditTextView;
    private CheckBox withCommunionCheckBox;
    private ImageView imagePickerView;
    private static String nameOfImage;
    private HashMap<String, Object> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_event);
        this.selectedDateTextView = findViewById(R.id.create_event_choosen_date_text_view);
        this.selectedTimeTextView = findViewById(R.id.create_event_choosen_time_text_view);

        this.nameEditTextView = findViewById(R.id.event_time);
        this.fullNameEditTextView = findViewById(R.id.event_full_name);
        this.eventTypeEditTextView = findViewById(R.id.event_type);
        this.pastorNameEditTextView = findViewById(R.id.event_pastor_name);
        this.commentEditTextView = findViewById(R.id.event_comments);
        this.withCommunionCheckBox = findViewById(R.id.event_with_communion);

        imagePickerView = (ImageView)findViewById(R.id.imagePickerView);

        imagePickerView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent imagePickerIntent = new Intent(CreateEventActivity.this, ImagePickerActiviy.class);
                // Start the new activity
                startActivity(imagePickerIntent);
            }
        });


        Bundle extras = getIntent().getExtras();
        if(extras != null){
            nameOfImage = extras.getString("choosenImage");
            imagePickerView.setImageURI(null);
            imagePickerView.setImageURI(Utils.createUriForImages(nameOfImage));
        } else {
            imagePickerView.setImageURI(null);
            imagePickerView.setImageURI(Utils.createUriForImages("/drawable/bible_free_icon"));
        }

        Button chooseDateButton = findViewById(R.id.create_event_choose_date_button);
        chooseDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateDialogFragment datePicker=new DateDialogFragment();
                datePicker.show(getSupportFragmentManager(), "showDate");

            }
        });

        Button chooseTimeButton = findViewById(R.id.create_event_choose_time_button);
        chooseTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TimeDialogFragment timePicker=new TimeDialogFragment();
                timePicker.show(getSupportFragmentManager(), "showTime");

            }
        });

        // setup the Spinners in the UI
        locationSpinner = (Spinner) findViewById(R.id.send_notification_dropdown_list);
        ArrayAdapter<CharSequence> locationAdapter = ArrayAdapter.createFromResource(this, R.array.locations, android.R.layout.simple_spinner_item);
        locationSpinner.setAdapter(locationAdapter);

        languageSpinner = (Spinner) findViewById(R.id.event_language_dropdown_list);
        ArrayAdapter<CharSequence> languageAdapter = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        languageSpinner.setAdapter(languageAdapter);

        Button createEventButton = findViewById(R.id.create_event_send_button);
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if date and time is not filled, then do not trigger the event creation
                String date = selectedDateTextView.getText().toString();
                String time = selectedTimeTextView.getText().toString();

                if (!(date.isEmpty() || time.isEmpty())){
                    data = new HashMap<>();

                    data.put("name", nameEditTextView.getText().toString());
                    data.put("fullName", fullNameEditTextView.getText().toString());
                    data.put("comments", commentEditTextView.getText().toString());
                    data.put("pastorName", pastorNameEditTextView.getText().toString());
                    data.put("typeOfEvent", eventTypeEditTextView.getText().toString());
                    data.put("eventImage", nameOfImage);
                    data.put("withCommunion", withCommunionCheckBox.isChecked());
                    data.put("eventDateAndTime", Utils.convertTimeDetailsInStringsToTimestamp(date, time));

                    if(locationSpinner.getSelectedItem().toString().equals("Church")){
                        data.put("location", churchLocation);
                    } else if(locationSpinner.getSelectedItem().toString().equals("Congregation House")) {
                        data.put("location", congregationHouseLocation);
                    } else {
                        data.put("location", null);
                    }

                    if(languageSpinner.getSelectedItem().toString().equals("Hungarian")){
                        data.put("language", "Hungarian");
                    } else if(languageSpinner.getSelectedItem().toString().equals("German")) {
                        data.put("language", "German");
                    } else {
                        data.put("language", null);
                    }

                    sendFirebaseEvent(data);

                    // clear the SharedPref data
                    SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear().apply();

                    // clear the UI
                    clearUI();

                } else {
                    Toast.makeText(CreateEventActivity.this, "Fill all fields first", Toast.LENGTH_LONG).show();
                }



            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

        // Store values between instances here
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();  // Put the values from the UI

        editor.putString("name", nameEditTextView.getText().toString());
        editor.putString("fullName", fullNameEditTextView.getText().toString());
        editor.putString("comments", commentEditTextView.getText().toString());
        editor.putString("pastorName", pastorNameEditTextView.getText().toString());
        editor.putString("typeOfEvent", eventTypeEditTextView.getText().toString());
        editor.putString("eventImage", nameOfImage);
        editor.putBoolean("withCommunion", withCommunionCheckBox.isChecked());
        editor.putString("date", selectedDateTextView.getText().toString());
        editor.putString("time", selectedTimeTextView.getText().toString());
        if(locationSpinner.getSelectedItem().toString().equals("Church")){
            editor.putString("location", "Church");
        } else if(locationSpinner.getSelectedItem().toString().equals("Congregation House")) {
            editor.putString("location", "Congregation House");
        } else {
            editor.putString("location", null);
        }

        if(languageSpinner.getSelectedItem().toString().equals("Hungarian")){
            editor.putString("language", "Hungarian");
        } else if(languageSpinner.getSelectedItem().toString().equals("German")) {
            editor.putString("language", "German");
        } else {
            editor.putString("language", null);
        }
        // Commit to storage
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        nameEditTextView.setText(preferences.getString("name",""));
        fullNameEditTextView.setText(preferences.getString("fullName",""));
        commentEditTextView.setText(preferences.getString("comments",""));
        pastorNameEditTextView.setText(preferences.getString("pastorName",""));
        eventTypeEditTextView.setText(preferences.getString("typeOfEvent",""));
        selectedDateTextView.setText(preferences.getString("date",""));
        selectedTimeTextView.setText(preferences.getString("time",""));

        if(preferences.getString("location", "Church").equals("Church")){
            locationSpinner.setSelection(0);
        } else if(preferences.getString("location", "Church").equals("Congregation House")) {
            locationSpinner.setSelection(1);
        } else {
            locationSpinner.setSelection(0);
        }

        if(preferences.getString("language", "Hungarian").equals("Hungarian")){
            languageSpinner.setSelection(0);
        } else if(preferences.getString("language", "Hungarian").equals("German")) {
            languageSpinner.setSelection(1);
        } else {
            languageSpinner.setSelection(0);
        }

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            nameOfImage = extras.getString("choosenImage");
            imagePickerView.setImageURI(null);
            imagePickerView.setImageURI(Utils.createUriForImages(nameOfImage));
        } else {
            imagePickerView.setImageURI(null);
            imagePickerView.setImageURI(Utils.createUriForImages(preferences.getString("eventImage", "/drawable/bible_free_icon")));
        }


        if(preferences.getBoolean("withCommunion", true) == true){
            withCommunionCheckBox.setChecked(true);
        } else {
            withCommunionCheckBox.setChecked(false);
        }

    }

    @Override
    public void onBackPressed()
    {
        Intent backToHome = new Intent(CreateEventActivity.this, MainActivity.class);
        // Start the new activity
        startActivity(backToHome);
    }

    @Override
    public void sendFirebaseEvent(Map<String, Object> firebaseEvent) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Add a new document with a generated ID
        db.collection("events")
                .add(firebaseEvent)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(LOG_TAG_FOR_DB_WRITE, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(CreateEventActivity.this, "Event successfully created", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(LOG_TAG_FOR_DB_WRITE, "Error adding document", e);
                        Toast.makeText(CreateEventActivity.this, "Event creation failed", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public class DateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        public DateDialogFragment()
        {
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar cal=Calendar.getInstance();
            int year=cal.get(Calendar.YEAR);
            int month=cal.get(Calendar.MONTH);
            int day=cal.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            showSetDate(year,monthOfYear,dayOfMonth);
        }

    }

    public class TimeDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

        public TimeDialogFragment()
        {
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int hourOfDay, minute;
            boolean is24HourView;

            TimePicker timePicker = new TimePicker(getActivity());
            hourOfDay = timePicker.getHour();
            minute = timePicker.getMinute();
            is24HourView = true;
            return new TimePickerDialog(getActivity(), this, hourOfDay, minute, is24HourView);
        }


        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            showSetTime(hourOfDay, minute);
        }
    }

    public void showSetDate(int year,int month,int day) {
        month++;
        selectedDateTextView.setText(year+"/"+month+"/"+day);
    }

    public void showSetTime(int hourOfDay, int minute) {
        String.format("%2d:%2d", hourOfDay, minute);
        selectedTimeTextView.setText(String.format("%02d:%02d", hourOfDay, minute));
    }

    public void clearUI() {
        nameEditTextView.setText("");
        fullNameEditTextView.setText("");
        commentEditTextView.setText("");
        pastorNameEditTextView.setText("");
        eventTypeEditTextView.setText("");
        imagePickerView.setImageURI(null);
        imagePickerView.setImageURI(Utils.createUriForImages("/drawable/bible_free_icon"));
        withCommunionCheckBox.setChecked(false);
        selectedDateTextView.setText("");
        selectedTimeTextView.setText("");
    }

}


