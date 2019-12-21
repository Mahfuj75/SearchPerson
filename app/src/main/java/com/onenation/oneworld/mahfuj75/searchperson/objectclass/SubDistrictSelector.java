package com.onenation.oneworld.mahfuj75.searchperson.objectclass;

import android.content.Context;

import com.onenation.oneworld.mahfuj75.searchperson.R;

import java.util.Objects;


/**
 * Created by mahfu on 11/18/2016.
 */

public class SubDistrictSelector {


    public SubDistrictSelector() {


    }

    public String[] GetLocation(Context context, String location) {
        String[] subLocationList;

        if (Objects.equals(location, "ঢাকা")) {
            subLocationList = context.getResources().getStringArray(R.array.d_dhaka);
        } else if (Objects.equals(location, "জামালপুর")) {
            subLocationList = context.getResources().getStringArray(R.array.d_jamalpur);

        } else if (Objects.equals(location, "ময়মনসিংহ")) {
            subLocationList = context.getResources().getStringArray(R.array.d_mymensingh);


        } else if (Objects.equals(location, "নেত্রকোনা")) {
            subLocationList = context.getResources().getStringArray(R.array.d_netrakona);


        } else if (Objects.equals(location, "শেরপুর")) {
            subLocationList = context.getResources().getStringArray(R.array.d_sherpur);

        } else if (Objects.equals(location, "কিশোরগঞ্জ")) {
            subLocationList = context.getResources().getStringArray(R.array.d_kisorgong);

        } else if (Objects.equals(location, "বরগুনা")) {
            subLocationList = context.getResources().getStringArray(R.array.d_borguna);


        } else if (Objects.equals(location, "বরিশাল")) {
            subLocationList = context.getResources().getStringArray(R.array.d_borisal);


        } else if (Objects.equals(location, "ভোলা")) {
            subLocationList = context.getResources().getStringArray(R.array.d_vola);

        } else if (Objects.equals(location, "ঝালকাঠি")) {
            subLocationList = context.getResources().getStringArray(R.array.d_jhalokati);

        } else if (Objects.equals(location, "পটুয়াখালী")) {
            subLocationList = context.getResources().getStringArray(R.array.d_potuakhale);


        } else if (Objects.equals(location, "পিরোজপুর")) {
            subLocationList = context.getResources().getStringArray(R.array.d_pirogpur);


        } else if (Objects.equals(location, "বান্দরবান")) {
            subLocationList = context.getResources().getStringArray(R.array.d_bandorban);

        } else if (Objects.equals(location, "ব্রাহ্মণবাড়ীয়া")) {
            subLocationList = context.getResources().getStringArray(R.array.d_bramhonbaria);

        } else if (Objects.equals(location, "চট্টগ্রাম")) {
            subLocationList = context.getResources().getStringArray(R.array.d_chittagong);

        } else if (Objects.equals(location, "কুমিল্লা")) {
            subLocationList = context.getResources().getStringArray(R.array.d_commila);


        } else if (Objects.equals(location, "চাঁদপুর")) {
            subLocationList = context.getResources().getStringArray(R.array.d_chadpur);

        } else if (Objects.equals(location, "ফরিদপুর")) {
            subLocationList = context.getResources().getStringArray(R.array.d_foridpur);

        } else if (Objects.equals(location, "গাজীপুর")) {
            subLocationList = context.getResources().getStringArray(R.array.d_gazipur);


        } else if (Objects.equals(location, "গোপালগঞ্জ")) {
            subLocationList = context.getResources().getStringArray(R.array.d_gopalgong);


        } else if (Objects.equals(location, "মাদারিপুর")) {
            subLocationList = context.getResources().getStringArray(R.array.d_madarypur);

        } else if (Objects.equals(location, "মানিকগঞ্জ")) {
            subLocationList = context.getResources().getStringArray(R.array.d_manikgong);

        } else if (Objects.equals(location, "মুন্সীগঞ্জ")) {
            subLocationList = context.getResources().getStringArray(R.array.d_munsygong);


        } else if (Objects.equals(location, "নারায়ণগঞ্জ")) {
            subLocationList = context.getResources().getStringArray(R.array.d_narayangong);


        } else if (Objects.equals(location, "নরসিংদী")) {
            subLocationList = context.getResources().getStringArray(R.array.d_norsingdy);

        } else if (Objects.equals(location, "রাজবাড়ী")) {
            subLocationList = context.getResources().getStringArray(R.array.d_rajbary);

        } else if (Objects.equals(location, "শরীয়তপুর")) {
            subLocationList = context.getResources().getStringArray(R.array.d_soriotpur);


        } else if (Objects.equals(location, "টাঙ্গাইল")) {
            subLocationList = context.getResources().getStringArray(R.array.d_tangail);


        } else if (Objects.equals(location, "বাগেরহাট")) {
            subLocationList = context.getResources().getStringArray(R.array.d_bagerhat);

        } else if (Objects.equals(location, "চুয়াডাঙ্গা")) {
            subLocationList = context.getResources().getStringArray(R.array.d_chuadanga);

        } else if (Objects.equals(location, "যশোর")) {
            subLocationList = context.getResources().getStringArray(R.array.d_josor);


        } else if (Objects.equals(location, "ঝিনাইদহ")) {
            subLocationList = context.getResources().getStringArray(R.array.d_jinaidaho);


        } else if (Objects.equals(location, "খুলনা")) {
            subLocationList = context.getResources().getStringArray(R.array.d_khulna);

        } else if (Objects.equals(location, "কুষ্টিয়া")) {
            subLocationList = context.getResources().getStringArray(R.array.d_kustia);

        } else if (Objects.equals(location, "মাগুরা")) {
            subLocationList = context.getResources().getStringArray(R.array.d_magura);

        } else if (Objects.equals(location, "মেহেরপুর")) {
            subLocationList = context.getResources().getStringArray(R.array.d_meherpur);

        } else if (Objects.equals(location, "নড়াইল")) {
            subLocationList = context.getResources().getStringArray(R.array.d_norile);

        } else if (Objects.equals(location, "সাতক্ষীরা")) {
            subLocationList = context.getResources().getStringArray(R.array.d_shatkhera);

        } else {

            subLocationList = context.getResources().getStringArray(R.array.all);


        }
        return subLocationList;
    }

}