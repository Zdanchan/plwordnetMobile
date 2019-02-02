package com.pwr.bzapps.plwordnetmobile.service.receiver;

import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.ProgressBar;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteDBFileManager;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.fragments.SettingsLocalDatabaseFragment;
import com.pwr.bzapps.plwordnetmobile.service.DownloadService;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

public class DownloadReceiver extends ResultReceiver {

    private SettingsLocalDatabaseFragment settingsLocalDatabaseFragment;
    private ProgressBar progressBar;
    private static DownloadReceiver instance = null;
    private boolean isRunning = false;
    private boolean finalized = false;

    private DownloadReceiver(Handler handler, ProgressBar progressBar, SettingsLocalDatabaseFragment settingsLocalDatabaseFragment) {
        super(handler);
        this.progressBar=progressBar;
        this.settingsLocalDatabaseFragment=settingsLocalDatabaseFragment;
    }

    public static DownloadReceiver getInstance(ProgressBar progressBar, SettingsLocalDatabaseFragment settingsLocalDatabaseFragment) {
        if(!isInitialized()){
            instance = new DownloadReceiver(new Handler(), progressBar,settingsLocalDatabaseFragment);
        }
        return instance;
    }

    public static DownloadReceiver getInstance(){
        return instance;
    }

    public static boolean isInitialized(){
        return instance!=null;
    }

    public boolean isRunning() {
        return isRunning;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);
        if (resultCode == DownloadService.UPDATE_PROGRESS) {
            int progress = resultData.getInt("progress");
            int status = resultData.getInt("status", -1);
            if(progress < 100){
                isRunning = true;
                finalized = false;
            }
            if(isRunning && settingsLocalDatabaseFragment.areButtonsEnabled()){
                settingsLocalDatabaseFragment.disableButtons();
            }
            if(progressBar!=null){
                if (Build.VERSION.SDK_INT >= 24)
                    progressBar.setProgress(progress,true);
                else
                    progressBar.setProgress(progress);
            }
            if(progress == 100){
                if(!finalized)
                    finalizeDownload();
            }
            if(status == 5){
                settingsLocalDatabaseFragment.setStatus(5);
            }
        }
    }

    private void finalizeDownload(){
        if(isLocalDBFine()){
            if(settingsLocalDatabaseFragment!=null) {
                settingsLocalDatabaseFragment.stopSyncAction();
                settingsLocalDatabaseFragment.setStatus(0);
            }
        }
        else {
            SQLiteDBFileManager.removeLocalDB(Settings.getDbType());
            if(settingsLocalDatabaseFragment!=null) {
                settingsLocalDatabaseFragment.setStatus(6);
                settingsLocalDatabaseFragment.stopSyncAction();
                settingsLocalDatabaseFragment.showWarningPopup();
            }
        }
        isRunning = false;
        finalized = true;
    }

    private boolean isLocalDBFine(){
        return isLocalDBFine(0);
    }

    private boolean isLocalDBFine(int retries){
        try {
            String checkQuery = "";
            String[] tables = {
                    SQLiteTablesConstNames.APPLICATION_LOCALISED_STRING_NAME,
                    SQLiteTablesConstNames.DICTIONARY_NAME,
                    SQLiteTablesConstNames.DOMAIN_NAME,
                    SQLiteTablesConstNames.LEXICON_NAME,
                    SQLiteTablesConstNames.EMOTIONAL_ANNOTATION_NAME,
                    SQLiteTablesConstNames.PART_OF_SPEECH_NAME,
                    SQLiteTablesConstNames.WORD_NAME,
                    SQLiteTablesConstNames.RELATION_TYPE_ALLOWED_LEXICON_NAME,
                    SQLiteTablesConstNames.RELATION_TYPE_ALLOWED_PART_OF_SPEECH_NAME,
                    SQLiteTablesConstNames.RELATION_TYPE_NAME,
                    SQLiteTablesConstNames.SENSE_ATTRIBUTE_NAME,
                    SQLiteTablesConstNames.SENSE_NAME,
                    SQLiteTablesConstNames.SENSE_EXAMPLE_NAME,
                    SQLiteTablesConstNames.SENSE_RELATION_NAME,
                    SQLiteTablesConstNames.SYNSET_ATTRIBUTE_NAME,
                    SQLiteTablesConstNames.SYNSET_NAME,
                    SQLiteTablesConstNames.SYNSET_EXAMPLE_NAME,
                    SQLiteTablesConstNames.SYNSET_RELATION_NAME
            };
            for (int i = 0; i < tables.length; i++) {
                checkQuery += "SELECT * FROM " + tables[i] + " LIMIT 1; ";
            }
            SQLiteConnector.getInstance().runQuery(checkQuery);
        } catch (SQLiteException e){
            if(retries<10)
                return isLocalDBFine(retries+1);
            return false;
        }
        return true;
    }

    public SettingsLocalDatabaseFragment getSettingsLocalDatabaseFragment() {
        return settingsLocalDatabaseFragment;
    }

    public void setSettingsLocalDatabaseFragment(SettingsLocalDatabaseFragment settingsLocalDatabaseFragment) {
        this.settingsLocalDatabaseFragment = settingsLocalDatabaseFragment;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }
}
