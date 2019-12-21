package com.onenation.oneworld.mahfuj75.searchperson;

import android.app.Application;
import android.os.SystemClock;

import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

/**
 * Created by mahfu on 11/16/2016.
 */

public class SearchPerson extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
