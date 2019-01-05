package com.pwr.bzapps.plwordnetmobile.database.access.task;

import android.content.Context;
import android.os.AsyncTask;

import com.pwr.bzapps.plwordnetmobile.activities.SenseViewActivity;
import com.pwr.bzapps.plwordnetmobile.database.access.ConnectionProvider;
import com.pwr.bzapps.plwordnetmobile.database.access.parse.JSONParser;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense.SenseDAO;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;
import com.pwr.bzapps.plwordnetmobile.utils.StringUtil;

import java.util.ArrayList;

public class RetrieveSynonymsTask extends AsyncTask<String,Void,String> {
    private SenseViewActivity senseViewActivity;
    private Context context;
    private Object resultHolder;

    public RetrieveSynonymsTask(SenseViewActivity senseViewActivity, Context context){
        this.senseViewActivity=senseViewActivity;
        this.context=context;
        resultHolder = null;
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = null;
        if(Settings.isOfflineMode())
            resultHolder = new ArrayList<SenseEntity>(
                    (new SenseDAO()).findBySynsetId(Integer.parseInt(strings[0])));
        else
            result = ConnectionProvider.getInstance(context).getSynonymsBySynsetId(strings[0]);
        return result;
    }

    protected void onPostExecute(String result) {
        if(Settings.isOfflineMode())
            senseViewActivity.setSynonyms((ArrayList<SenseEntity>)resultHolder);
        else {
            if("ConnectionException".equals(result)){

            }
            senseViewActivity.setSynonyms(JSONParser.parseJSONqueryArrayResponse(result,SenseEntity.class));
        }

    }
}
