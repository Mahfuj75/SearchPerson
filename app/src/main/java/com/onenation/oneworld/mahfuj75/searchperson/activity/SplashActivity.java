package com.onenation.oneworld.mahfuj75.searchperson.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    private String district;
    private String subDistrict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new BackgroundTask().execute();


    }


    public class BackgroundTask extends AsyncTask {

        Intent intent;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            intent = new Intent(getApplicationContext(), TabViewActivity.class);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            district = sharedPreferences.getString("district", "").trim();
            subDistrict = sharedPreferences.getString("sub_district", "").trim();


            if (district.equals("") || subDistrict.equals("")) {

                intent = new Intent(getApplicationContext(), LocationActivity.class);

            } else {

                intent = new Intent(getApplicationContext(), TabViewActivity.class);


            }
        }

        @Override
        protected Object doInBackground(Object[] params) {

            try {
                int SPLASH_TIME_OUT = 1000;
                Thread.sleep(SPLASH_TIME_OUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
//            Pass your loaded data here using Intent
//            intent.putExtra("data_key", "");
            startActivity(intent);
            finish();
        }
    }
}
