package com.example.seg2105termproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

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
        private static final String COLUMN_USER_TYPE = "User_Type";
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
                    Accounts.COLUMN_USER_TYPE + " TEXT," +
                    Accounts.COLUMN_USERNAME + " TEXT UNIQUE," +
                    Accounts.COLUMN_PASSWORD + " TEXT)";

    private static final String SQL_CREATE_COURSETABLE =
            "CREATE TABLE " + CourseTable.TABLE_NAME + " (" +
                    CourseTable._ID + " INTEGER PRIMARY KEY," +
                    CourseTable.COLUMN_COURSE_NAME + " TEXT," +
                    CourseTable.COLUMN_COURSE_CODE + " TEXT UNIQUE)";

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
     * Add a User Object into the User_Accounts table of the database.
     * @param user      A User object to insert into the database.
     * @throws IllegalArgumentException     Thrown if the user param shares a username
     *                                      with an already-existing user.
     */
    public void addUser (User user) throws IllegalArgumentException{

        // Get reference to writable database (opens it?).
        SQLiteDatabase db = this.getWritableDatabase();

        // Put values of User object into container object.
        ContentValues values = new ContentValues();
        values.put(Accounts.COLUMN_USER_TYPE, user.getType().toString());
        values.put(Accounts.COLUMN_USERNAME, user.getUsername());
        values.put(Accounts.COLUMN_PASSWORD, user.getPassword());

        // Insert the entry into the Accounts table of the database, throw an error if we
        // invalidate the unique constraint.
        if (db.insert(Accounts.TABLE_NAME, null, values) == -1) {
            throw new IllegalArgumentException();
        }

        // Close the reference.
        db.close();
    }

    /**
     * Add a Course object into the CourseTable table of the database.
     * @param course    A Course object to insert into the database.
     * @exception IllegalArgumentException      Thrown if the course param shares a course code
     *                                          with an already-existing course.
     */
    public void addCourse (Course course) throws IllegalArgumentException{

        // Get reference to writable database.
        SQLiteDatabase db = this.getWritableDatabase();

        // Put values of Course object into container object.
        ContentValues values = new ContentValues();
        values.put(CourseTable.COLUMN_COURSE_NAME, course.getCourseName());
        values.put(CourseTable.COLUMN_COURSE_CODE, course.getCourseCode());

        // Insert the entry into the CourseTable table of the database, throw an error if we
        // invalidate the unique constraint.
        if (db.insert(CourseTable.TABLE_NAME, null, values) == -1) {
            throw new IllegalArgumentException();
        }

        // Close the reference.
        db.close();
    }

    /**
     * Delete the User with the corresponding username.
     * @param username  The username string of the User that should be deleted.
     * @throws IllegalArgumentException if deletion was not successful
     */
    public void deleteUser (String username){

        // Get reference to writable database.
        SQLiteDatabase db = this.getWritableDatabase();

        String[] selectionArgs = {username};

        // Query the database to delete the User, throw an exception if 0 rows were affected.
        int result = db.delete(
                Accounts.TABLE_NAME,
                Accounts.COLUMN_USERNAME + " LIKE ?",
                selectionArgs);

        // Regardless of success or not, close the database.
        db.close();

        // if we didn't modify any rows, show error message
        if (result == 0) throw new IllegalArgumentException();
    }

    /**
     * Delete the Course with the corresponding course code.
     * @param courseCode    The course code string of the Course that should be deleted.
     * @throws IllegalArgumentException if deletion was not successful
     */
    public void deleteCourse (String courseCode){

        // Get reference to writable database.
        SQLiteDatabase db = this.getWritableDatabase();

        String[] selectionArgs = {courseCode};

        int result = db.delete(
                CourseTable.TABLE_NAME,
                CourseTable.COLUMN_COURSE_CODE + " LIKE ?",
                selectionArgs);

        // Regardless of success or not, close the database.
        db.close();

        // if we didn't modify any rows, show error message
        if (result == 0) throw new IllegalArgumentException();
    }

    /**
     * Change the course code, with validation
     * @param oldCode The current course code
     * @param newCode The new course code.
     * @throws IllegalArgumentException if course is not found, or new course code already exists
     */
    public void changeCourseCode (String oldCode, String newCode) throws IllegalArgumentException{
        // Get reference to writable database.
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the course is in the database
        Cursor cursor = db.rawQuery(String.format("SELECT _ID FROM %s WHERE %s = \"%s\"",
                CourseTable.TABLE_NAME, CourseTable.COLUMN_COURSE_CODE, oldCode), null);

        // Make sure that the check was successful
        if (!cursor.moveToFirst()) {
            throw new IllegalArgumentException(String.valueOf(R.string.course_not_found));
        }

        // We'll use the id returned by the check
        String id = String.valueOf(cursor.getInt(0));

        // Close the cursor
        cursor.close();

        // Check that the new code isn't being used
        cursor = db.rawQuery(String.format("SELECT _ID FROM %s WHERE %s = \"%s\"",
                CourseTable.TABLE_NAME, CourseTable.COLUMN_COURSE_CODE, newCode), null);

        // Make sure the check was successful
        if (cursor.getCount() != 0) {
            throw new IllegalArgumentException(String.valueOf(R.string.course_already_exists));
        }

        // Close the cursor
        cursor.close();

        // Create the variable to hold the update info, and execute the update.
        ContentValues values = new ContentValues();
        values.put(CourseTable.COLUMN_COURSE_CODE, newCode);
        db.update(CourseTable.TABLE_NAME, values, CourseTable._ID + " = ?", new String[]{id});
    }

    /**
     * Change the course name, with validation
     * @param code The current course code
     * @param newName The new course code.
     * @throws IllegalArgumentException if course is not found, or new course code already exists
     */
    public void changeCourseName (String code, String newName) throws IllegalArgumentException{
        // Get reference to writable database.
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the course is in the database
        Cursor cursor = db.rawQuery(String.format("SELECT _ID FROM %s WHERE %s = \"%s\"",
                CourseTable.TABLE_NAME, CourseTable.COLUMN_COURSE_CODE, code), null);

        // Make sure that the check was successful
        if (!cursor.moveToFirst()) {
            throw new IllegalArgumentException();
        }

        // We'll use the id returned by the check
        String id = String.valueOf(cursor.getInt(0));

        // Close the cursor
        cursor.close();

        // Create the variable to hold the update info, then execute the update.
        ContentValues values = new ContentValues();
        values.put(CourseTable.COLUMN_COURSE_NAME, newName);
        db.update(CourseTable.TABLE_NAME, values, CourseTable._ID + " = ?", new String[]{id});
    }

    /**
     * Retrieve the User with the corresponding username from the database.
     * @param username  The username string of the needed User.
     * @return  A reference to the User with the corresponding username.
     *          Null if no User with the username was found.
     */
    public User getUser (String username){

        // Get reference to writable database.
        SQLiteDatabase db = this.getWritableDatabase();

        // Instance the SQL query to get the entry (User) with the passed username.
        String query = "SELECT * FROM " + Accounts.TABLE_NAME +
                " WHERE " + Accounts.COLUMN_USERNAME +
                " = \"" + username + "\"";

        // Construct the Cursor object with the "raw" query.
        Cursor cursor = db.rawQuery(query, null);

        // Declare the User variable.
        User user;

        // If the User was found,
        if (cursor.moveToFirst()){

            // Construct and get the reference to the appropriate user subclass.
            user = constructUserSubclass(cursor);

        } else {
            // If the object is not found, set to null.
            user = null;
        }

        // Close the cursor and database objects.
        cursor.close();
        db.close();

        // Return the User (or null if not found).
        return user;
    }

    /**
     * Retrieve the Course with the corresponding course code from the database.
     * @param courseCode    The course code string of the needed Course.
     * @return  A reference to a Course with the corresponding course code.
     *          Null if no Course with the course code was found.
     */
    public Course getCourse(String courseCode){

        // Get reference to writable database.
        SQLiteDatabase db = this.getWritableDatabase();

        // Instance the SQL query to get the entry (Course) with the passed course code.
        String query = "SELECT * FROM " + CourseTable.TABLE_NAME +
                " WHERE " + CourseTable.COLUMN_COURSE_CODE +
                " = \"" + courseCode + "\"";

        // Construct the Cursor object with the "raw" query.
        Cursor cursor = db.rawQuery(query, null);

        // Declare Course variable.
        Course course;

        // If the Course was found,
        if (cursor.moveToFirst()){

            // Construct the course object.
            course = new Course(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );

        } else {
            // If the course was not found, set to null.
            course = null;
        }

        // Close the cursor and database objects.
        cursor.close();
        db.close();

        // Return the Course (or null if not found).
        return course;
    }

    public Course getCourseFromName(String courseName){

        // Get reference to writable database.
        SQLiteDatabase db = this.getWritableDatabase();

        // Instance the SQL query to get the entry (Course) with the passed course code.
        String query = "SELECT * FROM " + CourseTable.TABLE_NAME +
                " WHERE " + CourseTable.COLUMN_COURSE_NAME +
                " = \"" + courseName + "\"";

        // Construct the Cursor object with the "raw" query.
        Cursor cursor = db.rawQuery(query, null);

        // Declare Course variable.
        Course course;

        // If the Course was found,
        if (cursor.moveToFirst()){

            // Construct the course object.
            course = new Course(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );

        } else {
            // If the course was not found, set to null.
            course = null;
        }

        // Close the cursor and database objects.
        cursor.close();
        db.close();

        // Return the Course (or null if not found).
        return course;
    }

    /**
     * Instantiates and returns the reference to an array containing all Users in the database.
     * @return  An array containing all Users in the database.
     */
    public User[] getAllUsers(){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + Accounts.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        User[] users;
        if (cursor.moveToFirst()){
            int i = 0;
            users = new User[cursor.getCount()];

            do {
                users[i++] = constructUserSubclass(cursor);

            } while (cursor.moveToNext());

        } else {
            users = new User[0];
        }

        cursor.close();
        db.close();
        return users;
    }

    /**
     * Instantiates and returns the reference to an array containing all Courses in the database.
     * @return  An array containing all Courses in the database.
     */
    public Course[] getAllCourses(){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + CourseTable.TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        Course[] courses;
        if (cursor.moveToFirst()){
            int i = 0;
            courses = new Course[cursor.getCount()];

            do {
                courses[i++] = new Course(
                        Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1),
                        cursor.getString(2)
                );

            } while (cursor.moveToNext());

        } else {
            courses = new Course[0];
        }

        cursor.close();
        db.close();
        return courses;
    }

    /**
     * Constructs and returns the reference to the User subclass object the passed cursor
     * object is pointing at.
     * @param cursor    The cursor object that currently points at the desired user in the
     *                  database.
     * @return  The reference to the User subclass object that the cursor is pointing at.
     */
    private User constructUserSubclass(Cursor cursor){

        // Declare the user variable.
        User user;

        // Get the type of User saved in the database.
        UserType type = UserType.valueOf(cursor.getString(1));

        // Switch-case statement on the UserType.
        // Construct the corresponding User subclass.
        switch (type){
            case ADMIN:
                user = new Admin(
                        cursor.getInt(0),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                break;

            case INSTRUCTOR:
                user = new Instructor(
                        cursor.getInt(0),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                break;

            case STUDENT:
                user = new Student(
                        cursor.getInt(0),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                break;

            default:
                // Only runs if the type saved is not a valid type,
                // therefore meaning an error has occurred if this runs.

                // Any better exception suggestions?
                throw new IllegalStateException("Stored User has does not have valid typing");
        }

        // Return the instantiated user subclass.
        return user;
    }
}
