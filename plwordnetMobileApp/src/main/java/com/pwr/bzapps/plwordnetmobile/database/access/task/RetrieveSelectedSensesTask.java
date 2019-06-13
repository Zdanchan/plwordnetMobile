package com.pwr.bzapps.plwordnetmobile.database.access.task;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.pwr.bzapps.plwordnetmobile.activities.BookmarksActivity;
import com.pwr.bzapps.plwordnetmobile.activities.SenseViewActivity;
import com.pwr.bzapps.plwordnetmobile.database.access.ConnectionProvider;
import com.pwr.bzapps.plwordnetmobile.database.access.parse.JSONParser;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
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
        if(Settings.isOfflineMode()) {
            try{
                resultHolder = new ArrayList<SenseEntity>(
                        SenseDAO.findMultipleByIds(StringUtil.parseStringToLongArray(strings[0])));
            }catch (Exception e){
                return "LocalDBException";
            }
        }
        else
            result = ConnectionProvider.getInstance(context).getSensesByIds(strings[0]);
        return result;
    }

    protected void onPostExecute(String result) {
        if(bookmarksActivity!=null) {
            if (Settings.isOfflineMode()) {
                if ("LocalDBException".equals(result)) {
                    bookmarksActivity.showWarningPopup();
                }
                else if ("none".equals(Settings.getDbType())) {
                    bookmarksActivity.informThereIsNoLocalDatabase();
                }
                else {
                    Collections.sort((ArrayList<SenseEntity>) resultHolder);
                    bookmarksActivity.setBookmarksData((ArrayList<SenseEntity>) resultHolder);
                }
            } else {
                if ("ConnectionException".equals(result)) {
                    bookmarksActivity.informAboutConnectionProblems();
                }
                else {
                    resultHolder = JSONParser.parseJSONqueryArrayResponse(result, SenseEntity.class);
                    Collections.sort((ArrayList<SenseEntity>) resultHolder);
                    bookmarksActivity.setBookmarksData((ArrayList<SenseEntity>) resultHolder);
                }
            }
        }
    }
}
