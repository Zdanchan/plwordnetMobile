package com.pwr.bzapps.plwordnetmobile.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.activities.global.GlobalValues;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteDBFileManager;
import com.pwr.bzapps.plwordnetmobile.database.access.task.CheckLocalSQLiteDBWithServerTask;
import com.pwr.bzapps.plwordnetmobile.service.DownloadService;
import com.pwr.bzapps.plwordnetmobile.service.receiver.DownloadReceiver;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

public class SettingsLocalDatabaseFragment extends Fragment {

    private LinearLayout container;
    private RelativeLayout choose_local_db_label, offline_mode, synchronize_button, remove_button, status_button;
    private Switch offline_mode_switch;
    private RelativeLayout polish_db, english_db;
    private CheckBox polish_checkbox, english_checkbox;
    private CheckLocalSQLiteDBWithServerTask checkLocalSQLiteDBWithServerTask;
    private Animation rotation_anim;
    private ProgressBar sync_progress;
    private ImageView sync_icon;
    private TextView status_value;
    private boolean buttons_enabled;
    private int status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //set the layout you want to display in First Fragment
        View view = inflater.inflate(R.layout.settings_database_fragment, container, false);
        buttons_enabled = true;
        offline_mode = view.findViewById(R.id.offline_mode);
        choose_local_db_label = view.findViewById(R.id.local_database);
        polish_db = view.findViewById(R.id.polish_dictionary);
        english_db = view.findViewById(R.id.english_dictionary);
        polish_checkbox = view.findViewById(R.id.polish_checkbox);
        english_checkbox = view.findViewById(R.id.english_checkbox);
        synchronize_button = view.findViewById(R.id.sync_databases);
        status_button = view.findViewById(R.id.status);
        remove_button = view.findViewById(R.id.remove_databases);
        offline_mode_switch = offline_mode.findViewById(R.id.offline_mode_switch);
        offline_mode_switch.setChecked(Settings.isOfflineMode());
        offline_mode_switch.setClickable(false);
        offline_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!buttons_enabled && Settings.isOfflineMode()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.sync_please_wait), Toast.LENGTH_LONG).show();

                }
                else{
                    offline_mode_switch.setChecked(!offline_mode_switch.isChecked());
                    Settings.setOfflineMode(offline_mode_switch.isChecked());
                    if(offline_mode_switch.isChecked())
                        enableLocalDBSettings();
                    else
                        disableLocalDBSettings();
                }

            }
        });
        polish_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttons_enabled) {
                    polish_checkbox.setChecked(!polish_checkbox.isChecked());
                    saveCheckedDictionaries();
                    refreshStatus();
                }
                else {
                    showInformationToast();
                }
            }
        });
        english_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttons_enabled){
                    english_checkbox.setChecked(!english_checkbox.isChecked());
                    saveCheckedDictionaries();
                    refreshStatus();
                }
                else {
                    showInformationToast();
                }
            }
        });
        loadCheckedDictionaries();
        sync_progress = (ProgressBar) view.findViewById(R.id.sync_progress_bar);
        sync_progress.setVisibility(View.GONE);
        sync_icon = (ImageView) view.findViewById(R.id.sync_databases_icon);
        status_value = view.findViewById(R.id.status_value);

        synchronize_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttons_enabled) {
                    if (requestPermissions()) {
                        synchronizeLocalDB();
                    }
                }
                else {
                    showInformationToast();
                }
            }
        });

        status_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttons_enabled) {
                    refreshStatus();
                }
                else {
                    showInformationToast();
                }
            }
        });

        remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttons_enabled) {
                    clearChecked();
                    SQLiteDBFileManager.removeLocalDB();
                    status_value.setText(R.string.status_no_local_db);
                    status_value.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorNoLocalDB));
                    setStatus(2);
                    Toast.makeText(getActivity(), getResources().getString(R.string.remove_local_db_toast), Toast.LENGTH_LONG).show();
                }
                else {
                    showInformationToast();
                }
            }
        });

        if(Settings.isOfflineMode())
            enableLocalDBSettings();
        else
            disableLocalDBSettings();

        if(DownloadReceiver.isInitialized()){
            if(DownloadReceiver.getInstance().isRunning()) {
                DownloadReceiver.getInstance().setSettingsLocalDatabaseFragment(this);
                DownloadReceiver.getInstance().setProgressBar(sync_progress);
                startSyncAction();
            }
        }
        else{
            refreshStatus();
        }

        return view;

    }

    public void refreshStatus(){
        checkLocalSQLiteDBWithServerTask =
                new CheckLocalSQLiteDBWithServerTask(this, getActivity().getApplicationContext());
        setStatus(3);
        checkLocalSQLiteDBWithServerTask.execute(Settings.getDbType());
    }

    public void startSyncAction(){
        setStatus(4);
        sync_progress.setVisibility(View.VISIBLE);
        status_value.setText(R.string.status_synchronizing);
        status_value.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorSynchronizing));
        if (rotation_anim == null) {
            rotation_anim = (Animation) AnimationUtils.loadAnimation(
                    getActivity().getApplicationContext(), R.anim.rotate_360_anim);
        }
        rotation_anim.setRepeatCount(Animation.INFINITE);
        sync_icon.startAnimation(rotation_anim);
        rotation_anim.setFillAfter(true);
        disableButtons();
    }

    public void stopSyncAction(){
        sync_progress.setVisibility(View.GONE);
        sync_icon.clearAnimation();
        enableButtons();
    }

    private void synchronizeLocalDB(){
        String local_db_langs = saveCheckedDictionaries();
        Settings.setDbType(local_db_langs);
        if(!local_db_langs.equals(Settings.POSSIBLE_DB_LANGS[0])) {
            if (status == 1 || status == 2) {
                startSyncAction();
                Intent intent = new Intent(getActivity(), DownloadService.class);
                intent.putExtra("db_type", Settings.getDbType());
                intent.putExtra("receiver", DownloadReceiver.getInstance(sync_progress, SettingsLocalDatabaseFragment.this));
                getActivity().startService(intent);
            }
        }
        else{
            Toast.makeText(getActivity(), getResources().getString(R.string.no_db_selected), Toast.LENGTH_LONG).show();
        }
    }

    private void clearChecked(){
        polish_checkbox.setChecked(false);
        english_checkbox.setChecked(false);
        saveCheckedDictionaries();
    }

    public boolean areButtonsEnabled(){
        return buttons_enabled;
    }

    public void disableButtons(){
        buttons_enabled = false;
        choose_local_db_label.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        ((TextView)choose_local_db_label.findViewById(R.id.database_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        polish_db.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        ((TextView)polish_db.findViewById(R.id.polish_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        english_db.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        ((TextView)english_db.findViewById(R.id.english_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        synchronize_button.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        ((TextView)synchronize_button.findViewById(R.id.sync_databases_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        remove_button.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        ((TextView)remove_button.findViewById(R.id.remove_databases_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
    }

    public void enableButtons(){
        buttons_enabled = true;
        choose_local_db_label.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.alpha));
        ((TextView)choose_local_db_label.findViewById(R.id.database_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorMainText));
        polish_db.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.alpha));
        ((TextView)polish_db.findViewById(R.id.polish_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorMainText));
        english_db.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.alpha));
        ((TextView)english_db.findViewById(R.id.english_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorMainText));
        synchronize_button.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.alpha));
        ((TextView)synchronize_button.findViewById(R.id.sync_databases_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorMainText));
        remove_button.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.alpha));
        ((TextView)remove_button.findViewById(R.id.remove_databases_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorMainText));
    }

    public void disableLocalDBSettings(){
        buttons_enabled = false;
        choose_local_db_label.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        ((TextView)choose_local_db_label.findViewById(R.id.database_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        polish_db.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        ((TextView)polish_db.findViewById(R.id.polish_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        english_db.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        ((TextView)english_db.findViewById(R.id.english_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        synchronize_button.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        ((TextView)synchronize_button.findViewById(R.id.sync_databases_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        remove_button.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        ((TextView)remove_button.findViewById(R.id.remove_databases_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        status_button.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        ((TextView)status_button.findViewById(R.id.status_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        ((TextView)status_button.findViewById(R.id.status_value)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorTextInactive));

    }

    public void enableLocalDBSettings(){
        buttons_enabled = true;
        choose_local_db_label.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.alpha));
        ((TextView)choose_local_db_label.findViewById(R.id.database_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorMainText));
        polish_db.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.alpha));
        ((TextView)polish_db.findViewById(R.id.polish_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorMainText));
        english_db.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.alpha));
        ((TextView)english_db.findViewById(R.id.english_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorMainText));
        synchronize_button.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.alpha));
        ((TextView)synchronize_button.findViewById(R.id.sync_databases_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorMainText));
        remove_button.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.alpha));
        ((TextView)remove_button.findViewById(R.id.remove_databases_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorMainText));
        status_button.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.alpha));
        ((TextView)status_button.findViewById(R.id.status_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorMainText));
        setStatus(status);
    }

    public void setStatus(int status){
        if(status!=3 && !SQLiteDBFileManager.doesLocalDBExists()){
            status = 2;
        }
        this.status = status;
        switch (status){
            case 0:
                status_value.setText(R.string.status_up_to_date);
                status_value.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorUpToDate));
                break;
            case 1:
                status_value.setText(R.string.status_needs_update);
                status_value.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorNeedsUpdate));
                break;
            case 2:
                status_value.setText(R.string.status_no_local_db);
                status_value.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorNoLocalDB));
                break;
            case 3:
                status_value.setText(R.string.status_checking);
                status_value.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorSynchronizing));
                break;
            case 4:
                status_value.setText(R.string.status_synchronizing);
                status_value.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorSynchronizing));
                break;
            case 5:
                status_value.setText(R.string.status_connection_problem);
                status_value.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorNoLocalDB));
                break;
        }
        if(!Settings.isOfflineMode()){
            status_value.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        }
    }

    private void loadCheckedDictionaries(){
        String db_type = Settings.getDbType();
        if(db_type.equals(Settings.POSSIBLE_DB_LANGS[1])){
            polish_checkbox.setChecked(true);
            english_checkbox.setChecked(true);
        }
        else if(db_type.equals(Settings.POSSIBLE_DB_LANGS[2])){
            polish_checkbox.setChecked(true);
        }
        else if(db_type.equals(Settings.POSSIBLE_DB_LANGS[3])){
            english_checkbox.setChecked(true);
        }
    }

    private String saveCheckedDictionaries(){
        if(polish_checkbox.isChecked() && english_checkbox.isChecked() ){
            return Settings.setDbType(Settings.POSSIBLE_DB_LANGS[1]);
        }
        else if(polish_checkbox.isChecked()){
            return Settings.setDbType(Settings.POSSIBLE_DB_LANGS[2]);
        }
        else if(english_checkbox.isChecked() ){
            return Settings.setDbType(Settings.POSSIBLE_DB_LANGS[3]);
        }
        return Settings.setDbType(Settings.POSSIBLE_DB_LANGS[0]);
    }

    private boolean requestPermissions(){
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                return true;
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        GlobalValues.PERMISSIONS_WRITE_EXTERNAL_STORAGE);
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case GlobalValues.PERMISSIONS_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    synchronizeLocalDB();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.requires_permissions), Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void showInformationToast(){
        if(status==4){
            Toast.makeText(getActivity(), getResources().getString(R.string.sync_please_wait), Toast.LENGTH_LONG).show();
        }
        else if(!Settings.isOfflineMode()){
            Toast.makeText(getActivity(), getResources().getString(R.string.unavailable_in_online), Toast.LENGTH_LONG).show();
        }
    }
}
