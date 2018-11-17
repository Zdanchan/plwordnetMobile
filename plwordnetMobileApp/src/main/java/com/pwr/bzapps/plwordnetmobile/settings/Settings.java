package com.pwr.bzapps.plwordnetmobile.settings;

import android.content.Context;
import android.content.SharedPreferences;

import com.pwr.bzapps.plwordnetmobile.R;

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

    public static void loadSettings(Context ctx){
        context=ctx;
        if(preferences==null){
            preferences = context.getSharedPreferences(context.getResources().getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        }
        localeName = preferences.getString("selected_locale", "default");
        if(localeName.equals("default")){
            ArrayList<String> available = LanguageManager.getAvailableLanguages(context);
            if(!available.contains(localeName)){
                localeName = "en_US";
            }
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("selected_locale", localeName);
            editor.commit();
        }
        locale = new Locale(localeName.substring(0,localeName.indexOf('_')));
    }

    public static void saveSettings(Context ctx){
        context=ctx;
        SharedPreferences.Editor editor = preferences.edit();
        if(preferences==null){
            preferences = context.getSharedPreferences(context.getResources().getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        }
        editor.putString("selected_locale", localeName);

        editor.commit();
    }

    public static void refreshLocale(){
        setLocale(localeName);
    }

    public static void setLocale(String localeName) {
        ArrayList<String> available = LanguageManager.getAvailableLanguages(context);
        if(!available.contains(localeName)){
            localeName = "en_US";
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
        historyList.addFirst(entry.toLowerCase());

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


        SharedPreferences.Editor editor = preferences.edit();
        if(preferences==null){
            preferences = context.getSharedPreferences(context.getResources().getString(R.string.preference_file_key),Context.MODE_PRIVATE);
        }
        editor.putString("search_history", history);
        editor.commit();
    }

    public static boolean isOfflineMode() {
        return offlineMode;
    }

    public static void setOfflineMode(boolean offlineMode) {
        Settings.offlineMode = offlineMode;
    }
}
