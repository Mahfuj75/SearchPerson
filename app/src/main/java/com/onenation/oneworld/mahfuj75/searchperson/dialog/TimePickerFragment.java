package com.onenation.oneworld.mahfuj75.searchperson.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;

import android.os.Bundle;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by mahfu on 1/8/2017.
 */

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int amPm = c.get(Calendar.AM_PM);
        TimePickerDialog dialog = new TimePickerDialog(getActivity(), this, hour, minute,false);

        // Create a new instance of TimePickerDialog and return it
        return dialog;
    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        ((TimePickerDialog.OnTimeSetListener) getActivity()).onTimeSet(view, hourOfDay, minute);

    }
}
