package com.eliza.assetwise;

import android.app.Application;
import android.util.Log;

import com.eliza.assetwise.data.local.AssetWiseDatabase;

public class AssetWiseApp extends Application {

    private static final String TAG = "AssetWiseApp";
    private AssetWiseDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "AssetWise application started");
        database = AssetWiseDatabase.getDatabase(this);
        Log.d(TAG, "Database initialized");
    }

    public AssetWiseDatabase getDatabase() {
        return database;
    }
}
