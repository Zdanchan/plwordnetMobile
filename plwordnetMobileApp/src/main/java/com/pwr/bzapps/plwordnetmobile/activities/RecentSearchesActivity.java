package com.pwr.bzapps.plwordnetmobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.activities.template.BackButtonActivity;
import com.pwr.bzapps.plwordnetmobile.activities.template.DrawerMenuActivity;
import com.pwr.bzapps.plwordnetmobile.database.adapter.SearchEntryAdapter;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.util.ArrayList;
import java.util.LinkedList;

public class RecentSearchesActivity extends BackButtonActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private TextView message_text;
    private SearchEntryAdapter adapter;
    private ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_recent_searches);
        super.onCreate(savedInstanceState);
        listView = findViewById(R.id.recent_searches_list);
        message_text = findViewById(R.id.message_text);
        data = new ArrayList<String>();
        adapter = new SearchEntryAdapter(getApplicationContext(),data);
        listView.setOnItemClickListener(this);
        refreshData();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getApplicationContext(), SearchResultsListActivity.class);
        intent.putExtra("search_value",data.get(i));
        Settings.putSearchEntryToHistory(getApplicationContext(),data.get(i));
        startActivity(intent);
        refreshData();
    }

    private void refreshData(){
        data.clear();
        LinkedList<String> inputdata = Settings.getSearchHistory(getApplicationContext());
        data.addAll(inputdata);
        listView.setAdapter(adapter);
        if(data.isEmpty()){
            message_text.setText(getResources().getString(R.string.no_history));
        }
    }
}
