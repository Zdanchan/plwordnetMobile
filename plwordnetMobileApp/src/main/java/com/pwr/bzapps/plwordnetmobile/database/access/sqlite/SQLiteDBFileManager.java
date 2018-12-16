package com.pwr.bzapps.plwordnetmobile.database.access.sqlite;

import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.io.File;

public class SQLiteDBFileManager {

    public static Long getLocalDBLastModifiedTime(){
        File local_db = new File(Settings.getSqliteDbFileLocation());
        if(local_db.exists()){
            return local_db.lastModified();
        }
        return Long.MIN_VALUE;
    }

    public static void removeLocalDB(){
        File local_db = new File(Settings.getSqliteDbFileLocation());
        if(local_db.exists()){
            local_db.delete();
        }
    }
}
