package com.pwr.bzapps.plwordnetmobile.database.access.task;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.pwr.bzapps.plwordnetmobile.activities.BookmarksActivity;
import com.pwr.bzapps.plwordnetmobile.activities.SenseViewActivity;
import com.pwr.bzapps.plwordnetmobile.database.access.ConnectionProvider;
import com.pwr.bzapps.plwordnetmobile.database.access.parse.JSONParser;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;

public class RetrieveSelectedSensesTask extends AsyncTask<String,Void,String> {
    private BookmarksActivity bookmarksActivity;
    private Context context;

    public RetrieveSelectedSensesTask(BookmarksActivity bookmarksActivity, Context context){
        this.bookmarksActivity=bookmarksActivity;
        this.context=context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = ConnectionProvider.getInstance(context).getSensesByIds(strings[0]);
        return result;
    }

    protected void onPostExecute(String result) {
        if("ConnectionException".equals(result)){
            if(bookmarksActivity!=null){
                bookmarksActivity.informAboutConnectionProblems();
            }
        }
        if(bookmarksActivity!=null){
            bookmarksActivity.setBookmarksData(JSONParser.parseJSONqueryArrayResponse(result,SenseEntity.class));
        }
    }
}
