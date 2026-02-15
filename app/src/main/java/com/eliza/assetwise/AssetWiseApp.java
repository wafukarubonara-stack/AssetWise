package com.eliza.assetwise;

import android.app.Application;
import android.util.Log;

/**
 * AssetWise Application class
 * アプリ全体の初期化を管理
 */
public class AssetWiseApp extends Application {

    private static final String TAG = "AssetWiseApp";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "AssetWise application started");
    }
}
