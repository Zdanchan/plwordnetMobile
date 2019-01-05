package com.pwr.bzapps.plwordnetmobile.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.view.View;
import android.widget.RelativeLayout;
import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.activities.template.BackButtonActivity;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.task.CheckLocalSQLiteDBWithServerTask;
import com.pwr.bzapps.plwordnetmobile.fragments.*;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

public class SettingsActivity extends BackButtonActivity implements FragmentChangeListener{
    private SettingsCategoriesFragment settingsCategoriesFragment;
    private SettingsLocalDatabaseFragment settingsLocalDatabaseFragment;
    private SettingsVisualFragment settingsVisualFragment;
    private SettingsHistoryAndBookmarksFragment settingsHistoryAndBookmarksFragment;
    private RelativeLayout local_database, visual, history_and_bookmarks;

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
        if(!"none".equals(Settings.getDbType()))
            SQLiteConnector.reloadInstance(getApplicationContext());
        if (settingsCategoriesFragment != null && settingsCategoriesFragment.isVisible()) {
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
            //Whatever
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
