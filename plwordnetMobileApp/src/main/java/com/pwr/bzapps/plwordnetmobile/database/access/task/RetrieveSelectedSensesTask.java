package com.pwr.bzapps.plwordnetmobile.database.access.task;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.pwr.bzapps.plwordnetmobile.activities.BookmarksActivity;
import com.pwr.bzapps.plwordnetmobile.activities.SenseViewActivity;
import com.pwr.bzapps.plwordnetmobile.database.access.ConnectionProvider;
import com.pwr.bzapps.plwordnetmobile.database.access.parse.JSONParser;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense.SenseDAO;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;
import com.pwr.bzapps.plwordnetmobile.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RetrieveSelectedSensesTask extends AsyncTask<String,Void,String> {
    private BookmarksActivity bookmarksActivity;
    private Context context;
    private Object resultHolder;

    public RetrieveSelectedSensesTask(BookmarksActivity bookmarksActivity, Context context){
        this.bookmarksActivity=bookmarksActivity;
        this.context=context;
        resultHolder = null;
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = null;
        if(Settings.isOfflineMode())
            resultHolder = new ArrayList<SenseEntity>(
                    (new SenseDAO()).findMultipleByIds(StringUtil.parseStringToIntegerArray(strings[0])));
        else
            result = ConnectionProvider.getInstance(context).getSensesByIds(strings[0]);
        return result;
    }

    protected void onPostExecute(String result) {
        if(Settings.isOfflineMode()){
            if ("none".equals(Settings.getDbType())) {
                if (bookmarksActivity != null) {
                    bookmarksActivity.informThereIsNoLocalDatabase();
                }
            }
            if (bookmarksActivity != null) {
                Collections.sort((ArrayList<SenseEntity>) resultHolder);
                bookmarksActivity.setBookmarksData((ArrayList<SenseEntity>)resultHolder);
            }
        }
        else {
            if ("ConnectionException".equals(result)) {
                if (bookmarksActivity != null) {
                    bookmarksActivity.informAboutConnectionProblems();
                }
            }
            if (bookmarksActivity != null) {
                Collections.sort((ArrayList<SenseEntity>) resultHolder);
                bookmarksActivity.setBookmarksData(JSONParser.parseJSONqueryArrayResponse(result, SenseEntity.class));
            }
        }
    }
}
