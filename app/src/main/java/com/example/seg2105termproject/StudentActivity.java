package com.example.seg2105termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class StudentActivity extends AppCompatActivity {

    TextView tvStudentName;

    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        tvStudentName = findViewById(R.id.tvStudentName);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Intent intent = getIntent();
        student = (Student) dbHelper.getUser(intent.getStringExtra(MainActivity.EXTRA_USER));

        tvStudentName.setText("Welcome " + student.getUsername());
    }
}