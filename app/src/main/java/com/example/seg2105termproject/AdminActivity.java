package com.example.seg2105termproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
//import android.widget.Button;

public class AdminActivity extends AppCompatActivity {

    // What I have right now doesn't work. Feel free to completely change this.
    public static class CreateCourseFragment extends DialogFragment{

        @Override
        public Dialog onCreateDialog (Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = requireActivity().getLayoutInflater();

            EditText editCName = this.getActivity().findViewById(R.id.editCNameCreate);
            EditText editCCode = this.getActivity().findViewById(R.id.editCCodeCreate);

            builder.setView(inflater.inflate(R.layout.create_course_dialog, null))
                    .setTitle(R.string.create_course)
                    .setPositiveButton(R.string.create, (dialog, which) -> {
                        String courseName = editCName.getText().toString();
                        String courseCode = editCCode.getText().toString();

                        DatabaseHelper dbHelper = new DatabaseHelper(this.getActivity());

                        try {
                            dbHelper.addCourse(new Course(courseName, courseCode));
                        } catch (IllegalArgumentException e){
                            Utils.createErrorDialog(this.getActivity(), R.string.course_already_exists);
                        }
                    });

            return builder.create();
        }
    }

    Admin admin;

//    Button btnCreateCourse, btnDeleteCourse, btnFindCName, btnFindCCode, btnDeleteUser;
    TextView tvAdminName;
    RecyclerView CoursesView, UsersView;

    CoursesViewAdapter cAdapter;
    UsersViewAdapter uAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        tvAdminName = findViewById(R.id.tvAdminName);

        CoursesView = findViewById(R.id.CoursesView);
        UsersView = findViewById(R.id.UsersView);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        cAdapter = new CoursesViewAdapter(dbHelper.getAllCourses());
        uAdapter = new UsersViewAdapter(dbHelper.getAllUsers());

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
    public void createCourse(View view){
        CreateCourseFragment ccFragment = new CreateCourseFragment();
        ccFragment.show(getSupportFragmentManager(), "create_course");
        refreshViews();
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