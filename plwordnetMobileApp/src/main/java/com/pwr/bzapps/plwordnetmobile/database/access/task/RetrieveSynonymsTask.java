package com.pwr.bzapps.plwordnetmobile.database.access.task;

import android.content.Context;
import android.os.AsyncTask;

import com.pwr.bzapps.plwordnetmobile.activities.SenseViewActivity;
import com.pwr.bzapps.plwordnetmobile.database.access.ConnectionProvider;
import com.pwr.bzapps.plwordnetmobile.database.access.parse.JSONParser;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;

public class RetrieveSynonymsTask extends AsyncTask<String,Void,String> {
    private SenseViewActivity senseViewActivity;
    private Context context;

    public RetrieveSynonymsTask(SenseViewActivity senseViewActivity, Context context){
        this.senseViewActivity=senseViewActivity;
        this.context=context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = ConnectionProvider.getInstance(context).getSynonymsBySynsetId(strings[0]);
        return result;
    }

    protected void onPostExecute(String result) {
        if("ConnectionException".equals(result)){

        }
        senseViewActivity.setSynonyms(JSONParser.parseJSONqueryArrayResponse(result,SenseEntity.class));
    }
}
