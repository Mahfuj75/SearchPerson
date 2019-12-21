package com.onenation.oneworld.mahfuj75.searchperson.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Set the current date in the DatePickerFragment
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());


        // Create a new instance of DatePickerDialog and return it
        return  dialog;
    }

    // Callback to DatePickerActivity.onDateSet() to update the UI
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        ((DatePickerDialog.OnDateSetListener) getActivity()).onDateSet(view, year, monthOfYear, dayOfMonth);
    }
}