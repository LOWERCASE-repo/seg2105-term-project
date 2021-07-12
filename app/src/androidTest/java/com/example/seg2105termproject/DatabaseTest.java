package com.example.seg2105termproject;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import org.junit.rules.ExternalResource;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    private DatabaseHelper dbHelper;

    /**
     * Caution!
     * This block of code will delete the database for every run of a @Test method.
     * This is to keep tests consistent and have each one start on a fresh database.
     * As a result, since we currently keep one database, and this Instrumented Test draws
     * from the simulated virtual device, the database saved for manual test will be affected!
     *
     * You have been warned!
     */
    @Rule
    public ExternalResource testRule = new ExternalResource() {
        @Override
        public Statement apply(Statement base, Description description) {

            // The part that deletes the database file.
            File database = new File("/data/data/com.example.seg2105termproject/databases/project_database.db");
            if (database.exists()) database.delete();

            return super.apply(base, description);
        }
    };

    @Before
    public void setUp(){
        dbHelper = new DatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext());
    }

    // All User-related database methods are below.
    @Test
    public void testAddUser(){
        String name1 = "admin";
        String name2 = "Tony";
        String name3 = "Subaru";

        Admin admin = new Admin(1, name1, "admin123");
        Instructor tony = new Instructor(2, name2, "regexFile");
        Student subaru = new Student(3, name3, "quack");

        dbHelper.addUser(admin);
        dbHelper.addUser(tony);
        dbHelper.addUser(subaru);

        User u1 = dbHelper.getUser(name1);
        User u2 = dbHelper.getUser(name2);
        User u3 = dbHelper.getUser(name3);

        assertEquals(admin, u1);
        assertEquals(tony, u2);
        assertEquals(subaru, u3);
    }

    @Test
    public void testDeleteUser(){
        String name1 = "admin";
        String name2 = "Tony";
        String name3 = "Subaru";

        Admin admin = new Admin(1, name1, "admin123");
        Instructor tony = new Instructor(2, name2, "regexFile");
        Student subaru = new Student(3, name3, "quack");

        dbHelper.addUser(admin);
        dbHelper.addUser(tony);
        dbHelper.addUser(subaru);

        dbHelper.deleteUser(name2);
        dbHelper.deleteUser(name3);

        User u1 = dbHelper.getUser(name1);

        assertEquals(admin, u1);

        try {
            dbHelper.getUser(name2);
            fail();
        } catch (IllegalArgumentException e){

        }

        try {
            dbHelper.getUser(name3);
            fail();
        } catch (IllegalArgumentException e){

        }
    }

    // Enrollment-related tests.
    @Test
    public void testAddEnrolledCourse(){
        Student jimmy = new Student(1, "Jimmy", "123");
        Course math = new Course(1, "Math", "MAT 1341");

        dbHelper.addUser(jimmy);
        dbHelper.addCourse(math);

        assertFalse(dbHelper.checkEnrolled(1, 1));

        dbHelper.addEnrolledCourse(1, 1);

        assertTrue(dbHelper.checkEnrolled(1, 1));
    }

    @Test
    public void testRemoveEnrolledCourse(){
        Student jimmy = new Student(1, "Jimmy", "123");
        Course math = new Course(1, "Math", "MAT 1341");
        Course bio = new Course(2, "Biology", "BIO 2432");

        dbHelper.addUser(jimmy);
        dbHelper.addCourse(math);
        dbHelper.addCourse(bio);

        dbHelper.addEnrolledCourse(1, 1);
        dbHelper.addEnrolledCourse(1, 2);

        assertTrue(dbHelper.checkEnrolled(1, 1));
        assertTrue(dbHelper.checkEnrolled(1, 2));

        dbHelper.removeEnrolledCourse(1, 1);

        assertFalse(dbHelper.checkEnrolled(1, 1));
    }

    @Test
    public void testGetEnrolledStudents(){
        Student jimmy = new Student(1, "Jimmy", "123");
        Student jenny = new Student(2, "Jenny", "321");
        Course math = new Course(1, "Math", "MAT 1341");
        Course bio = new Course(2, "Biology", "BIO 2432");

        dbHelper.addUser(jimmy);
        dbHelper.addUser(jenny);
        dbHelper.addCourse(math);
        dbHelper.addCourse(bio);

        dbHelper.addEnrolledCourse(1, 1);
        dbHelper.addEnrolledCourse(1, 2);
        dbHelper.addEnrolledCourse(2, 1);

        User[] resultUsers1 = dbHelper.getEnrolledStudents(1);
        User[] resultUsers2 = dbHelper.getEnrolledStudents(2);

        User[] expectedUsers1 = {jimmy, jenny};
        User[] expectedUsers2 = {jimmy};

        assertArrayEquals(expectedUsers1, resultUsers1);
        assertArrayEquals(expectedUsers2, resultUsers2);
    }

    @Test
    public void testGetEnrolledCourses() {
        Student jimmy = new Student(1, "Jimmy", "123");
        Student jenny = new Student(2, "Jenny", "321");
        Course math = new Course(1, "Math", "MAT 1341");
        Course bio = new Course(2, "Biology", "BIO 2432");

        dbHelper.addUser(jimmy);
        dbHelper.addUser(jenny);
        dbHelper.addCourse(math);
        dbHelper.addCourse(bio);

        dbHelper.addEnrolledCourse(1, 1);
        dbHelper.addEnrolledCourse(1, 2);
        dbHelper.addEnrolledCourse(2, 1);

        Course[] resultCourses1 = dbHelper.getEnrolledCourses(1);
        Course[] resultCourses2 = dbHelper.getEnrolledCourses(2);

        Course[] expectedCourses1 = {math, bio};
        Course[] expectedCourses2 = {math};

        assertArrayEquals(expectedCourses1, resultCourses1);
        assertArrayEquals(expectedCourses2, resultCourses2);
    }

    @Test
    public void testTimeSlotConflict(){
        Student jimmy = new Student(1, "Jimmy", "123");
        Course math = new Course(1, "Math", "MAT 1341",
                null,
                new DayOfWeek[]{DayOfWeek.MONDAY},
                new LocalTime[]{LocalTime.of(8, 30)},
                new LocalTime[]{LocalTime.of(10, 0)},
                null, 0);
        Course bio = new Course(2, "Biology", "BIO 2432",
                null,
                new DayOfWeek[]{DayOfWeek.TUESDAY},
                new LocalTime[]{LocalTime.of(8, 45)},
                new LocalTime[]{LocalTime.of(10, 15)},
                null, 0);
        Course english = new Course(3, "English", "ENG 1010",
                null,
                new DayOfWeek[]{DayOfWeek.MONDAY},
                new LocalTime[]{LocalTime.of(10, 1)},
                new LocalTime[]{LocalTime.of(12, 0)},
                null, 0);
        Course geo = new Course(3, "Geology", "GEO 4815",
                null,
                new DayOfWeek[]{DayOfWeek.MONDAY},
                new LocalTime[]{LocalTime.of(9, 30)},
                new LocalTime[]{LocalTime.of(11, 0)},
                null, 0);

        dbHelper.addUser(jimmy);
        dbHelper.addCourse(math);
        dbHelper.addCourse(bio);
        dbHelper.addCourse(english  );
        dbHelper.addCourse(geo);

        dbHelper.addEnrolledCourse(1, 1);
        dbHelper.addEnrolledCourse(1, 2);
        dbHelper.addEnrolledCourse(1, 3);

        // Test for proper exception throw.
        try {
            dbHelper.addEnrolledCourse(1, 4);
            fail();
        } catch (IllegalArgumentException e){

        }
    }

    // All Course-related database methods are below.
    @Test
    public void testAddCourse(){
        String code = "ENG 1010";
        Course c1 = new Course(
                "English",
                code,
                null,
                new DayOfWeek[0],
                new LocalTime[0],
                new LocalTime[0],
                null,
                0);
        dbHelper.addCourse(c1);
        Course c2 = dbHelper.getCourse(code);
        assertEquals(c1, c2);
    }

    @Test
    public void testDeleteCourse(){
        String code1 = "SEG 2105";
        String code2 = "BIO 8008";
        String code3 = "KAKA";
        Course test1 = new Course(1,"Software Engineering", code1);
        Course test2 = new Course(2, "Biology", code2);
        Course test3 = new Course(3, "Ka Ka", code3);
        Student[] students = {
                new Student(1, "Billy", "fart"),
                new Student(2, "Andrew", "bananas"),
                new Student(3, "Enigma", "edgykid")
        };

        dbHelper.addCourse(test1);
        dbHelper.addCourse(test2);
        dbHelper.addCourse(test3);

        for (Student student : students){
            dbHelper.addUser(student);
            dbHelper.addEnrolledCourse(student.getId(), 1);
            dbHelper.addEnrolledCourse(student.getId(), 3);
        }

        dbHelper.deleteCourse(code1);   // Delete course with enrolled students.
        dbHelper.deleteCourse(code2);   // Delete course without enrolled students.

        try {
            dbHelper.getCourse(code1);
            fail();
        } catch (IllegalArgumentException e){

        }

        for (Student student : students){
            assertFalse(dbHelper.checkEnrolled(student.getId(), 1));
            assertTrue(dbHelper.checkEnrolled(student.getId(), 3));
        }
    }

    @Test
    public void testChangeCourseCode(){
        String name = "Test";
        String code = "TST 1010";
        String newCode = "TST 1001";

        dbHelper.addCourse(new Course(name, code));
        dbHelper.changeCourseCode(code, newCode);
        assertEquals(new Course(name, newCode), dbHelper.getCourse(newCode));

        dbHelper.addCourse(new Course("English", "ENG 1010"));
        try {
            dbHelper.changeCourseCode(code, "ENG 1010");
            fail();
        } catch (IllegalArgumentException e){

        }
    }

    @Test
    public void testChangeCourseName(){
        String name = "Test";
        String newName = "Test for me";
        String code = "TST 1010";

        dbHelper.addCourse(new Course(name, code));
        dbHelper.changeCourseName(code, newName);
        assertEquals(new Course(newName, code), dbHelper.getCourse(code));
    }

    @Test
    public void testChangeCourseInstructor(){
        String name = "Math";
        String code = "MAT 1341";
        Course test = new Course(1,
                name,
                code,
                new Instructor("Mr. CRR", "password"),
                new DayOfWeek[]{DayOfWeek.MONDAY},
                new LocalTime[]{LocalTime.of(17, 30)},
                new LocalTime[]{LocalTime.of(19, 0)},
                "Linear Algebra",
                110);
        Student[] students = {
                new Student(1, "Billy", "fart"),
                new Student(2, "Andrew", "bananas"),
                new Student(3, "Enigma", "edgykid")
        };

        dbHelper.addCourse(test);

        for (Student student : students){
            dbHelper.addEnrolledCourse(student.getId(), 1);
        }

        dbHelper.changeCourseInstructor(code, null);
        assertEquals(new Course(name, code, null, new DayOfWeek[0], new LocalTime[0], new LocalTime[0], "", 0), dbHelper.getCourse(code));

        for (Student student : students){
            assertFalse(dbHelper.checkEnrolled(student.getId(), 1));
        }

        Instructor i = new Instructor("John", "fatty");
        dbHelper.addUser(i);

        dbHelper.changeCourseInstructor(code, i);
        assertEquals(new Course(name, code, i, new DayOfWeek[0], new LocalTime[0], new LocalTime[0], "", 0), dbHelper.getCourse(code));
    }

    @Test
    public void testChangeCourseTimes(){
        String name = "Test";
        String code = "TST 1010";
        DayOfWeek[] days = {DayOfWeek.MONDAY, DayOfWeek.THURSDAY, DayOfWeek.WEDNESDAY};
        LocalTime[] starts = {LocalTime.of(8, 30), LocalTime.of(10, 0), LocalTime.of(16, 20)};
        LocalTime[] ends = {LocalTime.of(10, 0), LocalTime.of(11, 30), LocalTime.of(20, 35)};

        DayOfWeek newDay = DayOfWeek.SATURDAY;
        LocalTime newStart = LocalTime.of(14, 25);
        LocalTime newEnd = LocalTime.of(19, 45);

        dbHelper.addCourse(new Course(name, code, null, days, starts, ends, null, 0));

        dbHelper.changeCourseTime(code, 1, newDay, newStart, newEnd);
        assertEquals(new Course(
                name, code, null,
                new DayOfWeek[]{days[0], newDay, days[2]},
                new LocalTime[]{starts[0], newStart, starts[2]},
                new LocalTime[]{ends[0], newEnd, ends[2]},
                null, 0
        ), dbHelper.getCourse(code));

        try {
            dbHelper.changeCourseTime(code, 10, newDay, newStart, newEnd);
            fail();
        } catch (ArrayIndexOutOfBoundsException e){

        }

        try {
            dbHelper.changeCourseTime(code, 2, null, newStart, newEnd);
            fail();
        } catch (NullPointerException e){

        }

        dbHelper.changeCourseTime(code, 0, days[1], starts[1], ends[1]);
        assertEquals(new Course(
                name, code, null,
                new DayOfWeek[]{days[1], newDay, days[2]},
                new LocalTime[]{starts[1], newStart, starts[2]},
                new LocalTime[]{ends[1], newEnd, ends[2]},
                null, 0
        ), dbHelper.getCourse(code));
    }

    @Test
    public void testAddCourseTimes(){
        String name = "Test";
        String code = "TST 1010";
        DayOfWeek[] days = {DayOfWeek.MONDAY, DayOfWeek.THURSDAY};
        LocalTime[] starts = {LocalTime.of(8, 30), LocalTime.of(10, 0)};
        LocalTime[] ends = {LocalTime.of(10, 0), LocalTime.of(11, 30)};

        dbHelper.addCourse(new Course(name, code));

        dbHelper.addCourseTime(code, days[0], starts[0], ends[0]);
        assertEquals(new Course(
                name, code, null,
                new DayOfWeek[]{days[0]}, new LocalTime[]{starts[0]}, new LocalTime[]{ends[0]},
                null, 0
        ), dbHelper.getCourse(code));

        dbHelper.addCourseTime(code, days[1], starts[1], ends[1]);
        assertEquals(new Course(
                name, code, null,
                new DayOfWeek[]{days[0], days[1]},
                new LocalTime[]{starts[0], starts[1]},
                new LocalTime[]{ends[0], ends[1]},
                null, 0
        ), dbHelper.getCourse(code));
    }

    @Test
    public void testDeleteCourseTimes(){
        String name = "Test";
        String code = "TST 1010";
        DayOfWeek[] days = {DayOfWeek.MONDAY, DayOfWeek.THURSDAY, DayOfWeek.WEDNESDAY};
        LocalTime[] starts = {LocalTime.of(8, 30), LocalTime.of(10, 0), LocalTime.of(16, 20)};
        LocalTime[] ends = {LocalTime.of(10, 0), LocalTime.of(11, 30), LocalTime.of(20, 35)};

        dbHelper.addCourse(new Course(name, code, null, days, starts, ends, null, 0));

        dbHelper.deleteCourseTime(code, 1);
        assertEquals(new Course(
                name, code, null,
                new DayOfWeek[]{days[0], days[2]},
                new LocalTime[]{starts[0], starts[2]},
                new LocalTime[]{ends[0], ends[2]},
                null, 0
        ), dbHelper.getCourse(code));

        try {
            dbHelper.deleteCourseTime(code, 10);
            fail();
        } catch (ArrayIndexOutOfBoundsException e){

        }

        dbHelper.deleteCourseTime(code, 0);
        assertEquals(new Course(
                name, code, null,
                new DayOfWeek[]{days[2]},
                new LocalTime[]{starts[2]},
                new LocalTime[]{ends[2]},
                null, 0
        ), dbHelper.getCourse(code));
    }

    @Test
    public void testChangeCourseDesc(){
        String name = "Test";
        String code = "TST 1010";
        String desc = "This is a test course.";

        dbHelper.addCourse(new Course(name, code));
        dbHelper.changeCourseDesc(code, desc);

        assertEquals(new Course(
                        name, code, null,
                        new DayOfWeek[0], new LocalTime[0], new LocalTime[0],
                        desc, 0
                ), dbHelper.getCourse(code));
    }

    @Test
    public void testChangeCapacity(){
        String name = "Test";
        String code = "TST 1010";
        int cap = 30;

        dbHelper.addCourse(new Course(name, code));
        dbHelper.changeCourseCapacity(code, cap);

        assertEquals(new Course(
                        name, code, null,
                        new DayOfWeek[0], new LocalTime[0], new LocalTime[0],
                        null, cap
                ), dbHelper.getCourse(code));
    }

    @Test
    public void testGetCoursesByDay(){
        Course[] courses = {
                new Course("A", "A", null,
                        new DayOfWeek[]{DayOfWeek.MONDAY},
                        new LocalTime[]{LocalTime.of(8, 30)},
                        new LocalTime[]{LocalTime.NOON},
                        null,
                        0),
                new Course("B", "B", null,
                        new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.THURSDAY},
                        new LocalTime[]{LocalTime.of(8, 30), LocalTime.NOON},
                        new LocalTime[]{LocalTime.NOON, LocalTime.of(15, 45)},
                        null,
                        0),
                new Course("C", "C", null,
                        new DayOfWeek[]{DayOfWeek.TUESDAY},
                        new LocalTime[]{LocalTime.of(8, 30)},
                        new LocalTime[]{LocalTime.NOON},
                        null,
                        0),
                new Course("D", "D", null,
                        new DayOfWeek[]{DayOfWeek.SATURDAY, DayOfWeek.MONDAY, DayOfWeek.FRIDAY},
                        new LocalTime[]{LocalTime.of(8, 30), LocalTime.of(10, 30), LocalTime.MIDNIGHT},
                        new LocalTime[]{LocalTime.NOON, LocalTime.MIDNIGHT, LocalTime.of(4, 50)},
                        null,
                        0),
                new Course("E", "E", null,
                        new DayOfWeek[]{},
                        new LocalTime[]{},
                        new LocalTime[]{},
                        null,
                        0),
        };

        for (Course course : courses){
            dbHelper.addCourse(course);
        }

        Course[] result = dbHelper.getCoursesFromDay(DayOfWeek.MONDAY);
        Course[] expected = {courses[0], courses[1], courses[3]};

        assertArrayEquals(expected, result);
    }


}
