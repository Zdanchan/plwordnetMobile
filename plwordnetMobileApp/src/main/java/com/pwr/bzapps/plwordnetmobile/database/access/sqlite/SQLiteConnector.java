package com.pwr.bzapps.plwordnetmobile.database.access.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.pwr.bzapps.plwordnetmobile.activities.MainActivity;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.io.File;
import java.sql.*;
import java.util.Collection;

public class SQLiteConnector extends SQLiteOpenHelper {

    private static SQLiteConnector instance;
    private static Context context;

    public SQLiteConnector(Context context) {
        super(context, new File(Settings.getSqliteDbFile()).getAbsolutePath(), null, 1);
    }

    public static SQLiteConnector getInstance() {
        if(instance==null)
            reloadInstance(context);
        return instance;
    }

    public static SQLiteConnector reloadInstance(Context context){
        SQLiteConnector.context = context;
        instance = new SQLiteConnector(context);
        return instance;
    }

    public <T extends Entity> T getResultForQuery(String query, Class<T> clazz){
        SQLiteDatabase db = this.getWritableDatabase();
        T result = null;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        result = SQLiteEntityWrapper.wrapWithEntity(cursor, clazz);
        cursor.close();
        return result;
    }

    public <T extends Entity> Collection<T> getResultListForQuery(String query, Class<T> clazz, int resultLimit){
        SQLiteDatabase db = this.getWritableDatabase();
        Collection<T> results = null;
        Cursor cursor = db.rawQuery(query, null);
        results = SQLiteEntityWrapper.wrapWithEntityCollection(cursor, clazz, resultLimit);
        cursor.close();
        return results;
    }

    public <T extends Entity> Collection<T> getResultListForQuery(String query, Class<T> clazz){
        SQLiteDatabase db = this.getWritableDatabase();
        Collection<T> results = null;
        Cursor cursor = db.rawQuery(query, null);
        results = SQLiteEntityWrapper.wrapWithEntityCollection(cursor, clazz);
        cursor.close();
        return results;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
