package com.pwr.bzapps.plwordnetmobile.service.receiver;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.ProgressBar;
import com.pwr.bzapps.plwordnetmobile.fragments.SettingsLocalDatabaseFragment;
import com.pwr.bzapps.plwordnetmobile.service.DownloadService;

public class DownloadReceiver extends ResultReceiver {

    private SettingsLocalDatabaseFragment settingsLocalDatabaseFragment;
    private ProgressBar progressBar;
    private static DownloadReceiver instance = null;
    private boolean isRunning = false;

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
            }
            if(settingsLocalDatabaseFragment.areButtonsEnabled()){
                settingsLocalDatabaseFragment.disableButtons();
            }
            if(progressBar!=null){
                if (Build.VERSION.SDK_INT >= 24)
                    progressBar.setProgress(progress,true);
                else
                    progressBar.setProgress(progress);
            }
            if(progress == 100){
                settingsLocalDatabaseFragment.stopSyncAction();
                settingsLocalDatabaseFragment.setStatus(0);
                isRunning = false;
            }
            if(status == 5){
                settingsLocalDatabaseFragment.setStatus(5);
            }
        }
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
