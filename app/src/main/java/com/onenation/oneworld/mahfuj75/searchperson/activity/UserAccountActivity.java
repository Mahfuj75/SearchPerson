package com.onenation.oneworld.mahfuj75.searchperson.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.onenation.oneworld.mahfuj75.searchperson.R;
import com.onenation.oneworld.mahfuj75.searchperson.user_fregments.AccountInformationFragment;
import com.onenation.oneworld.mahfuj75.searchperson.user_fregments.ComplainReportFragment;
import com.onenation.oneworld.mahfuj75.searchperson.user_fregments.LostFoundReportFragment;

public class UserAccountActivity extends AppCompatActivity implements AccountInformationFragment.OnFragmentInteractionListener, ComplainReportFragment.OnFragmentInteractionListener, LostFoundReportFragment.OnFragmentInteractionListener {

    private ViewPager mPager;
    private TabLayout mTabs;
    private String[] tabs;
    MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPager = (ViewPager) findViewById(R.id.pager_user);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(myPagerAdapter);
        mTabs = (TabLayout) findViewById(R.id.tabs_user);

        tabs = getResources().getStringArray(R.array.tabs_user);

        mTabs.setupWithViewPager(mPager);
    }


    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {


            switch (position) {

                case 0:
                    return AccountInformationFragment.newInstance("First Fragment", "Instance 0");
                case 1:
                    return LostFoundReportFragment.newInstance("Second Fragment", "Instance 1");
                case 2:
                    return ComplainReportFragment.newInstance("Third Fragment", "Instance 2");


                default:
                    return AccountInformationFragment.newInstance("First Fragment", "Default");
            }

        }

        @Override
        public CharSequence getPageTitle(int position) {

            return tabs[position];
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
