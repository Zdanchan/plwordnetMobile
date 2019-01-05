package com.pwr.bzapps.plwordnetmobile.database.access.task;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.activities.SearchResultsListActivity;
import com.pwr.bzapps.plwordnetmobile.database.access.ConnectionProvider;
import com.pwr.bzapps.plwordnetmobile.database.access.parse.JSONParser;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense.SenseDAO;
import com.pwr.bzapps.plwordnetmobile.database.adapter.SenseAdapter;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;
import com.pwr.bzapps.plwordnetmobile.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class RetrieveSensesTask extends AsyncTask<String,Void,String>{

    private SenseAdapter adapter;
    private TextView message_view;
    private Context context;
    private SearchResultsListActivity searchResultsListActivity;
    private Object resultHolder;

    public RetrieveSensesTask(Context context, SenseAdapter adapter){
        this.adapter=adapter;
        this.context=context;
        resultHolder = null;
    }

    public RetrieveSensesTask(Context context, SenseAdapter adapter, TextView message_view){
        this.context=context;
        this.adapter=adapter;
        this.message_view = message_view;
        resultHolder = null;
    }

    public RetrieveSensesTask(Context context, SenseAdapter adapter, TextView message_view, SearchResultsListActivity searchResultsListActivity){
        this.context=context;
        this.adapter=adapter;
        this.message_view = message_view;
        this.searchResultsListActivity=searchResultsListActivity;
        resultHolder = null;
    }

    @Override
    protected String doInBackground(String... strings) {
        if(searchResultsListActivity!=null){
            searchResultsListActivity.setProgressBarVisibility(View.VISIBLE);
        }
        String result = null;
        if(Settings.isOfflineMode()) {
            resultHolder = new ArrayList<SenseEntity>((new SenseDAO()).findByWord(strings[0],50));
            Collections.sort((ArrayList<SenseEntity>) resultHolder);
        }
        else
            result = ConnectionProvider.getInstance(context).getSensesForWord(strings[0]);
        return result;
    }

    protected void onPostExecute(String result) {
        ArrayList<SenseEntity> results_list = null;
        if(Settings.isOfflineMode())
            results_list = (ArrayList<SenseEntity>) resultHolder;
        else {
            if("ConnectionException".equals(result)){
                if(message_view !=null){
                    message_view.setText(context.getResources().getString(R.string.no_connection));
                }
                if(searchResultsListActivity!=null){
                    searchResultsListActivity.setProgressBarVisibility(View.INVISIBLE);
                }
                return;
            }
            else if("{\"content\":[]}".equals(result) || result==null){
                if(message_view !=null){
                    message_view.setText(context.getResources().getString(R.string.no_results));
                }
                if(searchResultsListActivity!=null){
                    searchResultsListActivity.setProgressBarVisibility(View.INVISIBLE);
                }
                return;
            }
            results_list = JSONParser.parseJSONqueryArrayResponse(result, SenseEntity.class);
        }
        //Collections.sort(results_list);
        adapter.getData().addAll(results_list);
        adapter.notifyDataSetChanged();
        if(searchResultsListActivity!=null){
            searchResultsListActivity.setProgressBarVisibility(View.INVISIBLE);
        }
    }
}
