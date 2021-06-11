package com.example.seg2105termproject;

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
