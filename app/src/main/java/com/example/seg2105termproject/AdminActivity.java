package com.example.seg2105termproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
//import android.widget.Button;

public class AdminActivity extends AppCompatActivity {

    // embarrassingly enough i don't know how to reference this class from within an alert so if someone could replace this singleton with that, that'd be great
    static AdminActivity self;

    // What I have right now doesn't work. Feel free to completely change this.
//    public static class CreateCourseFragment extends DialogFragment {
//
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            LayoutInflater inflater = requireActivity().getLayoutInflater();
//
//            EditText editCName = this.getActivity().findViewById(R.id.editCNameCreate);
//            EditText editCCode = this.getActivity().findViewById(R.id.editCCodeCreate);
//
//            builder.setView(inflater.inflate(R.layout.create_course_dialog, null))
//                    .setTitle(R.string.create_course)
//                    .setPositiveButton(R.string.create, (dialog, which) -> {
//                        String courseName = editCName.getText().toString();
//                        String courseCode = editCCode.getText().toString();
//
//                        DatabaseHelper dbHelper = new DatabaseHelper(this.getActivity());
//
//                        try {
//                            dbHelper.addCourse(new Course(courseName, courseCode));
//                        } catch (IllegalArgumentException e) {
//                            Utils.createErrorDialog(this.getActivity(), R.string.course_already_exists);
//                        }
//                    });
//
//            return builder.create();
//        }
//    }

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

        Intent intent = getIntent();
        admin = (Admin) dbHelper.getUser(intent.getStringExtra(MainActivity.EXTRA_USER));

        tvAdminName.setText("Welcome " + admin.getUsername());
    }

    public void createCourse(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create Course");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText name = new EditText(this);
        name.setHint("Name");
        layout.addView(name);
        final EditText code = new EditText(this);
        code.setHint("Code");
        layout.addView(code);
        builder.setView(layout);
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("sysout", "aosnthuoeanthuo");
                String courseName = name.getText().toString();
                String courseCode = code.getText().toString();
                DatabaseHelper dbHelper = new DatabaseHelper(self);
                try {
                    dbHelper.addCourse(new Course(courseName, courseCode));
                    Log.d("sysout", "course added: " + courseName + " " + courseCode);
                } catch (IllegalArgumentException e) {
                    Log.d("sysout", "shit");
                    Utils.createErrorDialog(getParent(), R.string.course_already_exists);
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
        refreshViews();
    }

    private void refreshViews() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        cAdapter.refresh(dbHelper.getAllCourses());
        uAdapter.refresh(dbHelper.getAllUsers());
    }
}