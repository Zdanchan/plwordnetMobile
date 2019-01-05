package com.pwr.bzapps.plwordnetmobile.database.access.task;

import android.content.Context;
import android.os.AsyncTask;

import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.activities.SenseViewActivity;
import com.pwr.bzapps.plwordnetmobile.database.access.ConnectionProvider;
import com.pwr.bzapps.plwordnetmobile.database.access.parse.JSONParser;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense.SenseDAO;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.util.ArrayList;

public class RetrieveOneSenseTask extends AsyncTask<String,Void,String> {

    private SenseViewActivity senseViewActivity;
    private Context context;
    private Object resultHolder;

    public RetrieveOneSenseTask(SenseViewActivity senseViewActivity, Context context){
        this.senseViewActivity=senseViewActivity;
        this.context=context;
        resultHolder = null;
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = null;
        if(Settings.isOfflineMode())
            resultHolder = (new SenseDAO()).findById(Integer.parseInt(strings[0]));
        else
            result = ConnectionProvider.getInstance(context).getSenseById(Integer.parseInt(strings[0]));
        return result;
    }

    protected void onPostExecute(String result) {
        if(!Settings.isOfflineMode()) {
            if ("ConnectionException".equals(result)) {
                //if(message_view !=null){
                //    message_view.setText(context.getResources().getString(R.string.no_connection));
                //}
                //return;
            }
            resultHolder = JSONParser.parseJSONqueryResponse(result,SenseEntity.class);
        }
        senseViewActivity.setSenseEntity((SenseEntity)resultHolder);
    }
}
