package com.example.seg2105termproject;

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
        assertEquals(c2, c1);
    }

    @Test
    public void testDeleteCourse(){
        String code = "SEG 2105";
        Course test = new Course("Software Engineering", code);
        dbHelper.addCourse(test);
        dbHelper.deleteCourse(code);
        try {
            dbHelper.getCourse(code);
            fail();
        } catch (IllegalArgumentException e){

        }
    }

    @Test
    public void testChangeCourseCode(){
        String name = "Test";
        String code = "TST 1010";
        String newCode = "TST 1001";

        dbHelper.addCourse(new Course(name, code));
        dbHelper.changeCourseCode(code, newCode);
        assertEquals(dbHelper.getCourse(newCode), new Course(name, newCode));

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
        assertEquals(dbHelper.getCourse(code), new Course(newName, code));
    }

    @Test
    public void testChangeCourseInstructor(){
        String name = "Math";
        String code = "MAT 1341";
        Course test = new Course(
                name,
                code,
                new Instructor("Mr. CRR", "password"),
                new DayOfWeek[]{DayOfWeek.MONDAY},
                new LocalTime[]{LocalTime.of(17, 30)},
                new LocalTime[]{LocalTime.of(19, 0)},
                "Linear Algebra",
                110);

        dbHelper.addCourse(test);
        dbHelper.changeCourseInstructor(code, null);
        assertEquals(dbHelper.getCourse(code), new Course(name, code));

        Instructor i = new Instructor("John", "fatty");
        dbHelper.addUser(i);

        dbHelper.changeCourseInstructor(code, i);
        assertEquals(dbHelper.getCourse(code), new Course(1, name, code, i));
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
        assertEquals(dbHelper.getCourse(code), new Course(
                name, code, null,
                new DayOfWeek[]{days[0], newDay, days[2]},
                new LocalTime[]{starts[0], newStart, starts[2]},
                new LocalTime[]{ends[0], newEnd, ends[2]},
                null, 0
        ));

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
        assertEquals(dbHelper.getCourse(code), new Course(
                name, code, null,
                new DayOfWeek[]{days[1], newDay, days[2]},
                new LocalTime[]{starts[1], newStart, starts[2]},
                new LocalTime[]{ends[1], newEnd, ends[2]},
                null, 0
        ));
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
        assertEquals(dbHelper.getCourse(code), new Course(
                name, code, null,
                new DayOfWeek[]{days[0]}, new LocalTime[]{starts[0]}, new LocalTime[]{ends[0]},
                null, 0
        ));

        dbHelper.addCourseTime(code, days[1], starts[1], ends[1]);
        assertEquals(dbHelper.getCourse(code), new Course(
                name, code, null,
                new DayOfWeek[]{days[0], days[1]},
                new LocalTime[]{starts[0], starts[1]},
                new LocalTime[]{ends[0], ends[1]},
                null, 0
        ));
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
        assertEquals(dbHelper.getCourse(code), new Course(
                name, code, null,
                new DayOfWeek[]{days[0], days[2]},
                new LocalTime[]{starts[0], starts[2]},
                new LocalTime[]{ends[0], ends[2]},
                null, 0
        ));

        try {
            dbHelper.deleteCourseTime(code, 10);
            fail();
        } catch (ArrayIndexOutOfBoundsException e){

        }

        dbHelper.deleteCourseTime(code, 0);
        assertEquals(dbHelper.getCourse(code), new Course(
                name, code, null,
                new DayOfWeek[]{days[2]},
                new LocalTime[]{starts[2]},
                new LocalTime[]{ends[2]},
                null, 0
        ));
    }

    @Test
    public void testChangeCourseDesc(){
        String name = "Test";
        String code = "TST 1010";
        String desc = "This is a test course.";

        dbHelper.addCourse(new Course(name, code));
        dbHelper.changeCourseDesc(code, desc);

        assertEquals(dbHelper.getCourse(code),
                new Course(
                        name, code, null,
                        new DayOfWeek[0], new LocalTime[0], new LocalTime[0],
                        desc, 0
                ));
    }

    @Test
    public void testChangeCapacity(){
        String name = "Test";
        String code = "TST 1010";
        int cap = 30;

        dbHelper.addCourse(new Course(name, code));
        dbHelper.changeCourseCapacity(code, cap);

        assertEquals(dbHelper.getCourse(code),
                new Course(
                        name, code, null,
                        new DayOfWeek[0], new LocalTime[0], new LocalTime[0],
                        null, cap
                ));
    }


}
