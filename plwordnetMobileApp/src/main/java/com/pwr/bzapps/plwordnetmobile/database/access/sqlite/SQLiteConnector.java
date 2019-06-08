package com.pwr.bzapps.plwordnetmobile.database.access.sqlite;

import android.content.Context;
import com.activeandroid.ActiveAndroid;
import com.activeandroid.Cache;
import com.activeandroid.Configuration;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.io.File;

public class SQLiteConnector {

    private static Context context;

    public static void reloadDatabaseInstance(Context context){
//        if(Cache.isInitialized()) {
//            ActiveAndroid.dispose();
//        }
        SQLiteConnector.context=context;
        Configuration dbConfiguration =
                new Configuration.Builder(context)
                        .setDatabaseName(
                                SQLiteDBFileManager.getInstance().getSqliteDbFile(Settings.getDbType()).getAbsolutePath())
                        .create();
        ActiveAndroid.initialize(dbConfiguration);

    }

    public static void setContext(Context context) {
        SQLiteConnector.context = context;
    }
}
