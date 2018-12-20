package com.migue.zeus.expensesnotes.infrastructure;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Cache {
    private Context appContext;
    private static Cache cache;
    private SharedPreferences sharedPreferencesSettings;
    public static final String SETTINGS = "settings";
    public static final String SETTINGS_LAST_SELECTED_FRAGMENT = "lastSelectedFragment";

    private SharedPreferences sharedPreferencesSync;
    public static final String SYNC = "sync";
    public static final String SYNC_LAST_SYNCED_ID = "lastSyncedId";
    public static final String SYNC_TIME = "syncTime";
    public static final String SYNC_EMAIL = "email";
    public static final String SYNC_USER_ID = "userId";
    public static final String SYNC_USERNAME = "username";



    private Cache(Context appContext) {
        sharedPreferencesSettings = appContext.getSharedPreferences(SETTINGS, Activity.MODE_PRIVATE);
        sharedPreferencesSync = appContext.getSharedPreferences(SYNC, Activity.MODE_PRIVATE);
        this.appContext = appContext;
    }

    //The context passed into the getInstance should be application level context.
    public static Cache getInstance(Context context) {
        if (cache == null) {
            cache = new Cache(context.getApplicationContext());
        }
        return cache;
    }


    public SharedPreferences getSettings() {
        return sharedPreferencesSettings;
    }

    public SharedPreferences getSyncInfo() {
        return sharedPreferencesSync;
    }
}
