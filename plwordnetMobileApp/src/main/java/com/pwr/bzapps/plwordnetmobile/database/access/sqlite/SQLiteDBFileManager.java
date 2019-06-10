package com.pwr.bzapps.plwordnetmobile.database.access.sqlite;

import android.content.Context;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.io.File;

public class SQLiteDBFileManager {
    public static final String FILE_NAME= "plwordnet";

    private static Context context;
    private static SQLiteDBFileManager instance;

    private SQLiteDBFileManager(Context context){
        this.context=context;
    }

    public static SQLiteDBFileManager getInstance(Context context) {
        if (instance==null)
            instance = new SQLiteDBFileManager(context);
        return instance;
    }
    public static SQLiteDBFileManager getInstance() {
        if (instance==null)
            instance = new SQLiteDBFileManager(context);
        return instance;
    }

    public File getSqliteDbFile(String db_type) {
        return context.getDatabasePath(FILE_NAME + "_" + db_type);
    }

    public Long getLocalDBLastModifiedTime(){
        File local_db = getSqliteDbFile(Settings.getDbType());
        if(local_db.exists()){
            return local_db.lastModified();
        }
        return Long.MIN_VALUE;
    }

    public String getLocalDBSizeString(String db_type){
        File local_db = getSqliteDbFile(db_type);
        String size_string = "0 mb";
        if(doesLocalDBExists(db_type)) {
            double size = (double) (local_db.length() / (1024 * 1024));
            if(size>1024){
                size = (double) (local_db.length() / 1024);
                size_string = String.format( "%.2f", size ) + " Gb";
            }
            else
                size_string = String.format( "%.2f", size ) + " mb";

            return size_string;
        }
        else
            return size_string;
    }

    public int getLocalDBSize(String db_type){
        File local_db = getSqliteDbFile(db_type);
        if(doesLocalDBExists(db_type))
            return (int) (local_db.length() / (1024 * 1024));
        else
            return 0;
    }

    public boolean doesLocalDBExists(String db_type){
        File local_db = getSqliteDbFile(db_type);
        return local_db.exists();
    }

    public boolean doesLocalDBExists(){
        File local_db = getSqliteDbFile(Settings.getDbType());
        return local_db.exists();
    }

    public void removeLocalDB(String db_type){
        File local_db = getSqliteDbFile(db_type);
        if(local_db.exists()){
            local_db.delete();
        }
    }

    public void removeLocalDB(){
        Settings.loadPossibleDBLangs();
        String[] packs = Settings.POSSIBLE_DB_LANGS;

        for(int i=0; i<packs.length; i++){
            File local_db = getSqliteDbFile(packs[i]);
            if(local_db.exists()){
                local_db.delete();
            }
        }
    }

    public static void setContext(Context context) {
        SQLiteDBFileManager.context = context;
    }
}
