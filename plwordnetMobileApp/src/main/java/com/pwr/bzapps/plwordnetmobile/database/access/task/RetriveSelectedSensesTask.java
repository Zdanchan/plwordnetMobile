package com.pwr.bzapps.plwordnetmobile.database.access.task;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.pwr.bzapps.plwordnetmobile.activities.SenseViewActivity;
import com.pwr.bzapps.plwordnetmobile.database.access.ConnectionProvider;
import com.pwr.bzapps.plwordnetmobile.database.access.parse.JSONParser;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;

public class RetriveSelectedSensesTask extends AsyncTask<String,Void,String> {
    private SenseViewActivity senseViewActivity;
    private Context context;

    public RetriveSelectedSensesTask(SenseViewActivity senseViewActivity, Context context){
        this.senseViewActivity=senseViewActivity;
        this.context=context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = ConnectionProvider.getInstance(context).getSensesBySynsetIds(strings[0]);
        return result;
    }

    protected void onPostExecute(String result) {
        if("ConnectionException".equals(result)){

        }
        senseViewActivity.setRelated(JSONParser.parseJSONqueryArrayResponse(result,SenseEntity.class));
    }
}
