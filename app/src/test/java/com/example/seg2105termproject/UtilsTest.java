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
}
