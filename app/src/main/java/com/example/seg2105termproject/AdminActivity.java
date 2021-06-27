package com.example.seg2105termproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import android.widget.Button;

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
public class AdminActivity extends AppCompatActivity {

    // embarrassingly enough i don't know how to reference this class from within an alert so if someone could replace this singleton with that, that'd be great
    static AdminActivity self;

    Admin admin;

    //    Button btnCreateCourse, btnDeleteCourse, btnFindCName, btnFindCCode, btnDeleteUser;
    TextView tvAdminName;
    RecyclerView CoursesView, UsersView;

    CoursesViewAdapter cAdapter;
    UsersViewAdapter uAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        self = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        tvAdminName = findViewById(R.id.tvAdminName);

        CoursesView = findViewById(R.id.CoursesView);
        UsersView = findViewById(R.id.UsersView);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        cAdapter = new CoursesViewAdapter(dbHelper.getAllCourses());
        uAdapter = new UsersViewAdapter(dbHelper.getAllUsers());

        LinearLayoutManager coursesLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager usersLayoutManager = new LinearLayoutManager(this);

        CoursesView.setLayoutManager(coursesLayoutManager);
        UsersView.setLayoutManager(usersLayoutManager);

        CoursesView.setAdapter(cAdapter);
        UsersView.setAdapter(uAdapter);

        Intent intent = getIntent();
        admin = (Admin) dbHelper.getUser(intent.getStringExtra(MainActivity.EXTRA_USER));

        tvAdminName.setText("Welcome " + admin.getUsername());
    }

    /**
     * Method for the onClick of btnCreateCourse.
     * @param view  The view that calls the method.
     */
    public void createCourse(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create Course");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText name = new EditText(this);
        name.setHint("Name");
        name.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        layout.addView(name);
        final EditText code = new EditText(this);
        code.setHint("Code");
        code.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        layout.addView(code);
        builder.setView(layout);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String courseName = name.getText().toString();
                String courseCode = code.getText().toString();
                DatabaseHelper dbHelper = new DatabaseHelper(self);

                // Trimming removes excess whitespace
                if (courseName.trim().equals("") || courseCode.trim().equals("")) {
                    Utils.createErrorDialog(self, R.string.parameters_missing);
                    return;
                }
                try {
                    dbHelper.addCourse(new Course(courseName, courseCode));
                    Log.d("sysout", "course added: " + courseName + " " + courseCode);
                } catch (IllegalArgumentException e) {
                    Utils.createErrorDialog(self, R.string.course_already_exists);
                }

                self.refreshViews();
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
     * Method for the onClick of btnEditCCode.
     * @param view  The view that calls this method.
     */
    public void editCourseCode(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Course Code");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText oldCodeText = new EditText(this);
        oldCodeText.setHint("Old Code");
        oldCodeText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        layout.addView(oldCodeText);
        final EditText newCodeText = new EditText(this);
        newCodeText.setHint("New Code");
        newCodeText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        layout.addView(newCodeText);
        builder.setView(layout);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHelper dbHelper = new DatabaseHelper(self);
                try {
                    String oldCode = oldCodeText.getText().toString(), newCode = newCodeText.getText().toString();
                    // Trimming removes excess whitespace
                    if (oldCode.trim().equals("") || newCode.trim().equals("")) {
                        Utils.createErrorDialog(self, R.string.parameters_missing);
                        return;
                    }
                    dbHelper.changeCourseCode(oldCodeText.getText().toString(), newCodeText.getText().toString());
                } catch (IllegalArgumentException e) {
                    Utils.createErrorDialog(self, R.string.course_not_found);
                }

                self.refreshViews();
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
     * Method for the onClick of btnEditCName.
     * @param view  The view that calls this method.
     */
    public void editCourseName(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Course Name");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText name = new EditText(this);
        name.setHint("New Name");
        name.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        layout.addView(name);
        final EditText code = new EditText(this);
        code.setHint("Code");
        code.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        layout.addView(code);
        builder.setView(layout);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String courseName = name.getText().toString();
                String courseCode = code.getText().toString();
                // Trimming removes excess whitespace
                if (courseName.trim().equals("") || courseCode.trim().equals("")) {
                    Utils.createErrorDialog(self, R.string.parameters_missing);
                    return;
                }
                DatabaseHelper dbHelper = new DatabaseHelper(self);
                try {
                    dbHelper.changeCourseName(courseCode, courseName);
                } catch (IllegalArgumentException e) {
                    Utils.createErrorDialog(self, R.string.course_not_found);
                }

                self.refreshViews();
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
     * Method for the onClick of btnDeleteCourse.
     * @param view  The view that calls this method.
     */
    public void deleteCourse(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Course");
        final EditText code = new EditText(this);
        code.setHint("Code");
        code.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        builder.setView(code);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String courseCode = code.getText().toString();
                // Trimming removes excess whitespace
                if (courseCode.trim().equals("")) {
                    Utils.createErrorDialog(self, R.string.parameters_missing);
                    return;
                }
                DatabaseHelper dbHelper = new DatabaseHelper(self);
                try {
                    dbHelper.deleteCourse(courseCode);
                } catch (IllegalArgumentException e) {
                    Utils.createErrorDialog(self, R.string.course_not_found);
                }

                self.refreshViews();
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
     * Method for the onClick of btnDeleteUser.
     * @param view  The view that calls this method.
     */
    public void deleteUser(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete User");
        final EditText name = new EditText(this);
        name.setHint("Username");
        name.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        builder.setView(name);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String username = name.getText().toString();
                DatabaseHelper dbHelper = new DatabaseHelper(self);
                // Trimming removes excess whitespace
                if (username.trim().equals("")) {
                    Utils.createErrorDialog(self, R.string.parameters_missing);
                    return;
                }
                try {
                    User user = dbHelper.getUser(username);
                    if (user.getType() == UserType.ADMIN) {
                        Utils.createErrorDialog(self, R.string.cannot_delete_self);
                    } else {
                        dbHelper.deleteUser(username);
                    }
                } catch (IllegalArgumentException e) {
                    Utils.createErrorDialog(self, R.string.user_not_found);
                }

                self.refreshViews();
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
     * Refreshes the RecyclerViews with the latest User and Course data.
     */
    private void refreshViews() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        cAdapter.refresh(dbHelper.getAllCourses());
        uAdapter.refresh(dbHelper.getAllUsers());
    }
}