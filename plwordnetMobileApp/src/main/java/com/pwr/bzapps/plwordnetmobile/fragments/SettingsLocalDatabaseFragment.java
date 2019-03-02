package com.pwr.bzapps.plwordnetmobile.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.activities.SenseViewActivity;
import com.pwr.bzapps.plwordnetmobile.activities.global.GlobalValues;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteDBFileManager;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.adapter.SQLiteDictionaryAdapter;
import com.pwr.bzapps.plwordnetmobile.database.access.task.CheckLocalSQLiteDBWithServerTask;
import com.pwr.bzapps.plwordnetmobile.service.DownloadService;
import com.pwr.bzapps.plwordnetmobile.service.receiver.DownloadReceiver;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.util.ArrayList;

public class SettingsLocalDatabaseFragment extends Fragment {

    private LinearLayout container;
    private RelativeLayout choose_local_db_label, offline_mode, synchronize_button, remove_button, status_button;
    private Switch offline_mode_switch;
    private CheckLocalSQLiteDBWithServerTask checkLocalSQLiteDBWithServerTask;
    private Animation rotation_anim;
    private ProgressBar sync_progress;
    private ImageView sync_icon;
    private TextView status_value;
    private boolean buttons_enabled, isPopupUp = false;
    private int status;
    private SQLiteDictionaryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //set the layout you want to display in First Fragment
        View view = inflater.inflate(R.layout.settings_database_fragment, container, false);
        buttons_enabled = true;
        offline_mode = view.findViewById(R.id.offline_mode);
        choose_local_db_label = view.findViewById(R.id.local_database);
        synchronize_button = view.findViewById(R.id.sync_databases);
        this.container = (LinearLayout)view.findViewById(R.id.available_dictionaries);
        remove_button = view.findViewById(R.id.remove_databases);
        status_button = view.findViewById(R.id.status);
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
        prepareDictionarySelectors();
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
                    if(requestPermissions()) {
                        SQLiteDBFileManager.removeLocalDB();
                        status_value.setText(R.string.status_no_local_db);
                        status_value.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorNoLocalDB));
                        setStatus(2);
                        Toast.makeText(getActivity(), getResources().getString(R.string.remove_all_local_db_toast), Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();
                    }
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
        requestPermissions();
        return view;
    }

    private void prepareDictionarySelectors(){
        Settings.loadPossibleDBLangs();
        String[] packs = Settings.POSSIBLE_DB_LANGS;

        ArrayList<String> packs_list = new ArrayList<String>();
        for(int i=0; i<packs.length; i++){
            packs_list.add(packs[i]);
        }

        adapter = new SQLiteDictionaryAdapter(getActivity().getApplicationContext(),packs_list, this);
        for(int i=0; i<packs.length; i++){
            View view = adapter.getView(i);
            container.addView(view);
            final int index = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Settings.loadPossibleDBLangs();
                    String[] packs = Settings.POSSIBLE_DB_LANGS;
                    if(buttons_enabled) {
                        adapter.checkDictionaryByID(index);
                        saveCheckedDictionaries();
                        refreshStatus();
                    }
                    else {
                        showInformationToast();
                    }
                    Settings.setDbType(packs[index]);
                }
            });
        }
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
        adapter.notifyDataSetChanged();
    }

    private void synchronizeLocalDB(){
        String local_db_langs = saveCheckedDictionaries();
        Settings.setDbType(local_db_langs);
        if (status == 1 || status == 2) {
            startSyncAction();
            Intent intent = new Intent(getActivity(), DownloadService.class);
            intent.putExtra("db_type", Settings.getDbType());
            intent.putExtra("receiver", DownloadReceiver.getInstance(sync_progress, SettingsLocalDatabaseFragment.this));
            getActivity().startService(intent);
        }
    }

    public boolean areButtonsEnabled(){
        return buttons_enabled;
    }

    public void disableButtons(){
        buttons_enabled = false;
        choose_local_db_label.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        ((TextView)choose_local_db_label.findViewById(R.id.database_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        adapter.setBackgrounds(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        adapter.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        adapter.setIsEnabled(false);
        synchronize_button.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        ((TextView)synchronize_button.findViewById(R.id.sync_databases_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        remove_button.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        ((TextView)remove_button.findViewById(R.id.remove_databases_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
    }

    public void enableButtons(){
        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        buttons_enabled = true;
        choose_local_db_label.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.alpha));
        ((TextView)choose_local_db_label.findViewById(R.id.database_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorMainText));
        adapter.setBackgrounds(getActivity().getApplicationContext().getColor(R.color.alpha));
        adapter.setBackgroundsResource(outValue.resourceId);
        adapter.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorMainText));
        adapter.setIsEnabled(true);
        synchronize_button.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.alpha));
        synchronize_button.setBackgroundResource(outValue.resourceId);
        ((TextView)synchronize_button.findViewById(R.id.sync_databases_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorMainText));
        remove_button.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.alpha));
        remove_button.setBackgroundResource(outValue.resourceId);
        ((TextView)remove_button.findViewById(R.id.remove_databases_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorMainText));
    }

    public void disableLocalDBSettings(){
        buttons_enabled = false;
        choose_local_db_label.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        ((TextView)choose_local_db_label.findViewById(R.id.database_text)).setTextColor(getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        adapter.setBackgrounds(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        adapter.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        adapter.setIsEnabled(false);
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
        adapter.setBackgrounds(getActivity().getApplicationContext().getColor(R.color.alpha));
        adapter.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorMainText));
        adapter.setIsEnabled(true);
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
        if(getActivity()==null)
            return;
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
            case 6:
                status_value.setText(R.string.status_error);
                status_value.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorError));
                break;
        }
        if(!Settings.isOfflineMode()){
            status_value.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        }
    }

    public void showWarningPopup(){
        if(!isPopupUp) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getLayoutInflater();
            View convertView = (View) inflater.inflate(R.layout.warning_popup, null);
            Button ok_button = (Button) convertView.findViewById(R.id.ok_button);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView reason = (TextView) convertView.findViewById(R.id.reason);
            title.setText(R.string.download_error_title);
            reason.setText(R.string.download_error_content);
            ok_button.setText(R.string.ok_text);
            builder.setView(convertView);
            final AlertDialog dialog = builder.create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            isPopupUp = true;
            dialog.show();
            ok_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isPopupUp = false;
                    dialog.dismiss();
                }
            });
        }
    }

    private String saveCheckedDictionaries(){
        String selected = Settings.setDbType(adapter.getCheckedDictionary());
        return selected;
    }

    private boolean requestPermissions(){
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(getActivity(), getResources().getString(R.string.acquired_permissions), Toast.LENGTH_LONG).show();
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
