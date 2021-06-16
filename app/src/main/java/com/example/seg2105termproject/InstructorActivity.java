package com.example.seg2105termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * This file is part of Course Booking application for Android devices
 *
 * app/src/main/java/com/example/seg2105termproject
 *
 * University of Ottawa - Faculty of Engineering - SEG2105 -Course Booking application for Android devices
 * @author      Sally R       <@uottawa.ca>
 *              Jerry S       <jsoon029@uottawa.ca>
 *              Glen W        <@uottawa.ca>
 *              Youssef J     <yjall032@uottawa.ca>
 *
*/
public class InstructorActivity extends AppCompatActivity {

    static InstructorActivity self;
    TextView tvInstructorName, codeAndName, tvAssignedInstructor;
    Instructor instructor;
    Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        self = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);

        tvInstructorName = findViewById(R.id.tvInstructorName);
        codeAndName = findViewById(R.id.codeAndName);
        tvAssignedInstructor = findViewById(R.id.tvAssignedInstructor);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Intent intent = getIntent();
        instructor = (Instructor) dbHelper.getUser(intent.getStringExtra(MainActivity.EXTRA_USER));

        tvInstructorName.setText("Welcome " + instructor.getUsername());
    }

    private void update(Course course) {
        this.course = course;
        codeAndName.setText(course.getCourseCode() + " — " + course.getCourseName());
        Instructor instructor = course.getInstructor();
        if (instructor != null) {
            tvAssignedInstructor.setText(instructor.getUsername());
        } else {
            tvAssignedInstructor.setText(R.string.blank);
        }

    }

    public void findCourseByName(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Find Course By Name");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText name = new EditText(this);
        name.setHint("Name");
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

    public void findCourseByCode(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Find Course By Code");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText code = new EditText(this);
        code.setHint("Code");
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

    public void toggleAssignInstructor(View view) {
        if (course == null) {
            Utils.createErrorDialog(this, R.string.no_course);
            return;
        }
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        if (course.getInstructor() == null) {
            Log.d("sysout", "block entered");
            course.setInstructor(instructor);
            Log.d("sysout", "instructor set");
            dbHelper.deleteCourse(course.getCourseCode());
            Log.d("sysout", "course deleted");
            dbHelper.addCourse(course);
            Log.d("sysout", "course added");
            update(course);
            Log.d("sysout", "course updated");
        } else if (course.getInstructor().equals(instructor)) {
            course.setInstructor(null);
            dbHelper.deleteCourse(course.getCourseCode());
            dbHelper.addCourse(course);
            update(course);
        } else {
            Utils.createErrorDialog(this, R.string.course_claimed);
        }
    }
}