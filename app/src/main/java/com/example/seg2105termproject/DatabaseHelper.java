package com.example.seg2105termproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * Nested classes to define and contain table details.
     * Inheriting BaseColumns class gives the class an innate _ID value, and
     * "[helps the] database work harmoniously with the Android framework"
     * (basically, inheriting BaseColumns removes the need to declare an ID column).
     */
    // Accounts table for User objects.
    private static class Accounts implements BaseColumns {
        private static final String TABLE_NAME = "User_Accounts";
        private static final String COLUMN_ACCOUNT_TYPE = "Account_Type";
        private static final String COLUMN_USERNAME = "Username";
        private static final String COLUMN_PASSWORD = "Password";
    }

    // CourseTable table for Course objects.
    private static class CourseTable implements BaseColumns {
        private static final String TABLE_NAME = "Course_Table";
        private static final String COLUMN_COURSE_NAME = "Course_Name";
        private static final String COLUMN_COURSE_CODE = "Course_Code";
    }


    /**
     * The following constants are SQL queries to create and delete the two tables.
     */
    private static final String SQL_CREATE_ACCOUNTS =
            "CREATE TABLE " + Accounts.TABLE_NAME + " (" +
                    Accounts._ID + " INTEGER PRIMARY KEY," +
                    Accounts.COLUMN_ACCOUNT_TYPE + " TEXT," +
                    Accounts.COLUMN_USERNAME + " TEXT," +
                    Accounts.COLUMN_PASSWORD + " TEXT)";

    private static final String SQL_CREATE_COURSETABLE =
            "CREATE TABLE " + CourseTable.TABLE_NAME + " (" +
                    CourseTable._ID + " INTEGER PRIMARY KEY," +
                    CourseTable.COLUMN_COURSE_NAME + " TEXT," +
                    CourseTable.COLUMN_COURSE_CODE + " TEXT)";

    private static final String SQL_DELETE_ACCOUNTS =
            "DROP TABLE IF EXISTS " + Accounts.TABLE_NAME;

    private static final String SQL_DELETE_COURSETABLE =
            "DROP TABLE IF EXISTS " + CourseTable.TABLE_NAME;

    /**
     * The following are standard constants and methods for the OpenHelper.
     */
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "project_database.db";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ACCOUNTS);
        db.execSQL(SQL_CREATE_COURSETABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_ACCOUNTS);
        db.execSQL(SQL_DELETE_COURSETABLE);
        onCreate(db);
    }


    /**
     * Add a User object into the Accounts table of the database.
     * @param user  A User object to insert into the database.
     */
    public void addUser (User user){

        // Get reference to writable database (opens it?).
        SQLiteDatabase db = this.getWritableDatabase();

        // Put values of User object into container object.
        ContentValues values = new ContentValues();
        values.put(Accounts.COLUMN_ACCOUNT_TYPE, user.getType());
        values.put(Accounts.COLUMN_USERNAME, user.getUsername());
        values.put(Accounts.COLUMN_PASSWORD, user.getPassword());

        // Insert the entry into the Accounts table of the database.
        db.insert(Accounts.TABLE_NAME, null, values);

        // Close the reference.
        db.close();
    }

    /**
     * Add a Course object into the CourseTable table of the database.
     * @param course    A Course object to insert into the database.
     */
    public void addCourse (Course course){

        // Get reference to writable database.
        SQLiteDatabase db = this.getWritableDatabase();

        // Put values of Course object into container object.
        ContentValues values = new ContentValues();
        values.put(CourseTable.COLUMN_COURSE_NAME, course.getCourseName());
        values.put(CourseTable.COLUMN_COURSE_CODE, course.getCourseCode());

        // Insert the entry into the CourseTable table of the database.
        db.insert(CourseTable.TABLE_NAME, null, values);

        // Close the reference.
        db.close();
    }
}
