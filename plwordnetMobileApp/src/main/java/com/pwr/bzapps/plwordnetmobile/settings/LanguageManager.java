package com.pwr.bzapps.plwordnetmobile.settings;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;

import com.pwr.bzapps.plwordnetmobile.R;

import java.util.ArrayList;
import java.util.Locale;

public class LanguageManager {
    public static ArrayList<String> getAvailableLanguages(Context context){
        String[] list = context.getResources().getStringArray(R.array.locales_symbols);
        ArrayList<String> result = new ArrayList<String>();
        for(int i=0; i<list.length; i++){
            result.add(list[i]);
        }
        return result;
    }

    public static Context setLanguage(Context context){
        Resources res = context.getResources();
        Configuration config = res.getConfiguration();
        Locale locale = null;
        if(Build.VERSION.SDK_INT >= 24){
            locale = config.getLocales().get(0);
        }
        else {
            locale = config.locale;
        }
        if(!locale.getLanguage().equals(Settings.getLocaleName())){
            Locale.setDefault(Settings.getLocale(context));
            config.setLocale(Settings.getLocale(context));
            if (Build.VERSION.SDK_INT <= 17){
                context = context.createConfigurationContext(config);
            }
            else{
                config.locale = Settings.getLocale(context);
                res.updateConfiguration(config, res.getDisplayMetrics());
            }
        }
        return context;
    }
}
