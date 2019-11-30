package com.vlazar83.sopronievangelikus;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
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
    private static Spinner staticSpinner;
    public static final String LOG_TAG_FOR_DB_WRITE = "writeToFirestoreDB";
    private static TextView selectedDateTextView, selectedTimeTextView;
    private static EditText nameEditTextView, fullNameEditTextView, eventTypeEditTextView, pastorNameEditTextView, commentEditTextView;
    private static CheckBox withCommunionCheckBox;
    private HashMap<String, Object> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_event);
        this.selectedDateTextView = findViewById(R.id.create_event_choosen_date_text_view);
        this.selectedTimeTextView = findViewById(R.id.create_event_choosen_time_text_view);

        this.nameEditTextView = findViewById(R.id.event_name);
        this.fullNameEditTextView = findViewById(R.id.event_full_name);
        this.eventTypeEditTextView = findViewById(R.id.event_type);
        this.pastorNameEditTextView = findViewById(R.id.event_pastor_name);
        this.commentEditTextView = findViewById(R.id.event_comments);
        this.withCommunionCheckBox = findViewById(R.id.event_with_communion);

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

        staticSpinner = (Spinner) findViewById(R.id.event_location_dropdown_list);
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter.createFromResource(this, R.array.locations, android.R.layout.simple_spinner_item);

        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);

        Button createEventButton = findViewById(R.id.create_event_send_button);
        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                data = new HashMap<>();

                data.put("name", CreateEventActivity.nameEditTextView.getText().toString());
                data.put("fullName", CreateEventActivity.fullNameEditTextView.getText().toString());
                data.put("comments", CreateEventActivity.commentEditTextView.getText().toString());
                data.put("pastorName", CreateEventActivity.pastorNameEditTextView.getText().toString());
                data.put("typeOfEvent", CreateEventActivity.eventTypeEditTextView.getText().toString());
                data.put("withCommunion", CreateEventActivity.withCommunionCheckBox.isChecked());
                String date = CreateEventActivity.selectedDateTextView.getText().toString();
                String time = CreateEventActivity.selectedTimeTextView.getText().toString();
                data.put("eventDateAndTime", Utils.convertTimeDetailsInStringsToTimestamp(date, time));

                if(staticSpinner.getSelectedItem().toString().equals("Church")){
                    data.put("location", churchLocation);
                } else if(staticSpinner.getSelectedItem().toString().equals("Congregation House")) {
                    data.put("location", congregationHouseLocation);
                } else {
                    data.put("location", null);
                }

                sendFirebaseEvent(data);

            }
        });

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

    public static class DateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

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

    public static class TimeDialogFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

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

    public static void showSetDate(int year,int month,int day) {
        month++;
        selectedDateTextView.setText(year+"/"+month+"/"+day);
    }

    public static void showSetTime(int hourOfDay, int minute) {
        String.format("%2d:%2d", hourOfDay, minute);
        selectedTimeTextView.setText(String.format("%02d:%02d", hourOfDay, minute));
    }

}


