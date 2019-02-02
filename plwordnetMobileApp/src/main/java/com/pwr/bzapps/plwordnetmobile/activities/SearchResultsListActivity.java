package com.pwr.bzapps.plwordnetmobile.activities;

import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.activities.template.BackButtonActivity;
import com.pwr.bzapps.plwordnetmobile.activities.template.DrawerMenuActivity;
import com.pwr.bzapps.plwordnetmobile.database.access.task.RetrieveSensesTask;
import com.pwr.bzapps.plwordnetmobile.database.adapter.SenseAdapter;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.LexiconEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.PartOfSpeechEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.WordEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.util.ArrayList;

public class SearchResultsListActivity extends DrawerMenuActivity implements AdapterView.OnItemClickListener {

    private ArrayList<SenseEntity> data;
    private ListView listView;
    private SenseAdapter adapter;
    private EditText searchEdit;
    private ImageButton searchButton;
    private TextView messages;
    private RetrieveSensesTask retrieveSensesTask;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_results_list);
        super.onCreate(savedInstanceState);

        //prevents auto-opening keybord
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        searchEdit = findViewById(R.id.search_edit);
        searchButton = findViewById(R.id.search_button);
        listView = findViewById(R.id.results_list);
        messages = findViewById(R.id.message_text);
        progressBar = findViewById(R.id.loading_spinner);
        wentToNextActivity = true;
        String word = getIntent().getExtras().getString("search_value","");
        if("".equals(word)){
            handleClipboardContent();
        }
        else{
            searchEdit.setText(word);
        }
        data = new ArrayList<SenseEntity>();
        adapter = new SenseAdapter(getApplicationContext(),data);
        adapter.getData().clear();
        retrieveSensesTask = new RetrieveSensesTask(getApplicationContext(),adapter,messages,this);
        if(!"".equals(word) && word!=null){
            retrieveSensesTask.execute(word.trim());

        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
        }
        listView.setAdapter(adapter);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkIfContainsText(searchEdit.getText().toString())) {
                    if (retrieveSensesTask != null) {
                        retrieveSensesTask.cancel(true);
                    }
                    adapter.getData().clear();
                    messages.setText("");
                    Settings.putSearchEntryToHistory(getApplicationContext(), searchEdit.getText().toString());
                    retrieveSensesTask = new RetrieveSensesTask(getApplicationContext(), adapter, messages, SearchResultsListActivity.this);
                    retrieveSensesTask.execute(searchEdit.getText().toString().trim());
                }
            }
        });
        searchEdit.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if(checkIfContainsText(searchEdit.getText().toString())) {
                        if (retrieveSensesTask != null) {
                            retrieveSensesTask.cancel(true);
                        }
                        adapter.getData().clear();
                        messages.setText("");
                        Settings.putSearchEntryToHistory(getApplicationContext(), searchEdit.getText().toString());
                        retrieveSensesTask = new RetrieveSensesTask(getApplicationContext(), adapter, messages, SearchResultsListActivity.this);
                        retrieveSensesTask.execute(searchEdit.getText().toString().trim());
                        return true;
                    }
                }
                return false;
            }
        });
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if(source.charAt(i)==' '){

                    }
                    else if (!Character.isLetterOrDigit(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        searchEdit.setFilters(new InputFilter[]{filter});
        listView.setOnItemClickListener(this);
        //adapter.notifyDataSetChanged();
        ImageButton clear_text_button = findViewById(R.id.clear_text_button);
        clear_text_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchEdit.setText("");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Settings.getLocaleName()!=this.curr_language) {
            recreate();
        }
        handleClipboardContent();
    }

    @Override
    protected void onDestroy(){
        if(retrieveSensesTask!=null){
            retrieveSensesTask.cancel(true);
        }
        super.onDestroy();
    }

    private void handleClipboardContent(){
        if(!wentToNextActivity){
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            if(clipboard.hasPrimaryClip())
                if(clipboard.getPrimaryClip().getItemCount()>0)
                    if(clipboard.getPrimaryClip().getItemAt(0)!=null){
                        String string = "";
                        Object item = clipboard.getPrimaryClip().getItemAt(0).getText();
                        if(item instanceof android.text.SpannableString){
                            string = ((android.text.SpannableString)item).subSequence(0,((SpannableString) item).length()).toString();
                        }
                        else if(item instanceof String){
                            string = (String) item;
                        }
                        if(string!=null)
                            if(string.length()<40)
                                searchEdit.setText(clipboard.getPrimaryClip().getItemAt(0).getText());
                    }
        }
        wentToNextActivity = false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getApplicationContext(), SenseViewActivity.class);
        ArrayList<SenseEntity> word_related_senses = new ArrayList<SenseEntity>();
        intent.putExtra("sense_entity",data.get(i));
        for(SenseEntity sense : data){
            if(sense.getWord_id().getWord().equals(data.get(i).getWord_id().getWord()) &&
                    sense.getPart_of_speech_id().getId().equals(data.get(i).getPart_of_speech_id().getId()) &&
                    sense.getLexicon_id().getLanguage_name().equals(data.get(i).getLexicon_id().getLanguage_name())){
                word_related_senses.add(sense);
            }
        }
        //intent.putExtra("word_related_senses", word_related_senses);
        wentToNextActivity = true;
        startActivity(intent);
    }

    public void setProgressBarVisibility(final int visibility){
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                progressBar.setVisibility(visibility);
            }
        });

    }

    private void refreshListView(){
        adapter.notifyDataSetChanged();
    }

    private boolean checkIfContainsText(String string){
        String cuted = string.replaceAll(" ", "");
        if(cuted.equals(""))
            return false;
        return true;
    }


}
