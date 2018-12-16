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
    private RelativeLayout status_view;
    private Context context;

    public CheckLocalSQLiteDBWithServerTask(RelativeLayout status_view, SettingsLocalDatabaseFragment fragment, Context context){
        this.status_view=status_view;
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
        long last_update = SQLiteDBFileManager.getLocalDBLastModifiedTime();
        if(last_update == Long.MIN_VALUE){
            return "no-local-db";
        }
        else if(result>last_update){
            return "needs-update";
        }

        return "up-to-date";
    }

    protected void onPostExecute(String result) {
        if(status_view != null){
            TextView value = status_view.findViewById(R.id.status_value);
            if("ConnectionException".equals(result)) {
                value.setText(R.string.status_connection_problem);
                value.setTextColor(context.getColor(R.color.colorConnectionProblem));
                fragment.setStatus(3);
            }
            else if("up-to-date".equals(result)) {
                value.setText(R.string.status_up_to_date);
                value.setTextColor(context.getColor(R.color.colorUpToDate));
                fragment.setStatus(0);
            }
            else if("needs-update".equals(result)) {
                value.setText(R.string.status_needs_update);
                value.setTextColor(context.getColor(R.color.colorNeedsUpdate));
                fragment.setStatus(1);
            }
            else if("no-local-db".equals(result)) {
                value.setText(R.string.status_no_local_db);
                value.setTextColor(context.getColor(R.color.colorNoLocalDB));
                fragment.setStatus(2);
            }
        }
    }
}