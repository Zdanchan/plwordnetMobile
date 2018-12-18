package com.pwr.bzapps.plwordnetmobile.database.access.sqlite;

import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.io.File;

public class SQLiteDBFileManager {

    public static Long getLocalDBLastModifiedTime(){
        File local_db = new File(Settings.getSqliteDbFile());
        if(local_db.exists()){
            return local_db.lastModified();
        }
        return Long.MIN_VALUE;
    }

    public static boolean doesLocalDBExists(){
        File local_db = new File(Settings.getSqliteDbFile());
        return local_db.exists();
    }

    public static void removeLocalDB(){
        File pl_local_db = new File(Settings.getSqliteDbFileLocation() + Settings.FILE_NAME + "_"
                + Settings.POSSIBLE_DB_LANGS[0] + ".db");
        File en_local_db = new File(Settings.getSqliteDbFileLocation() + Settings.FILE_NAME + "_"
                + Settings.POSSIBLE_DB_LANGS[1] + ".db");
        File local_db = new File(Settings.getSqliteDbFileLocation() + Settings.FILE_NAME + "_"
                + Settings.POSSIBLE_DB_LANGS[2] + ".db");
        if(local_db.exists()){
            local_db.delete();
        }
        if(pl_local_db.exists()){
            pl_local_db.delete();
        }
        if(en_local_db.exists()){
            en_local_db.delete();
        }
    }
}
