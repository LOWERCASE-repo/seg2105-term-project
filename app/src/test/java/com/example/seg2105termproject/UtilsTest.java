package com.example.seg2105termproject;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class UtilsTest {

    @Test
    public void testDaysToString(){
        DayOfWeek[] days = {DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY};

        String result = Utils.daysToString(days);

        String actual = "MONDAY,WEDNESDAY,FRIDAY,";

        assertEquals(result, actual);
    }

    @Test
    public void testTimesToString(){
        LocalTime[] times = {LocalTime.of(8, 30),
                LocalTime.of(10, 0),
                LocalTime.of(15, 45)};

        String result = Utils.timesToString(times);

        String actual = "08:30,10:00,15:45,";

        assertEquals(result, actual);
    }

    @Test
    public void testParseDays(){
        String days = "MONDAY,WEDNESDAY,FRIDAY,";

        DayOfWeek[] result = Utils.parseDays(days);

        DayOfWeek[] actual = {DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY};

        assertArrayEquals(result, actual);
    }

    @Test
    public void testParseTimes(){
        String times = "08:30,10:00,15:45,";

        LocalTime[] result = Utils.parseTimes(times);

        LocalTime[] actual = {LocalTime.of(8, 30),
                LocalTime.of(10, 0),
                LocalTime.of(15, 45)};

        assertArrayEquals(result, actual);
    }

    @Test
    public void testDaysTimesEmpty(){
        String strDays = Utils.daysToString(new DayOfWeek[]{});
        String strTimes = Utils.timesToString(new LocalTime[]{});
        DayOfWeek[] days = Utils.parseDays("");
        LocalTime[] times = Utils.parseTimes("");

        assertEquals(strDays, "");
        assertEquals(strTimes, "");
        assertArrayEquals(days, new DayOfWeek[]{});
        assertArrayEquals(times, new LocalTime[]{});
    }

    @Test
    public void testUserEquals(){
        Student Jimmy1 = new Student("Jimmy", "1423");
        Student Jimmy2 = new Student("Jimmy", "1423");
        Student Tommy = new Student("Tommy", "password");
        Instructor Jimmy3 = new Instructor("Jimmy", "1423");
        Instructor Jimmy4 = new Instructor("Jimmy", "1423");
        Admin admin = new Admin("admin", "admin123");
        Admin admin2 = new Admin("admin", "admin123");

        assertEquals(Jimmy1, Jimmy2);
        assertNotEquals(Jimmy1, Tommy);
        assertNotEquals(Jimmy1, Jimmy3);
        assertEquals(Jimmy3, Jimmy4);
        assertEquals(admin, admin2);
    }
}
