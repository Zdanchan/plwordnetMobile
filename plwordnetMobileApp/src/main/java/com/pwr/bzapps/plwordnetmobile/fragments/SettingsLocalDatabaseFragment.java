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
    private RelativeLayout chooseLocalDbLabel, offlineMode, synchronizeButton, removeButton, statusButton;
    private Switch offlineModeSwitch;
    private CheckLocalSQLiteDBWithServerTask checkLocalSQLiteDBWithServerTask;
    private Animation rotationAnim;
    private ProgressBar syncProgress;
    private ImageView syncIcon;
    private TextView statusValue, syncProgressPercentage;
    private boolean buttonsEnabled, isPopupUp = false;
    private int status;
    private SQLiteDictionaryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //set the layout you want to display in First Fragment
        View view = inflater.inflate(R.layout.settings_database_fragment, container, false);
        buttonsEnabled = true;
        offlineMode = view.findViewById(R.id.offline_mode);
        chooseLocalDbLabel = view.findViewById(R.id.local_database);
        synchronizeButton = view.findViewById(R.id.sync_databases);
        this.container = (LinearLayout)view.findViewById(R.id.available_dictionaries);
        removeButton = view.findViewById(R.id.remove_databases);
        statusButton = view.findViewById(R.id.status);
        offlineModeSwitch = offlineMode.findViewById(R.id.offline_mode_switch);
        offlineModeSwitch.setChecked(Settings.isOfflineMode());
        offlineModeSwitch.setClickable(false);
        offlineMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!buttonsEnabled && Settings.isOfflineMode()) {
                    Toast.makeText(getActivity(),
                            getResources().getString(R.string.sync_please_wait),
                            Toast.LENGTH_LONG)
                            .show();

                }
                else{
                    offlineModeSwitch.setChecked(!offlineModeSwitch.isChecked());
                    Settings.setOfflineMode(offlineModeSwitch.isChecked());
                    if(offlineModeSwitch.isChecked())
                        enableLocalDBSettings();
                    else
                        disableLocalDBSettings();
                }

            }
        });
        prepareDictionarySelectors();
        syncProgress = (ProgressBar) view.findViewById(R.id.sync_progress_bar);
        syncProgress.setVisibility(View.GONE);
        syncProgressPercentage = (TextView) view.findViewById(R.id.sync_progress_percentage);
        syncProgressPercentage.setVisibility(View.GONE);
        syncIcon = (ImageView) view.findViewById(R.id.sync_databases_icon);
        statusValue = view.findViewById(R.id.status_value);

        synchronizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonsEnabled) {
                    if (requestPermissions()) {
                        synchronizeLocalDB();
                    }
                }
                else {
                    showInformationToast();
                }
            }
        });

        statusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonsEnabled) {
                    refreshStatus();
                }
                else {
                    showInformationToast();
                }
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonsEnabled) {
                    if(requestPermissions()) {
                        SQLiteDBFileManager.getInstance(getActivity().getApplicationContext()).removeLocalDB();
                        statusValue.setText(R.string.status_no_local_db);
                        statusValue.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorNoLocalDB));
                        setStatus(2);
                        Toast.makeText(getActivity(),
                                getResources().getString(R.string.remove_all_local_db_toast),
                                Toast.LENGTH_LONG)
                                .show();
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
                DownloadReceiver.getInstance().setProgressBar(syncProgress);
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
                    if(buttonsEnabled) {
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
        syncProgress.setVisibility(View.VISIBLE);
        syncProgressPercentage.setVisibility(View.VISIBLE);
        statusValue.setText(R.string.status_synchronizing);
        statusValue.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorSynchronizing));
        if (rotationAnim == null) {
            rotationAnim = (Animation) AnimationUtils.loadAnimation(
                    getActivity().getApplicationContext(), R.anim.rotate_360_anim);
        }
        rotationAnim.setRepeatCount(Animation.INFINITE);
        syncIcon.startAnimation(rotationAnim);
        rotationAnim.setFillAfter(true);
        disableButtons();
    }

    public void stopSyncAction(){
        syncProgress.setVisibility(View.GONE);
        syncProgressPercentage.setVisibility(View.GONE);
        syncIcon.clearAnimation();
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
            intent.putExtra("receiver",
                    DownloadReceiver.getInstance(
                            syncProgress,
                            syncProgressPercentage,
                            SettingsLocalDatabaseFragment.this));
            getActivity().startService(intent);
        }
    }

    public boolean areButtonsEnabled(){
        return buttonsEnabled;
    }

    public void disableButtons(){
        buttonsEnabled = false;
        chooseLocalDbLabel.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        ((TextView) chooseLocalDbLabel.findViewById(R.id.database_text)).setTextColor(
                getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        adapter.setBackgrounds(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        adapter.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        adapter.setIsEnabled(false);
        synchronizeButton.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        ((TextView) synchronizeButton.findViewById(R.id.sync_databases_text)).setTextColor(
                getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        removeButton.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        ((TextView) removeButton.findViewById(R.id.remove_databases_text)).setTextColor(
                getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
    }

    public void enableButtons(){
        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        buttonsEnabled = true;
        chooseLocalDbLabel.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.alpha));
        ((TextView) chooseLocalDbLabel.findViewById(R.id.database_text)).setTextColor(
                getActivity().getApplicationContext().getColor(R.color.colorMainText));
        adapter.setBackgrounds(getActivity().getApplicationContext().getColor(R.color.alpha));
        adapter.setBackgroundsResource(outValue.resourceId);
        adapter.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorMainText));
        adapter.setIsEnabled(true);
        synchronizeButton.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.alpha));
        synchronizeButton.setBackgroundResource(outValue.resourceId);
        ((TextView) synchronizeButton.findViewById(R.id.sync_databases_text)).setTextColor
                (getActivity().getApplicationContext().getColor(R.color.colorMainText));
        removeButton.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.alpha));
        removeButton.setBackgroundResource(outValue.resourceId);
        ((TextView) removeButton.findViewById(R.id.remove_databases_text)).setTextColor(
                getActivity().getApplicationContext().getColor(R.color.colorMainText));
    }

    public void disableLocalDBSettings(){
        buttonsEnabled = false;
        chooseLocalDbLabel.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        ((TextView) chooseLocalDbLabel.findViewById(R.id.database_text)).setTextColor(
                getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        adapter.setBackgrounds(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        adapter.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        adapter.setIsEnabled(false);
        synchronizeButton.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        ((TextView) synchronizeButton.findViewById(R.id.sync_databases_text)).setTextColor(
                getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        removeButton.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        ((TextView) removeButton.findViewById(R.id.remove_databases_text)).setTextColor(
                getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        statusButton.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.colorInactive));
        ((TextView) statusButton.findViewById(R.id.status_text)).setTextColor(
                getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
        ((TextView) statusButton.findViewById(R.id.status_value)).setTextColor(
                getActivity().getApplicationContext().getColor(R.color.colorTextInactive));

    }

    public void enableLocalDBSettings(){
        buttonsEnabled = true;
        chooseLocalDbLabel.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.alpha));
        ((TextView) chooseLocalDbLabel.findViewById(R.id.database_text)).setTextColor(
                getActivity().getApplicationContext().getColor(R.color.colorMainText));
        adapter.setBackgrounds(getActivity().getApplicationContext().getColor(R.color.alpha));
        adapter.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorMainText));
        adapter.setIsEnabled(true);
        synchronizeButton.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.alpha));
        ((TextView) synchronizeButton.findViewById(R.id.sync_databases_text)).setTextColor(
                getActivity().getApplicationContext().getColor(R.color.colorMainText));
        removeButton.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.alpha));
        ((TextView) removeButton.findViewById(R.id.remove_databases_text)).setTextColor(
                getActivity().getApplicationContext().getColor(R.color.colorMainText));
        statusButton.setBackgroundColor(getActivity().getApplicationContext().getColor(R.color.alpha));
        ((TextView) statusButton.findViewById(R.id.status_text)).setTextColor(
                getActivity().getApplicationContext().getColor(R.color.colorMainText));
        setStatus(status);
    }

    public void setStatus(int status){
        if(getActivity()==null)
            return;
        if(status!=3 && !SQLiteDBFileManager.getInstance(getActivity().getApplicationContext()).doesLocalDBExists()){
            status = 2;
        }
        this.status = status;
        switch (status){
            case 0:
                statusValue.setText(R.string.status_up_to_date);
                statusValue.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorUpToDate));
                break;
            case 1:
                statusValue.setText(R.string.status_needs_update);
                statusValue.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorNeedsUpdate));
                break;
            case 2:
                statusValue.setText(R.string.status_no_local_db);
                statusValue.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorNoLocalDB));
                break;
            case 3:
                statusValue.setText(R.string.status_checking);
                statusValue.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorSynchronizing));
                break;
            case 4:
                statusValue.setText(R.string.status_synchronizing);
                statusValue.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorSynchronizing));
                break;
            case 5:
                statusValue.setText(R.string.status_connection_problem);
                statusValue.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorNoLocalDB));
                break;
            case 6:
                statusValue.setText(R.string.status_error);
                statusValue.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorError));
                break;
        }
        if(!Settings.isOfflineMode()){
            statusValue.setTextColor(getActivity().getApplicationContext().getColor(R.color.colorTextInactive));
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
                Toast.makeText(getActivity(),
                        getResources().getString(R.string.acquired_permissions),
                        Toast.LENGTH_LONG)
                        .show();
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
                    Toast.makeText(getActivity(),
                            getResources().getString(R.string.requires_permissions),
                            Toast.LENGTH_LONG)
                            .show();
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
