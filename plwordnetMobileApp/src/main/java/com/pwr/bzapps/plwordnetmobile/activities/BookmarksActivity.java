package com.pwr.bzapps.plwordnetmobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.activities.template.BackButtonActivity;
import com.pwr.bzapps.plwordnetmobile.database.access.task.RetrieveSelectedSensesTask;
import com.pwr.bzapps.plwordnetmobile.database.adapter.SearchEntryAdapter;
import com.pwr.bzapps.plwordnetmobile.database.adapter.SenseAdapter;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BookmarksActivity extends BackButtonActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private TextView message_text;
    private SenseAdapter adapter;
    private ArrayList<SenseEntity> data;
    private RetrieveSelectedSensesTask retrieveSelectedSensesTask;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_bookmarks);
        super.onCreate(savedInstanceState);
        listView = findViewById(R.id.bookmarks_list);
        message_text = findViewById(R.id.message_text);
        progressBar = findViewById(R.id.loading_spinner_bookmarks);
        data = new ArrayList<SenseEntity>();
        retrieveSelectedSensesTask = new RetrieveSelectedSensesTask(this,getApplicationContext());
        Integer[] bookmarks = Settings.getBookmarkedIds(getApplicationContext());
        if(bookmarks.length==0){
            message_text.setText(getResources().getText(R.string.no_bookmarks));
            progressBar.setVisibility(View.GONE);
        }
        else
            retrieveSelectedSensesTask.execute(arrayToString(bookmarks));
        adapter = new SenseAdapter(getApplicationContext(),data);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getApplicationContext(), SenseViewActivity.class);
        intent.putExtra("sense_entity",data.get(i));
        startActivity(intent);
    }

    public void setBookmarksData(List<SenseEntity> data){
        this.data.addAll(data);
        listView.setAdapter(adapter);
        if(data.isEmpty()){
            message_text.setText(getResources().getString(R.string.no_bookmarks));
        }
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    public void informAboutConnectionProblems(){
        message_text.setText(R.string.no_connection);
    }

    private String arrayToString(Integer[] array){
        String result = "";
        for(int i=0; i<array.length; i++){
            result+=array[i]+",";
        }
        if(!result.isEmpty())
            result=result.substring(0,result.length()-1);

        return result;
    }
}