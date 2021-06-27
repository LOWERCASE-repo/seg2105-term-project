package com.example.seg2105termproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.time.DayOfWeek;
import java.time.LocalTime;

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
        private static final String COLUMN_COURSE_INSTRUCTOR = "Course_Instructor";
        private static final String COLUMN_COURSE_DAYS = "Course_Days";
        private static final String COLUMN_COURSE_START_TIMES = "Course_Start_Times";
        private static final String COLUMN_COURSE_END_TIMES = "Course_End_Times";
        private static final String COLUMN_COURSE_DESC = "Course_Description";
        private static final String COLUMN_COURSE_CAPACITY = "Course_Capacity";
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
                    CourseTable.COLUMN_COURSE_CODE + " TEXT UNIQUE," +
                    CourseTable.COLUMN_COURSE_INSTRUCTOR + " TEXT," +
                    CourseTable.COLUMN_COURSE_DAYS + " TEXT," +
                    CourseTable.COLUMN_COURSE_START_TIMES + " TEXT," +
                    CourseTable.COLUMN_COURSE_END_TIMES + " TEXT," +
                    CourseTable.COLUMN_COURSE_DESC + " TEXT," +
                    CourseTable.COLUMN_COURSE_CAPACITY + " INTEGER)";

    private static final String SQL_DELETE_ACCOUNTS =
            "DROP TABLE IF EXISTS " + Accounts.TABLE_NAME;

    private static final String SQL_DELETE_COURSETABLE =
            "DROP TABLE IF EXISTS " + CourseTable.TABLE_NAME;

    /**
     * A string constant of null to add null string values to ContentValues objects.
     */
    private static final String EMPTY = null;

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

