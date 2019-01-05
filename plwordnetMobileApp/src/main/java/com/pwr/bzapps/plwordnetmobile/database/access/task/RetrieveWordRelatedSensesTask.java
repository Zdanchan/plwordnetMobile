package com.pwr.bzapps.plwordnetmobile.database.access.task;

import android.content.Context;
import android.os.AsyncTask;

import com.pwr.bzapps.plwordnetmobile.activities.SenseViewActivity;
import com.pwr.bzapps.plwordnetmobile.database.access.ConnectionProvider;
import com.pwr.bzapps.plwordnetmobile.database.access.parse.JSONParser;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense.SenseDAO;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.util.ArrayList;

public class RetrieveWordRelatedSensesTask extends AsyncTask<String,Void,String> {
    private SenseViewActivity senseViewActivity;
    private Context context;
    private Object resultHolder;

    public RetrieveWordRelatedSensesTask(SenseViewActivity senseViewActivity, Context context){
        this.senseViewActivity=senseViewActivity;
        this.context=context;
        resultHolder = null;
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = null;
        if(Settings.isOfflineMode())
            resultHolder = new ArrayList<SenseEntity>(
                    (new SenseDAO()).findRelatedForWordAndLanguage(strings[0],strings[1]));
        else
            result = ConnectionProvider.getInstance(context).getRelatedSensesForWord(strings[0],strings[1]);
        return result;
    }

    protected void onPostExecute(String result) {
        if(Settings.isOfflineMode())
            senseViewActivity.setWordRelatedSenses((ArrayList<SenseEntity>)resultHolder);
        else {
            if ("ConnectionException".equals(result)) {

            }
            senseViewActivity.setWordRelatedSenses(JSONParser.parseJSONqueryArrayResponse(result, SenseEntity.class));
        }
    }
}
