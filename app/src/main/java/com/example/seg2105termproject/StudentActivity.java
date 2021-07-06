package com.example.seg2105termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Intent intent = getIntent();
        student = (Student) dbHelper.getUser(intent.getStringExtra(MainActivity.EXTRA_USER));

        tvStudentName.setText("Welcome " + student.getUsername());
    }

    /**
     * Update the views on the activity.
     * @param course    The course selected by the instructor (user).
     */
    private void update(Course course) {
        this.course = course;
        tvChosenCourse.setText(String.format("%s — %s", course.getCode(), course.getName()));
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

    // TODO findCourseByDay() — saved for jerry by request

    /**
     * Method for the onClick of btnToggleAssign.
     * @param view  The view that calls this method.
     */
    public void toggleEnroll(View view) {
//        if (course == null) {
//            Utils.createErrorDialog(this, R.string.no_course);
//            return;
//        }
//        DatabaseHelper dbHelper = new DatabaseHelper(this);
//
//        if (course.getInstructor() == null) {
//
//            dbHelper.addEnrolledCourse(student.getUsername(), course.getId());
//            Utils.createErrorDialog(this, R.string.enrolled); // maybe don't use error dialog for this
//            update(course);
//
//        } else if (course.getInstructor().equals(instructor)) {
//
//            // Set the course instructor to null.
//            dbHelper.changeCourseInstructor(course.getCode(), null);
//
//            // To save on resources (and not call another database open), simply set the
//            // selected course's instructor and update the views.
//            course.setInstructor(null);
//            update(course);
//
//        } else {
//            Utils.createErrorDialog(this, R.string.course_claimed);
//        }
    }
}