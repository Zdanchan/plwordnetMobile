package com.pwr.bzapps.plwordnetmobile.database.access.task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.database.access.ConnectionProvider;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteDBFileManager;
import com.pwr.bzapps.plwordnetmobile.fragments.SettingsLocalDatabaseFragment;

public class CheckLocalSQLiteDBWithServerTask extends AsyncTask<String,Void,String> {

    private SettingsLocalDatabaseFragment fragment;
    private Context context;

    public CheckLocalSQLiteDBWithServerTask(SettingsLocalDatabaseFragment fragment, Context context){
        this.fragment=fragment;
        this.context=context;
    }

    @Override
    protected String doInBackground(String... strings) {
        if("none".equals(strings[0])){
            return "no-local-db";
        }
        Long result = ConnectionProvider.getInstance(context).getSQLiteLastUpdateOnServer(strings[0]);
        if(result==Long.MAX_VALUE){
            return "ConnectionException";
        }
        long last_update = SQLiteDBFileManager.getInstance(context).getLocalDBLastModifiedTime();
        if(last_update == Long.MIN_VALUE){
            return "no-local-db";
        }
        else if(result>last_update){
            return "needs-update";
        }

        return "up-to-date";
    }

    protected void onPostExecute(String result) {
        if(fragment!=null) {
            if ("ConnectionException".equals(result)) {
                fragment.setStatus(5);
            } else if ("up-to-date".equals(result)) {
                fragment.setStatus(0);
            } else if ("needs-update".equals(result)) {
                fragment.setStatus(1);
            } else if ("no-local-db".equals(result)) {
                fragment.setStatus(2);
            }
        }
    }
}