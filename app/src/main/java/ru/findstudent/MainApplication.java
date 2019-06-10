package ru.findstudent;

import android.app.Application;

import ru.findstudent.db.DbHelper;

public class MainApplication extends Application {
    public static final String TAG = "HW2";
    private static DbHelper sDbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        sDbHelper = new DbHelper(this);
    }

    public static synchronized DbHelper getDbHelper() {
        return sDbHelper;
    }
}
