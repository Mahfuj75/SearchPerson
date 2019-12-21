package com.onenation.oneworld.mahfuj75.searchperson.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.onenation.oneworld.mahfuj75.searchperson.R;
import com.onenation.oneworld.mahfuj75.searchperson.objectclass.SubDistrictSelector;
import com.onenation.oneworld.mahfuj75.searchperson.custom.SpinnerCustomAdapter;

public class LocationActivity extends AppCompatActivity {

    private Spinner spinnerSubLocation;
    private Spinner spinnerLocation;
    private Button selectButtonLocation;
    private String[] subLocationList;
    private String[] locationList;


    private SharedPreferences sharedPreferences;
    private String district;
    private String subDistrict;

    private int selection = 0;
    private ArrayAdapter<String> subAdapter;


    private SubDistrictSelector subDistrictSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        locationList = getResources().getStringArray(R.array.district_bangladesh);
        subDistrictSelector = new SubDistrictSelector();


        spinnerLocation = (Spinner) findViewById(R.id.spinnerLocation);
        spinnerSubLocation = (Spinner) findViewById(R.id.spinnerSubLocation);
        selectButtonLocation = (Button) findViewById(R.id.locationSelectButton);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        district = sharedPreferences.getString("district", "").trim();
        subDistrict = sharedPreferences.getString("sub_district", "").trim();

        spinnerLocation.setAdapter(new SpinnerCustomAdapter().arrayListAdapterForSpinner(locationList,getApplicationContext()));


        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String location = spinnerLocation.getSelectedItem().toString().trim();
                //Toast.makeText(getApplicationContext(), location,Toast.LENGTH_LONG).show();

                subLocationList = subDistrictSelector.GetLocation(getApplicationContext(), location);
                spinnerSubLocation.setAdapter(new SpinnerCustomAdapter().arrayListAdapterForSpinner(subLocationList,getApplicationContext()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        for (int i = 0; i < locationList.length; i++) {
            if (locationList[i].equals(district)) {
                spinnerLocation.setSelection(i);

                subLocationList = subDistrictSelector.GetLocation(getApplicationContext(), spinnerLocation.getSelectedItem()
                        .toString().trim());

                spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (subDistrict.length() != 0) {
                            for (int j = 0; j < subLocationList.length; j++) {
                                if (subLocationList[j].equals(subDistrict.trim())) {

                                    spinnerSubLocation.setAdapter(new SpinnerCustomAdapter().arrayListAdapterForSpinner(subLocationList,getApplicationContext()));

                                    selection = j;
                                    spinnerSubLocation.setSelection(j);
                                    Toast.makeText(getApplicationContext(), selection + subDistrict, Toast.LENGTH_LONG).show();
                                }
                            }


                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                //subLocationSelect(subLocationList);


            }
        }

        selectButtonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferencesLocationInfo = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferencesLocationInfo.edit();
                editor.putString("district", spinnerLocation.getSelectedItem().toString());
                editor.putString("sub_district", spinnerSubLocation.getSelectedItem().toString());
                Toast.makeText(getApplicationContext(), spinnerLocation.getSelectedItem().toString() + " ," + spinnerSubLocation.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                editor.commit();

                Intent i = new Intent(getApplicationContext(), TabViewActivity.class);
                startActivity(i);
                finish();

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void subLocationSelect(final String[] subLocationList) {


        //Toast.makeText(getApplicationContext(),selection,Toast.LENGTH_LONG).show();


        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {





            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
        if (subDistrict.length() == 0) {
            spinnerSubLocation.setSelection(0);
        }


    }


}
