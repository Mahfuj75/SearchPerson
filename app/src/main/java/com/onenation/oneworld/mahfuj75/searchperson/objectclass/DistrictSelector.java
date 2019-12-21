package com.onenation.oneworld.mahfuj75.searchperson.objectclass;

import android.content.Context;

import com.onenation.oneworld.mahfuj75.searchperson.R;

import java.util.Objects;

/**
 * Created by mahfu on 1/7/2017.
 */

public class DistrictSelector {

    public DistrictSelector() {


    }


    public String[] GetLocation(Context context, String location)
    {
        String[] districtList ;

        if(Objects.equals(location, "ঢাকা"))
        {
            districtList = context.getResources().getStringArray(R.array.dhaka_division);
        }

        else if(Objects.equals(location, "রাজশাহী"))
        {
            districtList = context.getResources().getStringArray(R.array.rajshahi_division);

        }

        else if(Objects.equals(location, "চট্টগ্রাম"))
        {
            districtList = context.getResources().getStringArray(R.array.chittagong_division);

        }
        else if(Objects.equals(location, "খুলনা"))
        {
            districtList = context.getResources().getStringArray(R.array.khulna_division);


        }
        else if(Objects.equals(location, "ময়মনসিংহ"))
        {
            districtList = context.getResources().getStringArray(R.array.mymensingh_division);

        }
        else if(Objects.equals(location, "রংপুর"))
        {
            districtList = context.getResources().getStringArray(R.array.rangpur_division);

        }

        else if(Objects.equals(location, "সিলেট"))
        {
            districtList = context.getResources().getStringArray(R.array.sylhet_division);

        }
        else if(Objects.equals(location, "বরিশাল"))
        {
            districtList = context.getResources().getStringArray(R.array.barisal_division);
        }

        else{

            districtList = context.getResources().getStringArray(R.array.select);
        }
        return districtList;
    }
}
