package com.onenation.oneworld.mahfuj75.searchperson.custom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by mahfu on 3/7/2017.
 */

public class CustomDialogForInformation {

    public AlertDialog.Builder alart(Context context) {
        AlertDialog.Builder myAlert = new AlertDialog.Builder(context.getApplicationContext());
        myAlert.setTitle("Missing")
                .setMessage("Please check found or lost")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        myAlert.show();
        return myAlert;
    }
}
