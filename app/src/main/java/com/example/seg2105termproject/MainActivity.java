package com.example.seg2105termproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
//import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

/**
 * This file is part of Course Booking application for Android devices
 *
 * app/src/main/java/com/example/seg2105termproject
 *
 * University of Ottawa - Faculty of Engineering - SEG2105 -Course Booking application for Android devices
 * @author      Sally R       <@uottawa.ca> 
 *              Jerry S       <@uottawa.ca>
 *              Glen W        <@uottawa.ca>
 *              Youssef J     <yjall032@uottawa.ca>
 * 
*/
public class MainActivity extends AppCompatActivity {

//    Button btnSignUp, btnSignIn;
    EditText editUsernameUp, editPasswordUp, editRepeat, editUsernameIn, editPasswordIn;
    RadioButton radInstructor, radStudent;

    // Key for "extra" data that will be sent to the other activities.
    // Namely, the User's username, to bring across the user that logged in.
    public static final String EXTRA_USER = "com.example.seg2105termproject.USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        btnSignUp = findViewById(R.id.btnSignUp);
//        btnSignIn = findViewById(R.id.btnSignIn);

        editUsernameUp = findViewById(R.id.editUsernameUp);
        editUsernameIn = findViewById(R.id.editUsernameIn);
        editPasswordUp = findViewById(R.id.editPasswordUp);
        editPasswordIn = findViewById(R.id.editPasswordIn);
        editRepeat = findViewById(R.id.editRepeat);

        radInstructor = findViewById(R.id.radInstructor);
        radStudent = findViewById(R.id.radStudent);

        // I don't want to import a starter database so I'll add a permanent admin account
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        try {
            dbHelper.addUser(new Admin("admin", "admin123"));
        } catch (IllegalArgumentException ignored){}
    }

    /**
     * Method for the onClick of btnSignUp.
     * @param view  The view that calls this method.
     */
    public void signUp(View view){

        String username = editUsernameUp.getText().toString();
        String password = editPasswordUp.getText().toString();
        String repeat = editRepeat.getText().toString();

        boolean isInstructor = radInstructor.isChecked();
        boolean isStudent = radStudent.isChecked();

        if (
                username.trim().equals("")
                || password.equals("")
                || (!isInstructor && !isStudent)
        ) {
            // Error message: Fields missing.
            Utils.createErrorDialog(this, R.string.missing_fields);

        } else if (!password.equals(repeat)){
            // Error message: Passwords do not match.
            Utils.createErrorDialog(this, R.string.repeat_incorrect);

        } else {
            DatabaseHelper dbHelper = new DatabaseHelper(this);

            User newUser;
            if (isInstructor){
                newUser = new Instructor(username, password);

            } else {    // Must be student.
                newUser = new Student(username, password);
            }

            try {
                dbHelper.addUser(newUser);
            } catch (IllegalArgumentException e){
                Utils.createErrorDialog(this, R.string.user_already_exists);
            }


            editUsernameUp.setText("");

            // Automatic log in -> change to proper activity.
        }

        editPasswordUp.setText("");
        editRepeat.setText("");
    }

    /**
     * Method for the onClick of btnSignIn.
     * @param view  The view that calls this method.
     */
    public void signIn(View view){

        String username = editUsernameIn.getText().toString();
        String password = editPasswordIn.getText().toString();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        User desiredUser = dbHelper.getUser(username);

        if (desiredUser == null){
            // Error message: user not found.
            Utils.createErrorDialog(this, R.string.user_not_found);

        } else {

            if (password.equals(desiredUser.getPassword())){

                Intent intent = null;
                switch (desiredUser.getType()){
                    case ADMIN:
                        // Load admin activity in intent object.
                        intent = new Intent(this, AdminActivity.class);
                        break;
                    case INSTRUCTOR:
                        // Load instructor activity in intent object.
                        intent = new Intent(this, InstructorActivity.class);
                        break;
                    case STUDENT:
                        // Load student activity in intent object.
                        intent = new Intent(this, StudentActivity.class);
                        break;
                }

                // Send the username through to the activity.
                intent.putExtra(EXTRA_USER, username);

                // Start the user activity.
                startActivity(intent);

            } else {
                // Error message: password incorrect.
                Utils.createErrorDialog(this, R.string.password_incorrect);
            }

            editUsernameIn.setText("");
        }

        editPasswordIn.setText("");
    }
}