package com.onenation.oneworld.mahfuj75.searchperson.objectclass;

import android.content.Context;

import com.onenation.oneworld.mahfuj75.searchperson.R;

import java.util.Objects;

/**
 * Created by mahfu on 1/15/2017.
 */

public class CityCorporationSelector {
    public CityCorporationSelector() {
    }

    public String[] GetLocation(Context context, String location)
    {
        String[] cityCorporationList ;

        if(Objects.equals(location, "ঢাকা"))
        {
            cityCorporationList = context.getResources().getStringArray(R.array.dhake_city_copporation);
        }

        else if(Objects.equals(location, "রাজশাহী"))
        {
            cityCorporationList = context.getResources().getStringArray(R.array.rajshahi_city_corporation);

        }

        else if(Objects.equals(location, "চট্টগ্রাম"))
        {
            cityCorporationList = context.getResources().getStringArray(R.array.chittagong_division);

        }
        else if(Objects.equals(location, "খুলনা"))
        {
            cityCorporationList = context.getResources().getStringArray(R.array.khulna_city_corporation);


        }
        else if(Objects.equals(location, "ময়মনসিংহ"))
        {
            cityCorporationList = context.getResources().getStringArray(R.array.select);

        }
        else if(Objects.equals(location, "রংপুর"))
        {
            cityCorporationList = context.getResources().getStringArray(R.array.rangpur_city_corporation);

        }

        else if(Objects.equals(location, "সিলেট"))
        {
            cityCorporationList = context.getResources().getStringArray(R.array.sylhet_city_corporation);



        }
        else if(Objects.equals(location, "বরিশাল"))
        {
            cityCorporationList = context.getResources().getStringArray(R.array.barisal_city_corporation);

        }

        else{

            cityCorporationList = context.getResources().getStringArray(R.array.select);
        }
        return cityCorporationList;
    }
}
