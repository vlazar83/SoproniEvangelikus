package com.vlazar83.sopronievangelikus;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class CreateEventActivity extends AppCompatActivity implements FirebaseEventInterface{

    private int year, month, day;
    private DatePicker datePicker;
    private static TextView selectedDateTextView, selectedTimeTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_event);
        this.selectedDateTextView = findViewById(R.id.create_event_choosen_date_text_view);
        this.selectedTimeTextView = findViewById(R.id.create_event_choosen_time_text_view);

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

    }



    @Override
    public void sendFirebaseEvent(FirebaseEvent firebaseEvent) {

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


