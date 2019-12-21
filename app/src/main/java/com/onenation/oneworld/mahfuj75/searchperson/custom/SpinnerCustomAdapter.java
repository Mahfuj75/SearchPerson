package com.onenation.oneworld.mahfuj75.searchperson.custom;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.onenation.oneworld.mahfuj75.searchperson.R;

/**
 * Created by mahfu on 3/7/2017.
 */

public class SpinnerCustomAdapter {

    public ArrayAdapter arrayListAdapterForSpinner(String [] locationList, Context context)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context.getApplicationContext(),
                R.layout.spinner_item, locationList) {

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                if (position % 2 == 0) { // we're on an even row
                    view.setBackgroundColor(Color.parseColor("#EEEEEE"));
                } else {
                    view.setBackgroundColor(Color.parseColor("#FFFFFF"));
                }
                return view;

            }

        };
        return adapter;
    }



}