//        Log.d("sysout", "add course called");

        // Get reference to writable database.
        SQLiteDatabase db = this.getWritableDatabase();

        // Put values of Course object into container object.
        ContentValues values = new ContentValues();
        values.put(CourseTable.COLUMN_COURSE_NAME, course.getName());
        values.put(CourseTable.COLUMN_COURSE_CODE, course.getCode());

        Instructor instructor = course.getInstructor();
        if (instructor == null) {
            // Replace with tertiary operator when Log is removed.
            values.put(CourseTable.COLUMN_COURSE_INSTRUCTOR, EMPTY);
            Log.d("sysout", "add course null instructor");
        } else {
            values.put(CourseTable.COLUMN_COURSE_INSTRUCTOR, course.getInstructor().getUsername());
            Log.d("sysout", "add course with instructor");
        }

        values.put(CourseTable.COLUMN_COURSE_DAYS, Utils.daysToString(course.getDays()));
        values.put(CourseTable.COLUMN_COURSE_START_TIMES, Utils.timesToString(course.getStartTimes()));
        values.put(CourseTable.COLUMN_COURSE_END_TIMES, Utils.timesToString(course.getEndTimes()));
        values.put(CourseTable.COLUMN_COURSE_DESC, course.getDescription());
        values.put(CourseTable.COLUMN_COURSE_CAPACITY, course.getCapacity());

        // Insert the entry into the CourseTable table of the database, throw an error if we
        // invalidate the unique constraint.
        if (db.insert(CourseTable.TABLE_NAME, null, values) == -1) {
            Log.d("sysout", "add course failed");
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
    public void deleteUser (String username) throws IllegalArgumentException{

        // Get reference to writable database.
        SQLiteDatabase db = this.getWritableDatabase();

        String[] selectionArgs = {username};

        // Query the database to delete the User.
        int result = db.delete(
                Accounts.TABLE_NAME,
                Accounts.COLUMN_USERNAME + " LIKE ?",
                selectionArgs);

        // Delete the course details of courses the instructor was teaching.
        // Does not affect any course if a Student is deleted, since all usernames are unique.
        ContentValues values = new ContentValues();
        values.put(CourseTable.COLUMN_COURSE_INSTRUCTOR, EMPTY);
        values.put(CourseTable.COLUMN_COURSE_DAYS, EMPTY);
        values.put(CourseTable.COLUMN_COURSE_START_TIMES, EMPTY);
        values.put(CourseTable.COLUMN_COURSE_END_TIMES, EMPTY);
        values.put(CourseTable.COLUMN_COURSE_DESC, EMPTY);
        values.put(CourseTable.COLUMN_COURSE_CAPACITY, 0);

        db.update(
                CourseTable.TABLE_NAME,
                values,
                CourseTable.COLUMN_COURSE_INSTRUCTOR + " = ?",
                new String[]{username}
        );

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
    public void deleteCourse (String courseCode) throws IllegalArgumentException{

        // Get reference to writable database.
        SQLiteDatabase db = this.getWritableDatabase();

        String[] selectionArgs = {courseCode};

        int result = db.delete(
                CourseTable.TABLE_NAME,
                CourseTable.COLUMN_COURSE_CODE + " LIKE ?",
                selectionArgs);

        // Regardless of success or not, close the database.
        db.close();

        // If we didn't modify any rows, the course was not found. Throw the exception.
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

        // Check that the new code isn't being used
        Cursor cursor = db.rawQuery(String.format("SELECT _ID FROM %s WHERE %s = \"%s\"",
                CourseTable.TABLE_NAME, CourseTable.COLUMN_COURSE_CODE, newCode), null);

        // Make sure the check was successful
        if (cursor.getCount() != 0) {
            throw new IllegalArgumentException(String.valueOf(R.string.course_already_exists));
        }

        // Close the cursor.
        cursor.close();

        // Create the variable to hold the update info, and execute the update.
        ContentValues values = new ContentValues();
        values.put(CourseTable.COLUMN_COURSE_CODE, newCode);

        int result = db.update(
                CourseTable.TABLE_NAME,
                values,
                CourseTable.COLUMN_COURSE_CODE + " = ?",
                new String[]{oldCode});

        // Close the database.
        db.close();

        // If we didn't modify any rows, the course was not found. Throw the exception.
        if (result == 0) throw new IllegalArgumentException();
    }

    /**
     * Change the course name, with validation
     * @param code The current course code
     * @param newName The new course code.
     * @throws IllegalArgumentException if course is not found.
     */
    public void changeCourseName (String code, String newName) throws IllegalArgumentException{
        // Get reference to writable database.
        SQLiteDatabase db = this.getWritableDatabase();

        // Create the variable to hold the update info, then execute the update.
        ContentValues values = new ContentValues();
        values.put(CourseTable.COLUMN_COURSE_NAME, newName);

        int result = db.update(
                CourseTable.TABLE_NAME,
                values,
                CourseTable.COLUMN_COURSE_CODE + " LIKE ?",
                new String[]{code});

        // Close the database.
        db.close();

        // If we didn't modify any rows, the course was not found. Throw the exception.
        if (result == 0) throw new IllegalArgumentException();
    }

    /**
     * Change the Instructor of the specified Course to the passed Instructor.
     * @param code          The code of the course which needs its instructor updated.
     * @param instructor    The instructor to set for the course. Can be null.
     * @throws IllegalArgumentException if course is not found.
     */
    public void changeCourseInstructor(String code, Instructor instructor) throws IllegalArgumentException{
        // Get reference to writable database.
        SQLiteDatabase db = this.getWritableDatabase();

        // Create the variable to hold the update info, then execute the update.
        ContentValues values = new ContentValues();

        // If deleting the instructor,
        if (instructor == null){

            // delete the course details of courses the instructor was teaching.
            values.put(CourseTable.COLUMN_COURSE_INSTRUCTOR, EMPTY);
            values.put(CourseTable.COLUMN_COURSE_DAYS, EMPTY);
            values.put(CourseTable.COLUMN_COURSE_START_TIMES, EMPTY);
            values.put(CourseTable.COLUMN_COURSE_END_TIMES, EMPTY);
            values.put(CourseTable.COLUMN_COURSE_DESC, EMPTY);
            values.put(CourseTable.COLUMN_COURSE_CAPACITY, 0);

        } else {
            values.put(CourseTable.COLUMN_COURSE_INSTRUCTOR, instructor.getUsername());
        }

        int result = db.update(
                CourseTable.TABLE_NAME,
                values,
                CourseTable.COLUMN_COURSE_CODE + " = ?",
                new String[]{code});

        // Close the database.
        db.close();

        // If we didn't modify any rows, the course was not found. Throw the exception.
        if (result == 0) throw new IllegalArgumentException();
    }

    /**
     * Change the course times (weekdays, start and end times of said weekdays) of the specified
     * Course to the passed times.
     * The start and end times, kept in their respective array parameters, correspond to the days
     * of the week, kept in the days array parameter.
     * Together, they form a period of time of when the course is active.
     * @param code          The code of the course which needs its times updated.
     * @param days          The days of the week the course is active.
     * @param startTimes    The time of the day when the course starts.
     * @param endTimes      The time of the day when the course ends.
     * @throws IllegalArgumentException if course is not found or if parameter arrays are not
     *                                  the same length.
     * @throws NullPointerException if any of the time arrays are null.
     */
    public void changeCourseTimes(String code, DayOfWeek[] days, LocalTime[] startTimes, LocalTime[] endTimes) throws IllegalArgumentException, NullPointerException{
        // If the arrays are not of the same length, throw exception.
        if (days.length != startTimes.length || days.length != endTimes.length){
            throw new IllegalArgumentException(Course.TIME_ARRAYS_LENGTH_NOT_EQ);
        }

        // Get reference to writable database.
        SQLiteDatabase db = this.getWritableDatabase();

        // Create the variable to hold the update info, then execute the update.
        ContentValues values = new ContentValues();
        values.put(CourseTable.COLUMN_COURSE_DAYS, Utils.daysToString(days));
        values.put(CourseTable.COLUMN_COURSE_START_TIMES, Utils.timesToString(startTimes));
        values.put(CourseTable.COLUMN_COURSE_END_TIMES, Utils.timesToString(endTimes));

        int result = db.update(
                CourseTable.TABLE_NAME,
                values,
                CourseTable.COLUMN_COURSE_CODE + " = ?",
                new String[]{code});

        // Close the database.
        db.close();

        // If we didn't modify any rows, the course was not found. Throw the exception.
        if (result == 0) throw new IllegalArgumentException();
    }

    /**
     * Adds a specified course time (being a combination of a day of the week, a start
     * time, and an end time) from a course in the database.
     * @param code      The code of the course which needs a time added.
     * @param day       The day of the week of the course time to add.
     * @param startTime The start time of the course time to add.
     * @param endTime   The end time of the course time to add.
     * @throws IllegalArgumentException if course is not found.
     * @throws NullPointerException if any of the course time parameters are null.
     */
    public void addCourseTimes(String code, DayOfWeek day, LocalTime startTime, LocalTime endTime) throws IllegalArgumentException, NullPointerException{
        // If any time parameters are null, throw NullPointerException.
        if (day == null || startTime == null || endTime == null){
            throw new NullPointerException();
        }

        // Get reference to writable database.
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a cursor object, locating the selected course.
        Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s WHERE %s = \"%s\"",
                CourseTable.TABLE_NAME, CourseTable.COLUMN_COURSE_CODE, code), null);

        // If the course is not found, throw exception. Since this is done now,
        // no exception has to be thrown later.
        if (!cursor.moveToFirst()){
            throw new IllegalArgumentException();
        }

        // Create the new string, having the new time data attached to the back.
        String newDays = cursor.getString(4) + day.toString() + ",";
        String newStartTimes = cursor.getString(5) + startTime.toString() + ",";
        String newEndTimes = cursor.getString(6) + endTime.toString() + ",";

        // Add the values into the ContentValues container.
        ContentValues values = new ContentValues();
        values.put(CourseTable.COLUMN_COURSE_DAYS, newDays);
        values.put(CourseTable.COLUMN_COURSE_START_TIMES, newStartTimes);
        values.put(CourseTable.COLUMN_COURSE_END_TIMES, newEndTimes);

        // Update the database.
        db.update(
                CourseTable.TABLE_NAME,
                values,
                CourseTable.COLUMN_COURSE_CODE + " = ?",
                new String[]{code});

        // Close the database.
        db.close();
    }

    /**
     * Deletes a specified course time (being a combination of a day of the week, a start
     * time, and an end time) from a course in the database.
     * @param code      The code of the course which needs a time deleted.
     * @param day       The day of the week of the course time to delete.
     * @param startTime The start time of the course time to delete.
     * @param endTime   The end time of the course time to delete.
     * @throws IllegalArgumentException if course is not found.
     * @throws NullPointerException if any of the course time parameters are null.
     * @throws ArrayIndexOutOfBoundsException   if the specified time is not registered
     *                                          for the course.
     */
    public void deleteCourseTime (String code, DayOfWeek day, LocalTime startTime, LocalTime endTime) throws IllegalArgumentException, NullPointerException, ArrayIndexOutOfBoundsException{
        // If any time parameters are null, throw NullPointerException.
        if (day == null || startTime == null || endTime == null){
            throw new NullPointerException();
        }

        // Get reference to writable database.
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a cursor object, locating the selected course.
        Cursor cursor = db.rawQuery(String.format("SELECT * FROM %s WHERE %s = \"%s\"",
                CourseTable.TABLE_NAME, CourseTable.COLUMN_COURSE_CODE, code), null);

        // If the course is not found, throw exception. Since this is done now,
        // no exception has to be thrown later.
        if (!cursor.moveToFirst()){
            throw new IllegalArgumentException();
        }

        // Create arrays of the old time values.
        DayOfWeek[] oldDays = Utils.parseDays(cursor.getString(4));
        LocalTime[] oldStartTimes = Utils.parseTimes(cursor.getString(5));
        LocalTime[] oldEndTimes = Utils.parseTimes(cursor.getString(6));

        // If the array size is 0, then the next part won't work.
        // As such, throw ArrayIndexOutOfBoundsException to symbolize that the
        // specified time is not registered for the course.
        if (oldDays.length == 0){
            throw new ArrayIndexOutOfBoundsException();
        }

        // Instantiate new arrays for the new time values
        // (one item shorter than the old arrays).
        DayOfWeek[] newDays = new DayOfWeek[oldDays.length - 1];
        LocalTime[] newStartTimes = new LocalTime[oldStartTimes.length - 1];
        LocalTime[] newEndTimes = new LocalTime[oldEndTimes.length - 1];

        // An index integer for the new array.
        int index = 0;

        // Loop through the old arrays.
        // Will automatically result in ArrayIndexOutOfBoundsException due to unhandled
        // incrementing as a result of not having one time set skipped.
        for (int i = 0; i < oldDays.length; i++){

            // For all values except the set described by the parameters,
            // save them into the new arrays.
            if (!oldDays[i].equals(day) || !oldStartTimes[i].equals(startTime) || !oldEndTimes[i].equals(endTime)){
                newDays[index] = oldDays[i];
                newStartTimes[index] = oldStartTimes[i];
                newEndTimes[index] = oldEndTimes[i];
                index++;
            }
        }

        // Put the new time arrays into the ContentValues container.
        ContentValues values = new ContentValues();
        values.put(CourseTable.COLUMN_COURSE_DAYS, Utils.daysToString(newDays));
        values.put(CourseTable.COLUMN_COURSE_START_TIMES, Utils.timesToString(newStartTimes));
        values.put(CourseTable.COLUMN_COURSE_END_TIMES, Utils.timesToString(newEndTimes));

        // Update the database.
        db.update(CourseTable.TABLE_NAME,
                values,
                CourseTable.COLUMN_COURSE_CODE + " = ?",
                new String[]{code});

        // Close the database.
        db.close();
    }

    /**
     * Change the description of the specified Course to the passed description string.
     * @param code      The code of the Course which needs its description updated.
     * @param newDesc   The new description of the Course.
     * @throws IllegalArgumentException if course is not found.
     */
    public void changeCourseDesc(String code, String newDesc) throws IllegalArgumentException{
        // Get reference to writable database.
        SQLiteDatabase db = this.getWritableDatabase();

        // Create the variable to hold the update info, then execute the update.
        ContentValues values = new ContentValues();
        values.put(CourseTable.COLUMN_COURSE_DESC, newDesc);

        int result = db.update(
                CourseTable.TABLE_NAME,
                values,
                CourseTable.COLUMN_COURSE_CODE + " = ?",
                new String[]{code});

        // Close the database.
        db.close();

        // If we didn't modify any rows, the course was not found. Throw the exception.
        if (result == 0) throw new IllegalArgumentException();
    }

    /**
     * Change the capacity of a specified Course to the passed capacity integer.
     * @param code          The code of the Course which needs its capacity updated.
     * @param newCapacity   The new capacity (integer) of the Course.
     * @throws IllegalArgumentException if course is not found.
     */
    public void changeCourseCapacity(String code, int newCapacity) throws IllegalArgumentException{
        // Get reference to writable database.
        SQLiteDatabase db = this.getWritableDatabase();

        // Create the variable to hold the update info, then execute the update.
        ContentValues values = new ContentValues();
        values.put(CourseTable.COLUMN_COURSE_CAPACITY, newCapacity);

        int result = db.update(
                CourseTable.TABLE_NAME,
                values,
                CourseTable.COLUMN_COURSE_CODE + " = ?",
                new String[]{code});

        // Close the database.
        db.close();

        // If we didn't modify any rows, the course was not found. Throw the exception.
        if (result == 0) throw new IllegalArgumentException();
    }

    /**
     * Retrieve the User with the corresponding username from the database.
     * @param username  The username string of the needed User.
     * @return  A reference to the User with the corresponding username.
     * @throws IllegalArgumentException if no User with the username was found.
     */
    public User getUser (String username) throws IllegalArgumentException{

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
            // If the object is not found, throw exception.
            throw new IllegalArgumentException();
        }

        // Close the cursor and database objects.
        cursor.close();
        db.close();

        // Return the User.
        return user;
    }

    /**
     * Retrieve the Course with the corresponding course code from the database.
     * @param courseCode    The course code string of the needed Course.
     * @return  A reference to a Course with the corresponding course code.
     * @throws IllegalArgumentException if no Course with the course code was found.
     */
    public Course getCourse(String courseCode) throws IllegalArgumentException{

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

            // Construct the Course.
            course = constructCourse(cursor);

        } else {
            // If the course was not found, throw exception.
            throw new IllegalArgumentException();
        }

        // Close the cursor and database objects.
        cursor.close();
        db.close();

