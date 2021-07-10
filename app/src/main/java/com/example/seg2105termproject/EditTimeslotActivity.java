package com.example.seg2105termproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * This file is part of Course Booking application for Android devices
 *
 * app/src/main/java/com/example/seg2105termproject
 *
 * University of Ottawa - Faculty of Engineering - SEG2105 -Course Booking application for Android devices
 * @author      Sally R       <sraad062@uottawa.ca>
 *              Jerry S       <jsoon029@uottawa.ca>
 *              Glen W        <@uottawa.ca>
 *              Youssef J     <yjall032@uottawa.ca>
 *
 */
public class EditTimeslotActivity extends AppCompatActivity {

    Spinner spinnerDaySelect;
    TimePicker tpStart, tpEnd;

    String courseCode;
    int position;

    Course course;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_timeslot);

        spinnerDaySelect = findViewById(R.id.spinnerDaySelect);
        tpStart = findViewById(R.id.tpStart);
        tpEnd = findViewById(R.id.tpEnd);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Grab the intent package.
        Intent intent = getIntent();
        courseCode = intent.getStringExtra(InstructorActivity.SELECTED_COURSE);
        position = intent.getIntExtra(TimetableActivity.TIMESLOT_POSITION, -1);

        course = dbHelper.getCourse(courseCode);

        // Failsafe, but not suppose to happen.
        if (position == -1){
            Utils.createErrorDialog(this, R.string.timeslot_not_found);
            finish();
        }

        // Set the spinner items.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_of_week, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDaySelect.setAdapter(adapter);

        // Set the selected item.
        String strOldDay = course.getDays()[position].toString();
        int spinnerPosition = adapter.getPosition(strOldDay.charAt(0) + strOldDay.substring(1).toLowerCase());
        spinnerDaySelect.setSelection(spinnerPosition);

        // Set selected times.
        LocalTime oldStartTime = course.getStartTimes()[position];
        LocalTime oldEndTime = course.getEndTimes()[position];

        tpStart.setHour(oldStartTime.getHour());
        tpStart.setMinute(oldStartTime.getMinute());

        tpEnd.setHour(oldEndTime.getHour());
        tpEnd.setMinute(oldEndTime.getMinute());
    }

    /**
     * Method for the onClick of btnSaveTimeslot.
     * @param view  The view that calls this method.
     */
    public void saveTimeslot(View view){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        if (LocalTime.of(tpStart.getHour(), tpStart.getMinute())
                .compareTo(LocalTime.of(tpEnd.getHour(), tpEnd.getMinute())) >= 0) {
            Utils.createErrorDialog(this, R.string.timeslot_end_start_time);
        }
        else {
            dbHelper.changeCourseTime(courseCode, position,
                    DayOfWeek.valueOf(spinnerDaySelect.getSelectedItem().toString().toUpperCase()),
                    LocalTime.of(tpStart.getHour(), tpStart.getMinute()),
                    LocalTime.of(tpEnd.getHour(), tpEnd.getMinute()));
            TimetableActivity.self.update();
            finish();
        }
    }

    /**
     * Method for the onClick of btnDeleteTimeslot.
     * @param view  The view that calls this method.
     */
    public void deleteTimeslot(View view){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.deleteCourseTime(courseCode, position);
        TimetableActivity.self.update();
        finish();
    }
}
