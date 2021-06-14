package com.example.seg2105termproject;
/**
 * This file is part of Course Booking application for Android devices
 *
 * app/src/main/java/com/example/seg2105termproject
 *
 * University of Ottawa - Faculty of Engineering - SEG2105 -Course Booking application for Android devices
 * @author      Sally R       <@uottawa.ca> 
 *              Jerry S       <@uottawa.ca>
 *              Glen W        <@uottawa.ca>
 *              Youssef J     <yjall032@uottawa.ca>
 * 
*/
import android.content.Context;

import androidx.appcompat.app.AlertDialog;

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
}