//        Log.d("sysout", "getcourse called, instructor: " + course.getInstructor().getUsername());

        // Return the Course.
        return course;
    }

    /**
     * Retrieve the Course with the corresponding course name from the database.
     * @param courseName    The course name string of the needed Course.
     * @return  A reference to a Course with the corresponding course name.
     * @throws IllegalArgumentException if no Course with the course name was found.
     */
    public Course getCourseFromName(String courseName) throws IllegalArgumentException{

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
            course = constructCourse(cursor);

        } else {
            // If the course was not found, throw exception.
            throw new IllegalArgumentException();
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
                courses[i++] = constructCourse(cursor);

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

    /**
     * Constructs and returns the reference to the Course object the passed cursor
     * object is pointing at.
     * @param cursor    The cursor object that currently points at the desired course in the
     *                  database.
     * @return  The reference to the Course object that the cursor is pointing at.
     */
    public Course constructCourse(Cursor cursor){
        // Check if instructor for the course exists.
        Instructor instructor;

        try {
            // Get the Instructor object from the database
            // (i.e. confirm Instructor is in database).
            instructor = (Instructor) getUser(cursor.getString(3));
        } catch (IllegalArgumentException e){

            // Thrown exception means not found.
            instructor = null;
        }

        // Create the course.
        return new Course(
                Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                instructor,
                Utils.parseDays(cursor.getString(4)),
                Utils.parseTimes(cursor.getString(5)),
                Utils.parseTimes(cursor.getString(6)),
                cursor.getString(7),
                Integer.parseInt(cursor.getString(8))
        );
    }
}
