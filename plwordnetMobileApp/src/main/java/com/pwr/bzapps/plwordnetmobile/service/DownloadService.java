package com.pwr.bzapps.plwordnetmobile.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteDBFileManager;
import com.pwr.bzapps.plwordnetmobile.fragments.SettingsLocalDatabaseFragment;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class DownloadService extends IntentService {
    public static final int UPDATE_PROGRESS = 8344;
    public static final String SERVICE_NAME = "DownloadService";
    private boolean running = false;

    public DownloadService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent.getBooleanExtra("isChecking", false))
            return;
        String urlToDownload = getApplicationContext().getString(R.string.spring_interface_address) + "/db_controller/files?db_type=" + intent.getStringExtra("db_type");
        ResultReceiver receiver = (ResultReceiver) intent.getParcelableExtra("receiver");
        try {
            running = true;
            URL url = new URL(urlToDownload);
            URLConnection connection = url.openConnection();
            connection.connect();
            int fileLength = connection.getContentLength();

            File old_file = SQLiteDBFileManager.getInstance().getSqliteDbFile(Settings.getDbType());
            if(old_file.exists()){
                old_file.delete();
            }

            InputStream input = new BufferedInputStream(connection.getInputStream());
            OutputStream output = new FileOutputStream(
                    SQLiteDBFileManager.getInstance().getSqliteDbFile(Settings.getDbType()));

            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                Bundle resultData = new Bundle();
                resultData.putInt("progress" ,(int) (total * 100 / fileLength));
                resultData.putInt("status",4);
                receiver.send(UPDATE_PROGRESS, resultData);
                output.write(data, 0, count);
            }

            output.flush();
            output.close();
            input.close();
        } catch (IOException e) {
            Bundle resultData = new Bundle();
            resultData.putInt("status",5);
            receiver.send(UPDATE_PROGRESS, resultData);
            e.printStackTrace();
        }
        finally {
            running = false;
        }

        Bundle resultData = new Bundle();
        resultData.putInt("progress",100);
        receiver.send(UPDATE_PROGRESS, resultData);
    }

    public boolean isRunning() {
        return running;
    }
}