package com.pwr.bzapps.plwordnetmobile.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.activities.template.BackButtonActivity;
import com.pwr.bzapps.plwordnetmobile.language.LanguageAdapter;
import com.pwr.bzapps.plwordnetmobile.language.LanguageManager;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.util.ArrayList;

public class ChangeLanguageActivity extends BackButtonActivity implements AdapterView.OnItemClickListener{

    private ArrayList<String> languages;
    private ListView listView;
    private LanguageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_change_language);
        super.onCreate(savedInstanceState);

        languages = LanguageManager.getAvailableLanguages(getApplicationContext());
        listView = findViewById(R.id.languages_list_view);

        adapter = new LanguageAdapter(getApplicationContext(),languages);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Settings.saveSettings(getApplicationContext());
        Settings.flagLocaleSync();
        super.onBackPressed();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String[] locales = getApplicationContext().getResources().getStringArray(R.array.locales_symbols);
        Settings.setLocale(locales[i]);
        adapter.notifyDataSetChanged();
        onBackPressed();
    }
}
