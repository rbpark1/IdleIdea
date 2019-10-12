package com.robbypark.android.idleidea;

import android.app.Application;
import android.content.Context;

// Gives access to Context globally.
// See AndroidManifest.xml
// Used by IdeaDataSource to pass Context to SQLiteHelper
public class MyApp extends Application {
    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance;
        // or return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
