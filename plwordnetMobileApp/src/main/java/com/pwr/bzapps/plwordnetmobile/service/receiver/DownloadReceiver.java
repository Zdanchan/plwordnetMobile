package com.pwr.bzapps.plwordnetmobile.service.receiver;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteDBFileManager;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application.ApplicationLocalisedStringDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application.DictionaryDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application.DomainDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application.LexiconDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.grammar.EmotionalAnnotationDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.grammar.PartOfSpeechDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.grammar.WordDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.relation.RelationTypeAllowedLexiconDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.relation.RelationTypeAllowedPartOfSpeechDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.relation.RelationTypeDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense.SenseAttributeDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense.SenseDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense.SenseExampleDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense.SenseRelationDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset.SynsetAttributeDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset.SynsetDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset.SynsetExampleDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset.SynsetRelationDAO;
import com.pwr.bzapps.plwordnetmobile.fragments.SettingsLocalDatabaseFragment;
import com.pwr.bzapps.plwordnetmobile.service.DownloadService;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

public class DownloadReceiver extends ResultReceiver {

    private SettingsLocalDatabaseFragment settingsLocalDatabaseFragment;
    private ProgressBar progressBar;
    private TextView percentageProgress;
    private static DownloadReceiver instance = null;
    private boolean isRunning = false;
    private boolean finalized = false;

    private DownloadReceiver(Handler handler,
                             ProgressBar progressBar,
                             TextView percentageProgress,
                             SettingsLocalDatabaseFragment settingsLocalDatabaseFragment) {
        super(handler);
        this.progressBar=progressBar;
        this.percentageProgress=percentageProgress;
        this.settingsLocalDatabaseFragment=settingsLocalDatabaseFragment;
    }

    public static DownloadReceiver getInstance(ProgressBar progressBar,
                                               TextView percentageProgress,
                                               SettingsLocalDatabaseFragment settingsLocalDatabaseFragment) {
        if(!isInitialized()){
            instance = new DownloadReceiver(new Handler(), progressBar, percentageProgress,settingsLocalDatabaseFragment);
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
            if (percentageProgress!=null){
                percentageProgress.setText(progress + "%");
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
            SQLiteDBFileManager.getInstance().removeLocalDB(Settings.getDbType());
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
//        return true;
    }

    private boolean isLocalDBFine(int retries){
        try {
            ApplicationLocalisedStringDAO.checkTable();
            DictionaryDAO.checkTable();
            DomainDAO.checkTable();
            LexiconDAO.checkTable();
            EmotionalAnnotationDAO.checkTable();
            PartOfSpeechDAO.checkTable();
            WordDAO.checkTable();
            RelationTypeAllowedLexiconDAO.checkTable();
            RelationTypeAllowedPartOfSpeechDAO.checkTable();
            RelationTypeDAO.checkTable();
            SenseAttributeDAO.checkTable();
            SenseDAO.checkTable();
            SenseExampleDAO.checkTable();
            SenseRelationDAO.checkTable();
            SynsetAttributeDAO.checkTable();
            SynsetDAO.checkTable();
            SynsetExampleDAO.checkTable();
            SynsetRelationDAO.checkTable();
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
