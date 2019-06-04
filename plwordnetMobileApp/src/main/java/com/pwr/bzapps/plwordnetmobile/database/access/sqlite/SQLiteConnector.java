package com.pwr.bzapps.plwordnetmobile.database.access.sqlite;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.io.File;

public class SQLiteConnector {

    private static SQLiteLanguageDatabase database;
    private static Context context;

    public static SQLiteLanguageDatabase getDatabaseInstance() {
        return getDatabaseInstance(context);
    }

    public static SQLiteLanguageDatabase getDatabaseInstance(Context context) {
        if(database==null)
            reloadDatabaseInstance(context);
        return database;
    }

    public static SQLiteLanguageDatabase reloadDatabaseInstance(Context context){
        SQLiteLanguageDatabase database = Room.databaseBuilder(context,
                SQLiteLanguageDatabase.class, new File(Settings.getSqliteDbFile()).getAbsolutePath())
                .build();
        SQLiteConnector.database = database;
        return database;
    }

    public static void setContext(Context context) {
        SQLiteConnector.context = context;
    }
}
