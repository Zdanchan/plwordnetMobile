package com.pwr.bzapps.plwordnetmobile.database.access.task;

import android.content.Context;
import android.os.AsyncTask;

import com.pwr.bzapps.plwordnetmobile.activities.SenseViewActivity;
import com.pwr.bzapps.plwordnetmobile.database.access.ConnectionProvider;
import com.pwr.bzapps.plwordnetmobile.database.access.parse.JSONParser;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;

public class RetrieveWordRelatedSensesTask extends AsyncTask<String,Void,String> {
    private SenseViewActivity senseViewActivity;
    private Context context;

    public RetrieveWordRelatedSensesTask(SenseViewActivity senseViewActivity, Context context){
        this.senseViewActivity=senseViewActivity;
        this.context=context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = ConnectionProvider.getInstance(context).getRelatedSensesForWord(strings[0]);
        return result;
    }

    protected void onPostExecute(String result) {
        if("ConnectionException".equals(result)){

        }
        senseViewActivity.setWordRelatedSenses(JSONParser.parseJSONqueryArrayResponse(result,SenseEntity.class));
    }
}
