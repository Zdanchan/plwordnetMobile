package com.pwr.bzapps.plwordnetmobile.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.widget.Toast;
import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.activities.template.BackButtonActivity;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteDBFileManager;
import com.pwr.bzapps.plwordnetmobile.fragments.*;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

public class SettingsActivity extends BackButtonActivity implements FragmentChangeListener{
    private SettingsCategoriesFragment settingsCategoriesFragment;
    private SettingsLocalDatabaseFragment settingsLocalDatabaseFragment;
    private SettingsVisualFragment settingsVisualFragment;
    private SettingsHistoryAndBookmarksFragment settingsHistoryAndBookmarksFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_settings);
        super.onCreate(savedInstanceState);

        if (findViewById(R.id.settings_fragment_container) != null) {

            settingsCategoriesFragment = new SettingsCategoriesFragment();
            settingsLocalDatabaseFragment = new SettingsLocalDatabaseFragment();
            settingsVisualFragment = new SettingsVisualFragment();
            settingsHistoryAndBookmarksFragment = new SettingsHistoryAndBookmarksFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.settings_fragment_container, settingsCategoriesFragment, "SettingsCategoriesFragment").commit();
        }
    }

    public void onBackPressed() {
        Settings.saveSettings(getApplication());
        if (settingsCategoriesFragment != null && settingsCategoriesFragment.isVisible()) {
            if(!SQLiteDBFileManager.getInstance().getSqliteDbFile(Settings.getDbType()).exists()){
                Settings.setOfflineMode(false);
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.selected_unavailable), Toast.LENGTH_LONG).show();
            }
            if(!"none".equals(Settings.getDbType()))
                SQLiteConnector.reloadDatabaseInstance(getApplicationContext());
            else{
                Settings.setOfflineMode(false);
            }
            super.onBackPressed();
        }
        else if (settingsLocalDatabaseFragment != null && settingsLocalDatabaseFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.settings_fragment_container, settingsCategoriesFragment, "SettingsCategoriesFragment").commit();
        }
        else if (settingsVisualFragment != null && settingsVisualFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.settings_fragment_container, settingsCategoriesFragment, "SettingsCategoriesFragment").commit();
        }
        else if (settingsHistoryAndBookmarksFragment != null && settingsHistoryAndBookmarksFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.settings_fragment_container, settingsCategoriesFragment, "SettingsCategoriesFragment").commit();
        }
        else {

        }

    }

    @Override
    public <T> void replaceFragment(Class<T> fragment_class) {
        Fragment fragment = settingsCategoriesFragment;
        switch (fragment_class.getSimpleName()) {
            case "SettingsLocalDatabaseFragment":
                fragment = settingsLocalDatabaseFragment;
                break;
            case "SettingsVisualFragment":
                fragment = settingsVisualFragment;
                break;
            case "SettingsHistoryAndBookmarksFragment":
                fragment = settingsHistoryAndBookmarksFragment;
                break;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.settings_fragment_container, fragment, fragment.getClass().getSimpleName()).commit();
    }
}
