package com.example.seg2105termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class InstructorActivity extends AppCompatActivity {

    TextView tvInstructorName;

    Instructor instructor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);

        tvInstructorName = findViewById(R.id.tvInstructorName);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Intent intent = getIntent();
        instructor = (Instructor) dbHelper.getUser(intent.getStringExtra(MainActivity.EXTRA_USER));

        tvInstructorName.setText("Welcome " + instructor.getUsername());
    }
}