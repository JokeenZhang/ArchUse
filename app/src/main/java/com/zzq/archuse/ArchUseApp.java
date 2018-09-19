package com.zzq.archuse;

import android.app.Application;

public class ArchUseApp extends Application  {

    private static ArchUseApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static ArchUseApp getInstance() {
        return instance;
    }

}
