package com.pwr.bzapps.plwordnetmobile.database.access.task;

import android.content.Context;
import android.os.AsyncTask;

import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.activities.SenseViewActivity;
import com.pwr.bzapps.plwordnetmobile.database.access.ConnectionProvider;
import com.pwr.bzapps.plwordnetmobile.database.access.parse.JSONParser;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;

import java.util.ArrayList;

public class RetrieveOneSenseTask extends AsyncTask<String,Void,String> {

    private SenseViewActivity senseViewActivity;
    private Context context;

    public RetrieveOneSenseTask(SenseViewActivity senseViewActivity, Context context){
        this.senseViewActivity=senseViewActivity;
        this.context=context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = ConnectionProvider.getInstance(context).getSenseById(Integer.parseInt(strings[0]));
        return result;
    }

    protected void onPostExecute(String result) {
        if("ConnectionException".equals(result)){
            //if(message_view !=null){
            //    message_view.setText(context.getResources().getString(R.string.no_connection));
            //}
            //return;
        }
        senseViewActivity.setSenseEntity(JSONParser.parseJSONqueryResponse(result,SenseEntity.class));
    }
}
