package com.pwr.bzapps.plwordnetmobile.settings;

import android.content.Context;
import android.content.SharedPreferences;

import android.os.Environment;
import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.language.LanguageManager;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;

public class Settings {

    private static SharedPreferences preferences;
    private static Context context;

    private static Locale locale;
    private static String localeName;
    private static int max_results_count = 50;
    private static boolean localeNeedsSync = false;
    private static boolean offlineMode = false;
    private static int app_files_location = 0;
    private static String sqlite_db_file_location;
    private static String db_type = "none";
    public static final int DEVICE_LOCATION = 0;
    public static final int SDCARD_LOCATION = 1;
    public static final String FILE_NAME= "plwordnet";
    public static final String[] POSSIBLE_DB_LANGS = {"none", "all", "polish", "english" };



    public static void loadSettings(Context ctx){
        context=ctx;
        if(preferences==null){
            preferences = context.getSharedPreferences(context.getResources().getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        }
        localeName = preferences.getString("selected_locale", "default");
        if(localeName.equals("default")){
            localeName = getMatchingLocale(Locale.getDefault().getLanguage());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("selected_locale", localeName);
            editor.commit();
        }
        offlineMode = preferences.getBoolean("offline_mode", false);
        app_files_location = preferences.getInt("app_files_location", 0);
        sqlite_db_file_location = preferences.getString("sqlite_db_file_location", changeSqliteDbFileLocation(app_files_location));
        db_type = preferences.getString("db_type", "none");
        locale = new Locale(localeName.substring(0,localeName.indexOf('_')));
    }

    public static void saveSettings(Context ctx){
        context=ctx;
        SharedPreferences.Editor editor = getEditor();
        editor.putString("selected_locale", localeName);
        editor.putBoolean("offline_mode",offlineMode);

        editor.commit();
    }

    private static String getMatchingLocale(String id){
        ArrayList<String> available = LanguageManager.getAvailableLanguages(context);
        for(String locale_full_id : available){
            if(locale_full_id.contains(id.toLowerCase() + "_")){
                return locale_full_id;
            }
        }
        return "en_EN";
    }

    public static void refreshLocale(){
        setLocale(localeName);
    }

    public static void setLocale(String localeName) {
        ArrayList<String> available = LanguageManager.getAvailableLanguages(context);
        if(!available.contains(localeName)){
            localeName = "en_EN";
        }
        Settings.localeName = localeName;
        locale = new Locale(localeName.substring(0,localeName.indexOf('_')));
        LanguageManager.setLanguage(context);
        localeNeedsSync = true;
    }

    public static String getLocaleName(){
        return localeName;
    }

    public static String getShortLocalName(){
        return localeName.substring(0,2);
    }

    public static Locale getLocale() {
        if(locale==null){
            loadSettings(context);
        }
        return locale;
    }

    public static Locale getLocale(Context context) {
        if(locale==null){
            loadSettings(context);
        }
        return locale;
    }

    public static boolean doesLocaleNeedsSync(){
        return localeNeedsSync;
    }

    public static void flagLocaleSync(){
        localeNeedsSync = true;
    }

    public static void unflagLocaleSync(){
        localeNeedsSync = false;
    }

    public static LinkedList<String> getSearchHistory(Context ctx){
        context=ctx;
        if(preferences==null){
            preferences = context.getSharedPreferences(context.getResources().getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        }
        String history = preferences.getString("search_history", "");
        LinkedList<String> historyList = new LinkedList<String>();
        for(String entry : history.split(";")){
            historyList.add(entry);
        }
        if("".equals(historyList.get(0))){
            historyList.remove(0);
        }
        return historyList;
    }

    public static void putSearchEntryToHistory(Context ctx, String entry){
        if("".equals(entry.trim())){
            return;
        }
        LinkedList<String> historyList = getSearchHistory(ctx);
        if(historyList.contains(entry.toLowerCase())){
            historyList.remove(historyList.indexOf(entry.toLowerCase()));
        }
        historyList.addFirst(entry.toLowerCase().trim());

        if(historyList.size()>=80){
            historyList.remove(historyList.size()-1);
        }

        String history = "";

        for(String i : historyList){
            history+=i+";";
        }
        if(!history.isEmpty()){
            history=history.substring(0,history.length()-1);
        }


        SharedPreferences.Editor editor = getEditor();
        editor.putString("search_history", history);
        editor.commit();
    }


    public static Integer[] getBookmarkedIds(Context ctx){
        context=ctx;
        if(preferences==null){
            preferences = context.getSharedPreferences(context.getResources().getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        }
        String[] bookmarked = preferences.getString("bookmarks", "").split(";");
        if(bookmarked.length==1 && bookmarked[0].isEmpty())
            return new Integer[0];
        Integer[] ids = new Integer[bookmarked.length];
        for(int i=0; i <ids.length; i++){
            ids[i]=Integer.parseInt(bookmarked[i]);
        }
        return ids;
    }

    public static boolean isSenseBookmarked(Context ctx, Integer id){
        Integer[] ids = getBookmarkedIds(ctx);
        return contains(ids,id);
    }

    public static void addOrRemoveBookmark(Context ctx, Integer id){
        Integer[] ids = getBookmarkedIds(ctx);
        String bookmarked = "";
        if(contains(ids,id)){
            for(int i=0; i<ids.length;i++){
                if(ids[i].intValue()!=id.intValue()){
                    bookmarked+=ids[i]+";";
                }
            }
            if(!bookmarked.isEmpty()){
                bookmarked=bookmarked.substring(0,bookmarked.length()-1);
            }
        }
        else{
            for(int i=0; i<ids.length;i++){
                bookmarked+=ids[i]+";";
            }
            bookmarked+=id;
        }

        SharedPreferences.Editor editor = getEditor();
        editor.putString("bookmarks", bookmarked);
        editor.commit();
    }

    private static boolean contains(Integer[] ids, Integer id){
        for(int i=0; i<ids.length; i++){
            if(ids[i].intValue()==id.intValue())
                return true;

        }
        return false;
    }

    public static void clearHistory(){
        SharedPreferences.Editor editor = getEditor();
        editor.putString("search_history", "");
        editor.commit();
    }

    public static void clearBookmarks(){
        SharedPreferences.Editor editor = getEditor();
        editor.putString("bookmarks", "");
        editor.commit();
    }

    public static boolean isOfflineMode() {
        return offlineMode;
    }

    public static void setOfflineMode(boolean offlineMode) {
        Settings.offlineMode = offlineMode;
    }

    public static String getSqliteDbFileLocation() {
        return sqlite_db_file_location;
    }

    public static String getSqliteDbFile() {
        return sqlite_db_file_location + "/" + FILE_NAME + "_" + db_type + ".db";
    }

    public static String changeSqliteDbFileLocation(int location){
        SharedPreferences.Editor editor = getEditor();
        if(location == SDCARD_LOCATION){
            sqlite_db_file_location = "/sdcard/plWordNetGO/";
            File file = new File(sqlite_db_file_location);
            file.mkdirs();
        }
        else{
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/plWordNetGO/");
            file.mkdirs();
            sqlite_db_file_location = file.getAbsolutePath();
        }
        editor.putString("sqlite_db_file_location", sqlite_db_file_location);
        editor.commit();
        return sqlite_db_file_location;
    }

    public static String getDbType() {
        return db_type;
    }

    public static String setDbType(String db_type) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString("db_type", db_type);
        editor.commit();
        Settings.db_type = db_type;
        return db_type;
    }
    
    private static SharedPreferences.Editor getEditor(){
        if(preferences==null){
            preferences = context.getSharedPreferences(context.getResources().getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = preferences.edit();
        return editor;
    }
}
