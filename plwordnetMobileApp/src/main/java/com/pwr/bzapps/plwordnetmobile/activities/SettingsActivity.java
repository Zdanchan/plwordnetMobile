package com.pwr.bzapps.plwordnetmobile.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;
import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.activities.notification.WarningPopup;
import com.pwr.bzapps.plwordnetmobile.activities.template.BackButtonActivity;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteDBFileManager;
import com.pwr.bzapps.plwordnetmobile.fragments.*;
import com.pwr.bzapps.plwordnetmobile.service.DownloadService;
import com.pwr.bzapps.plwordnetmobile.service.receiver.DownloadReceiver;
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
            if(!"none".equals(Settings.getDbType()))
                SQLiteConnector.reloadDatabaseInstance(getApplicationContext());
            else{
                Settings.setOfflineMode(false);
            }
            super.onBackPressed();
        }
        else if (settingsLocalDatabaseFragment != null && settingsLocalDatabaseFragment.isVisible()) {
            if(DownloadReceiver.isDownloadReceiverRunning()){
                final WarningPopup warningPopup =
                        new WarningPopup(this,
                                getLayoutInflater(),
                                getResources().getString(R.string.please_wait),
                                getResources().getString(R.string.currently_downloading),
                                getResources().getString(R.string.wait_button),
                                getResources().getString(R.string.cancel_button)
                                );
                warningPopup.addOnClickListener(0, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        warningPopup.dismiss();
                    }
                });
                warningPopup.addOnClickListener(1, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(DownloadReceiver.isInitialized())
                            DownloadReceiver.getInstance().cancel();
                        warningPopup.dismiss();
                    }
                });
                warningPopup.show();
            }
            else
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
                getSupportFragmentManager().beginTransaction().remove(settingsLocalDatabaseFragment).commit();
                settingsLocalDatabaseFragment = new SettingsLocalDatabaseFragment();
                fragment = settingsLocalDatabaseFragment;
                break;
            case "SettingsVisualFragment":
                getSupportFragmentManager().beginTransaction().remove(settingsVisualFragment).commit();
                settingsVisualFragment = new SettingsVisualFragment();
                fragment = settingsVisualFragment;
                break;
            case "SettingsHistoryAndBookmarksFragment":
                getSupportFragmentManager().beginTransaction().remove(settingsHistoryAndBookmarksFragment).commit();
                settingsHistoryAndBookmarksFragment = new SettingsHistoryAndBookmarksFragment();
                fragment = settingsHistoryAndBookmarksFragment;
                break;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.settings_fragment_container, fragment, fragment.getClass().getSimpleName()).commit();
    }
}
