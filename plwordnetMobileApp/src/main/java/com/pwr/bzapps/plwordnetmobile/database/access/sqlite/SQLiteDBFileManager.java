package com.pwr.bzapps.plwordnetmobile.database.access.sqlite;

import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.io.File;
import java.sql.*;
import java.util.Collection;

public class SQLiteDBFileManager {
    public static Long getLocalDBLastModifiedTime(){
        File local_db = new File(Settings.getSqliteDbFile());
        if(local_db.exists()){
            return local_db.lastModified();
        }
        return Long.MIN_VALUE;
    }

    public static boolean doesLocalDBExists(String db_type){
        File local_db = new File(Settings.getSqliteDbFileLocation() + "/" + Settings.FILE_NAME + "_" + db_type + ".db");
        return local_db.exists();
    }

    public static boolean doesLocalDBExists(){
        File local_db = new File(Settings.getSqliteDbFile());
        return local_db.exists();
    }

    public static void removeLocalDB(String db_type){
        File local_db = new File(Settings.getSqliteDbFileLocation() + "/" + Settings.FILE_NAME + "_" + db_type + ".db");
        if(local_db.exists()){
            local_db.delete();
        }
    }

    public static void removeLocalDB(){
        Settings.loadPossibleDBLangs();
        String[] packs = Settings.POSSIBLE_DB_LANGS;

        for(int i=0; i<packs.length; i++){
            File local_db = new File(Settings.getSqliteDbFileLocation() + "/" + Settings.FILE_NAME + "_"
                    + packs[i] + ".db");
            if(local_db.exists()){
                local_db.delete();
            }
        }
    }
}
