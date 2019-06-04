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
            SQLiteConnector.getDatabaseInstance().applicationLocalisedStringDAO().checkTable();
            SQLiteConnector.getDatabaseInstance().dictionaryDAO().checkTable();
            SQLiteConnector.getDatabaseInstance().domainDAO().checkTable();
            SQLiteConnector.getDatabaseInstance().lexiconDAO().checkTable();
            SQLiteConnector.getDatabaseInstance().emotionalAnnotationDAO().checkTable();
            SQLiteConnector.getDatabaseInstance().partOfSpeechDAO().checkTable();
            SQLiteConnector.getDatabaseInstance().wordDAO().checkTable();
            SQLiteConnector.getDatabaseInstance().relationTypeAllowedLexiconDAO().checkTable();
            SQLiteConnector.getDatabaseInstance().relationTypeAllowedPartOfSpeechDAO().checkTable();
            SQLiteConnector.getDatabaseInstance().relationTypeDAO().checkTable();
            SQLiteConnector.getDatabaseInstance().senseAttributeDAO().checkTable();
            SQLiteConnector.getDatabaseInstance().senseDAO().checkTable();
            SQLiteConnector.getDatabaseInstance().senseExampleDAO().checkTable();
            SQLiteConnector.getDatabaseInstance().senseRelationDAO().checkTable();
            SQLiteConnector.getDatabaseInstance().synsetAttributeDAO().checkTable();
            SQLiteConnector.getDatabaseInstance().synsetDAO().checkTable();
            SQLiteConnector.getDatabaseInstance().synsetExampleDAO().checkTable();
            SQLiteConnector.getDatabaseInstance().synsetRelationDAO().checkTable();
        } catch (Exception e){
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
