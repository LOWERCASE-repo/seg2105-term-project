package com.example.seg2105termproject;

import android.content.Context;
import androidx.appcompat.app.AlertDialog;

import java.lang.reflect.Array;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;

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
public class Utils {

    /**
     * Construct and show a basic message dialog, describing an error encountered and that
     * the action failed.
     * @param context   The context (activity) the message is called from.
     * @param msgId     The string resource id (R.string.______) of the desired message.
     */
    public static void createErrorDialog(Context context, int msgId){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msgId).setTitle(R.string.error);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Converts an array of DayOfWeek (an enumeration of the days of the week) into
     * a formatted string.
     * Ignores null cases in the array.
     * @param days  The array of the DayOfWeek enum.
     * @return  A string in the format of "-DAY,-DAY,...,-DAY,"
     */
    public static String daysToString(DayOfWeek[] days) {
        if (days == null){
            return "";
        }

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < days.length; i++){

            // Ignore null cases.
            if (days[i] != null){
                buffer.append(days[i].toString());
                buffer.append(",");
            }
        }

        return buffer.toString();
    }

    /**
     * Converts an array of LocalTime objects (an object representing a time of the day)
     * into a formatted string.
     * Ignores null cases in the array.
     * @param times The array of LocalTime objects.
     * @return  A string in the format of "HH:mm,HH:mm,...,HH:mm,"
     *          (in other words, in ISO_LOCAL_TIME format with commas after each entry).
     */
    public static String timesToString(LocalTime[] times){
        if (times == null){
            return "";
        }

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < times.length; i++){

            // Ignore null cases.
            if (times[i] != null){
                buffer.append(times[i].toString());
                buffer.append(",");
            }
        }

        return buffer.toString();
    }

    /**
     * Parses a string, formatted from Utils.daysToString, and returns the equivalent
     * array of DayOfWeek enums.
     * @param strDays   The string formatted as "-DAY,-DAY,...,-DAY,".
     * @return  The DayOfWeek array that is equivalent to the days of the week
     *          listed in the string.
     */
    public static DayOfWeek[] parseDays(String strDays){
        if (strDays == null){
            return new DayOfWeek[0];
        }

        int pointer = 0;
        ArrayList<DayOfWeek> days = new ArrayList<DayOfWeek>();

        for (int i = 0; i < strDays.length(); i++){
            if (strDays.charAt(i) == ','){
                days.add(DayOfWeek.valueOf(strDays.substring(pointer, i)));
                pointer = i + 1;
            }
        }

        return days.toArray(new DayOfWeek[]{});
    }

    /**
     * Parses a string, formatted from Utils.timesToString, and returns the equivalent
     * array of LocalTime objects.
     * @param strTimes  The string formatted as "HH:mm,HH:mm,...,HH:mm,".
     * @return  The LocalTime array that is equivalent to the times of the day
     *          listed in the string.
     */
    public static LocalTime[] parseTimes(String strTimes){
        if (strTimes == null){
            return new LocalTime[0];
        }

        int pointer = 0;
        ArrayList<LocalTime> times = new ArrayList<LocalTime>();

        for (int i = 0; i < strTimes.length(); i++){
            if (strTimes.charAt(i) == ','){
                times.add(LocalTime.parse(strTimes.subSequence(pointer, i)));
                pointer = i + 1;
            }
        }

        return times.toArray(new LocalTime[]{});
    }
}
