package com.onenation.oneworld.mahfuj75.searchperson.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.onenation.oneworld.mahfuj75.searchperson.R;
import com.onenation.oneworld.mahfuj75.searchperson.tabFragments.AllFragment;
import com.onenation.oneworld.mahfuj75.searchperson.tabFragments.CrimeFragment;
import com.onenation.oneworld.mahfuj75.searchperson.tabFragments.FoundFragment;
import com.onenation.oneworld.mahfuj75.searchperson.tabFragments.LocationFragment;
import com.onenation.oneworld.mahfuj75.searchperson.tabFragments.LostFragment;

public class TabViewActivity extends AppCompatActivity implements AllFragment.OnFragmentInteractionListener, FoundFragment.OnFragmentInteractionListener, LostFragment.OnFragmentInteractionListener
        , CrimeFragment.OnFragmentInteractionListener, LocationFragment.OnFragmentInteractionListener {


    private ViewPager mPager;
    private TabLayout mTabs;
    private String[] tabs;
    private FirebaseAuth mAuth;
    private String location = "abc";
    private String subLocation = "abc";
    private DatabaseReference mRef;
    private FloatingActionButton floatingActionButtonAddMissing;
    private FloatingActionButton floatingActionButtonAddComplain;
    private FloatingActionMenu floatingActionMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_view);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        tabs = getResources().getStringArray(R.array.tabs);
        mAuth = FirebaseAuth.getInstance();
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabs = (TabLayout) findViewById(R.id.tabs);
        mTabs.setupWithViewPager(mPager);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.floatingActionMenu);


        floatingActionButtonAddMissing = (FloatingActionButton) findViewById(R.id.fab11);
        floatingActionButtonAddComplain = (FloatingActionButton) findViewById(R.id.fab22);

        floatingActionButtonAddMissing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    Intent i = new Intent(getApplicationContext(), AddMissingPersonActivity.class);
                    floatingActionMenu.close(true);
                    startActivity(i);


                } else {
                    floatingActionMenu.close(true);
                    Snackbar.make(view, "Please create account , then add missing post", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });

        floatingActionButtonAddComplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddComplainActivity.class);
                floatingActionMenu.close(true);
                startActivity(i);
            }
        });


    }


    @Override
    protected void onRestart() {
        super.onRestart();
        floatingActionMenu.showMenu(true);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class MyPagerAdapter extends FragmentPagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {


            switch (position) {

                case 0:
                    return AllFragment.newInstance("First Fragment", "Instance 0");
                case 1:
                    return FoundFragment.newInstance("Second Fragment", "Instance 1");
                case 2:
                    return LostFragment.newInstance("Third Fragment", "Instance 2");
                case 3:
                    return CrimeFragment.newInstance("Forth Fragment", "Instance 3");
                case 4:
                    return LocationFragment.newInstance(location, subLocation);
                default:
                    return AllFragment.newInstance("ThirdFragment", "Default");
            }

        }

        @Override
        public CharSequence getPageTitle(int position) {

            return tabs[position];
        }

        @Override
        public int getCount() {
            return 5;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_missing_person, menu);

        MenuItem logout = menu.findItem(R.id.action_logout);
        MenuItem logIn = menu.findItem(R.id.action_login);
        final MenuItem userProfile = menu.findItem(R.id.action_user_profile);


        if (mAuth.getCurrentUser() == null) {
            logIn.setVisible(true);
            logout.setVisible(false);
            userProfile.setVisible(false);
        } else {
            logout.setVisible(true);
            userProfile.setVisible(true);
            logIn.setVisible(false);

        }


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_login) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        } else if (id == R.id.action_user_profile) {
            Intent i = new Intent(getApplicationContext(), UserAccountActivity.class);
            startActivity(i);
        } else if (id == R.id.action_logout) {

            mAuth.signOut();
            Intent i = new Intent(getApplicationContext(), TabViewActivity.class);
            startActivity(i);
            finish();


        } else if (id == R.id.action_change_location) {

            Intent i = new Intent(getApplicationContext(), LocationActivity.class);
            startActivity(i);

        }
        else if(id== R.id.action_settings)
        {
            Intent i = new Intent(getApplicationContext(),SettingsActivity.class);
            startActivity(i);

        }


        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

}
