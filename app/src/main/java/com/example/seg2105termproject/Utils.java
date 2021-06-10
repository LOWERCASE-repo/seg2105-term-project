package com.example.seg2105termproject;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class Utils {

    public static void createErrorDialog(Context context, int msgId){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msgId).setTitle(R.string.error);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
