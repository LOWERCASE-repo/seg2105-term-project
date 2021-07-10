package com.example.seg2105termproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.DayOfWeek;

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
public class StudentActivity extends AppCompatActivity {

    static StudentActivity self;

    TextView tvStudentName, tvChosenCourse;
    Button btnSearchCode, btnSearchName, btnSearchDay, btnToggleEnroll;
    Student student;
    Course course;

    RecyclerView enrolledCoursesView;
    CoursesViewAdapter cAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        self = this;

        tvStudentName = findViewById(R.id.tvStudentName);
        tvChosenCourse = findViewById(R.id.tvChosenCourse);
        btnSearchCode = findViewById(R.id.btnSearchCode);
        btnSearchName = findViewById(R.id.btnSearchName);
        btnSearchDay = findViewById(R.id.btnSearchDay);
        btnToggleEnroll = findViewById(R.id.btnToggleEnroll);

        enrolledCoursesView = findViewById(R.id.enrolledCoursesView);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        student = (Student) dbHelper.getUser(intent.getStringExtra(MainActivity.EXTRA_USER));

        cAdapter = new CoursesViewAdapter(dbHelper.getEnrolledCourses(student.getUsername()));
        LinearLayoutManager coursesLayoutManager = new LinearLayoutManager(this);
        enrolledCoursesView.setLayoutManager(coursesLayoutManager);
        enrolledCoursesView.setAdapter(cAdapter);

        tvStudentName.setText("Welcome " + student.getUsername());
    }

    /**
     * Update the views on the activity.
     * @param course    The course selected by the instructor (user).
     */
    private void update(Course course) {
        this.course = course;
        tvChosenCourse.setText(String.format("%s — %s", course.getCode(), course.getName()));

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        cAdapter.refresh(dbHelper.getEnrolledCourses(student.getUsername()));
    }

    /**
     * Method for the onClick of btnFindByName.
     * @param view  The view that calls this method.
     */
    public void findCourseByName(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Find Course By Name");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText name = new EditText(this);
        name.setHint("Name");
        name.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        layout.addView(name);
        builder.setView(layout);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String courseName = name.getText().toString();
                if (courseName.trim().equals("")) {
                    Utils.createErrorDialog(self, R.string.parameters_missing);
                    return;
                }
                DatabaseHelper dbHelper = new DatabaseHelper(self);
                try {
                    update(dbHelper.getCourseFromName(courseName));
                } catch (IllegalArgumentException e) {
                    Utils.createErrorDialog(self, R.string.course_not_found);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    /**
     * Method for the onClick of btnFindByCode.
     * @param view  The view that calls this method.
     */
    public void findCourseByCode(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Find Course By Code");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText code = new EditText(this);
        code.setHint("Code");
        code.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        layout.addView(code);
        builder.setView(layout);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String courseCode = code.getText().toString();
                if (courseCode.trim().equals("")) {
                    Utils.createErrorDialog(self, R.string.parameters_missing);
                    return;
                }
                DatabaseHelper dbHelper = new DatabaseHelper(self);
                try {
                    update(dbHelper.getCourse(courseCode));
                } catch (IllegalArgumentException e) {
                    Utils.createErrorDialog(self, R.string.course_not_found);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    /**
     * Method for onClick of btnFindByDay.
     * @param view  The view that calls this method.
     */
    public void findCourseByDay(View view){
        // Picking the day.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final int[] selectedIndex = {-1};

        builder.setTitle("Find Course By Day | Pick day");

        // Set the radio button click method.
        builder.setSingleChoiceItems(R.array.days_of_week, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Set the index.
                selectedIndex[0] = which;
            }
        });

        // Set the "confirm" button.
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pickCourse(DayOfWeek.of(selectedIndex[0] + 1));
            }
        });

        // Set the "cancel" button.
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { dialog.cancel(); }
        });

        builder.show();
    }

    /**
     * Second part to {@link #findCourseByDay(View)}.
     * Only called when a day is chosen.
     * @param day   The day of the week to search courses by.
     * @see #findCourseByDay(View)
     */
    private void pickCourse(DayOfWeek day){
        // Picking the course.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Find Course By Day | Pick course");

        // Constant integer array to hold selected value.
        final int[] selectedIndex = {-1};
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        Course[] coursesOnDay;
        try {
            coursesOnDay = dbHelper.getCoursesFromDay(day);
        } catch (IllegalArgumentException e){
            Utils.createErrorDialog(this, R.string.no_courses_on_day);
            return;
        }

        String[] strCourses = new String[coursesOnDay.length];

        for (int i = 0; i < coursesOnDay.length; i++){
            strCourses[i] = String.format("%s — %s", coursesOnDay[i].getCode(), coursesOnDay[i].getName());
        }

        // Set the radio button click method.
        builder.setSingleChoiceItems(strCourses, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedIndex[0] = which;
            }
        });

        // Set the "confirm" button.
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                update(coursesOnDay[selectedIndex[0]]);
            }
        });

        // Set the "cancel" button.
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { dialog.cancel(); }
        });

        builder.show();
    }

    /**
     * Method for the onClick of btnToggleAssign.
     * @param view  The view that calls this method.
     */
    public void toggleEnroll(View view) {
        if (course == null) {
            Utils.createErrorDialog(this, R.string.no_course);
            return;
        }
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        if (!dbHelper.checkEnrolled(student.getUsername(), course.getId())) {
            try {
                dbHelper.addEnrolledCourse(student.getUsername(), course.getId());
            } catch (IllegalArgumentException e) {
                Utils.createErrorDialog(self, R.string.timeslot_conflict);
            }
            update(course);

        } else {

            dbHelper.removeEnrolledCourse(student.getUsername(), course.getId());
            update(course);

        }
    }
}