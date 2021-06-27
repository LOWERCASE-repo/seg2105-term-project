package com.example.seg2105termproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
public class TimetableActivity extends AppCompatActivity {

    public static final String TIMESLOT_POSITION = "com.example.seg2105termproject.TIMESLOT_POSITION";

    static TimetableActivity self;
    Course course;

    TextView tvCourseName;
    RecyclerView timeslotsView;
    TimeslotsViewAdapter tAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        self = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        tvCourseName = findViewById(R.id.tvCourseName);
        timeslotsView = findViewById(R.id.courseTimeslotsView);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        course = dbHelper.getCourse(intent.getStringExtra(InstructorActivity.SELECTED_COURSE));

        tvCourseName.setText(String.format("%s — %s", course.getName(), course.getCode()));

        tAdapter = new TimeslotsViewAdapter(
                timeslotsView,
                course);
        LinearLayoutManager timeslotLayoutManager = new LinearLayoutManager(this);
        timeslotsView.setLayoutManager(timeslotLayoutManager);
        timeslotsView.setAdapter(tAdapter);
    }

    /**
     * Method for the onClick of btnAddTimeslot.
     * @param view  The view that calls this method.
     */
    public void addTimeslot(View view){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.addCourseTime(course.getCode(),
                DayOfWeek.MONDAY,
                LocalTime.of(8,30),
                LocalTime.of(10, 0));
        update();
    }

    /**
     * Update the activity display.
     */
    public void update(){
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        tAdapter.refresh(dbHelper.getCourse(course.getCode()));
    }
}
