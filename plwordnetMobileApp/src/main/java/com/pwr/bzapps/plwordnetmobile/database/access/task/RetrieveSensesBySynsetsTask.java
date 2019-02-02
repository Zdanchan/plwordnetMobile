package com.pwr.bzapps.plwordnetmobile.database.access.task;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import com.pwr.bzapps.plwordnetmobile.activities.SenseViewActivity;
import com.pwr.bzapps.plwordnetmobile.database.access.ConnectionProvider;
import com.pwr.bzapps.plwordnetmobile.database.access.parse.JSONParser;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteDBFileManager;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense.SenseDAO;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;
import com.pwr.bzapps.plwordnetmobile.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class RetrieveSensesBySynsetsTask extends AsyncTask<String,Void,String> {
    private SenseViewActivity senseViewActivity;
    private Context context;
    private Object resultHolder;

    public RetrieveSensesBySynsetsTask(SenseViewActivity senseViewActivity, Context context){
        this.senseViewActivity=senseViewActivity;
        this.context=context;
        resultHolder = null;
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = null;
        if(Settings.isOfflineMode()) {
            if (!SQLiteDBFileManager.doesLocalDBExists())
                return "NoLocalDatabase";
            try {
                resultHolder = new ArrayList<SenseEntity>(
                        (new SenseDAO()).findMultipleBySynsetIds(StringUtil.parseStringToIntegerArray(strings[0])));
            }catch (SQLiteException e){
            return "LocalDBException";
            }
        }
        else
            result = ConnectionProvider.getInstance(context).getSensesBySynsetIds(strings[0]);
        return result;
    }

    protected void onPostExecute(String result) {
        if(senseViewActivity != null) {
            if (Settings.isOfflineMode()) {
                if ("LocalDBException".equals(result)) {
                    senseViewActivity.showWarningPopup();
                }
                else if ("NoLocalDatabase".equals(result)) {
                    senseViewActivity.setRelated(new ArrayList<SenseEntity>());
                }
                else
                    senseViewActivity.setRelated((ArrayList<SenseEntity>) resultHolder);

            } else {
                if ("ConnectionException".equals(result)) {
                    senseViewActivity.setWordRelatedSenses((new ArrayList<SenseEntity>()));
                }
                else
                    senseViewActivity.setRelated(JSONParser.parseJSONqueryArrayResponse(result, SenseEntity.class));
            }
        }
    }
}
