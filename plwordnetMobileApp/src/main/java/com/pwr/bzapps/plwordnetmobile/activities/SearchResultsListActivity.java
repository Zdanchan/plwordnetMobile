package com.pwr.bzapps.plwordnetmobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
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
import com.pwr.bzapps.plwordnetmobile.database.access.task.RetrieveSensesTask;
import com.pwr.bzapps.plwordnetmobile.database.adapter.SenseAdapter;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.LexiconEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.PartOfSpeechEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.WordEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.util.ArrayList;

public class SearchResultsListActivity extends BackButtonActivity implements AdapterView.OnItemClickListener {

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
        String word = getIntent().getExtras().getString("search_value","");
        searchEdit.setText(word);
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
                if(retrieveSensesTask!=null){
                    retrieveSensesTask.cancel(true);
                }
                adapter.getData().clear();
                messages.setText("");
                Settings.putSearchEntryToHistory(getApplicationContext(),searchEdit.getText().toString());
                retrieveSensesTask = new RetrieveSensesTask(getApplicationContext(),adapter,messages,SearchResultsListActivity.this);
                retrieveSensesTask.execute(searchEdit.getText().toString().trim());
            }
        });
        searchEdit.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if(retrieveSensesTask!=null){
                        retrieveSensesTask.cancel(true);
                    }
                    adapter.getData().clear();
                    messages.setText("");
                    Settings.putSearchEntryToHistory(getApplicationContext(),searchEdit.getText().toString());
                    retrieveSensesTask = new RetrieveSensesTask(getApplicationContext(),adapter,messages,SearchResultsListActivity.this);
                    retrieveSensesTask.execute(searchEdit.getText().toString().trim());
                    return true;
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
        intent.putExtra("word_related_senses", word_related_senses);
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

    private ArrayList<SenseEntity> prepareDummyData(){
        ArrayList<SenseEntity> dummyData = new ArrayList<SenseEntity>();

        SenseEntity item1 = new SenseEntity();
        item1.setWord_id(new WordEntity());
        item1.getWord_id().setWord("zamek");
        item1.setVariant(1);
        item1.setPart_of_speech_id(new PartOfSpeechEntity());
        item1.getPart_of_speech_id().setId(2);
        item1.setLexicon_id(new LexiconEntity());
        item1.getLexicon_id().setLanguage_name("Polish");
        //item1.setShortDescription("warowna budowla mieszkalna, rezydencja pana, króla, księcia lub magnata");

        SenseEntity item2 = new SenseEntity();
        item2.setWord_id(new WordEntity());
        item2.getWord_id().setWord("zamek");
        item2.setVariant(2);
        item2.setPart_of_speech_id(new PartOfSpeechEntity());
        item2.getPart_of_speech_id().setId(2);
        item2.setLexicon_id(new LexiconEntity());
        item2.getLexicon_id().setLanguage_name("Polish");
        //item2.setShortDescription("mechanizm lub urządzenie do zamykania np. drzwi, szuflad, walizek");

        SenseEntity item3 = new SenseEntity();
        item3.setWord_id(new WordEntity());
        item3.getWord_id().setWord("zamek");
        item3.setVariant(3);
        item3.setPart_of_speech_id(new PartOfSpeechEntity());
        item3.getPart_of_speech_id().setId(2);
        item3.setLexicon_id(new LexiconEntity());
        item3.getLexicon_id().setLanguage_name("Polish");
        //item3.setShortDescription("urządzenie do łączenia lub zabezpieczania w ustalonym położeniu elementów maszyny");

        SenseEntity item4 = new SenseEntity();
        item4.setWord_id(new WordEntity());
        item4.getWord_id().setWord("zamek");
        item4.setVariant(4);
        item4.setPart_of_speech_id(new PartOfSpeechEntity());
        item4.getPart_of_speech_id().setId(2);
        item4.setLexicon_id(new LexiconEntity());
        item4.getLexicon_id().setLanguage_name("Polish");
        //item4.setShortDescription("mechanizm broni palnej służący do zamykania na czas wystrzału i otwierania po strzale tylnej części lufy");

        SenseEntity item5 = new SenseEntity();
        item5.setWord_id(new WordEntity());
        item5.getWord_id().setWord("zamek");
        item5.setVariant(5);
        item5.setPart_of_speech_id(new PartOfSpeechEntity());
        item5.getPart_of_speech_id().setId(2);
        item5.setLexicon_id(new LexiconEntity());
        item5.getLexicon_id().setLanguage_name("Polish");
        //item5.setShortDescription("blokada w informatyce");

        SenseEntity item6 = new SenseEntity();
        item6.setWord_id(new WordEntity());
        item6.getWord_id().setWord("zamek");
        item6.setVariant(6);
        item6.setPart_of_speech_id(new PartOfSpeechEntity());
        item6.getPart_of_speech_id().setId(2);
        item6.setLexicon_id(new LexiconEntity());
        item6.getLexicon_id().setLanguage_name("Polish");
        //item6.setShortDescription("zapięcie przy ubraniu, suwak, ekler");

        SenseEntity item7 = new SenseEntity();
        item7.setWord_id(new WordEntity());
        item7.getWord_id().setWord("zamek");
        item7.setVariant(7);
        item7.setPart_of_speech_id(new PartOfSpeechEntity());
        item7.getPart_of_speech_id().setId(2);
        item7.setLexicon_id(new LexiconEntity());
        item7.getLexicon_id().setLanguage_name("Polish");
        //item7.setShortDescription("zagranie taktyczne w hokeju; zamknięcie przeciwnika w jego tercji lodowiska/boiska");

        SenseEntity item8 = new SenseEntity();
        item8.setWord_id(new WordEntity());
        item8.getWord_id().setWord("zamek");
        item8.setVariant(8);
        item8.setPart_of_speech_id(new PartOfSpeechEntity());
        item8.getPart_of_speech_id().setId(2);
        item8.setLexicon_id(new LexiconEntity());
        item8.getLexicon_id().setLanguage_name("Polish");
        //item8.setShortDescription("");

        SenseEntity item9 = new SenseEntity();
        item9.setWord_id(new WordEntity());
        item9.getWord_id().setWord("zamek");
        item9.setVariant(9);
        item9.setPart_of_speech_id(new PartOfSpeechEntity());
        item9.getPart_of_speech_id().setId(2);
        item9.setLexicon_id(new LexiconEntity());
        item9.getLexicon_id().setLanguage_name("Polish");
        //item9.setShortDescription("");

        dummyData.add(item1);
        dummyData.add(item2);
        dummyData.add(item3);
        dummyData.add(item4);
        dummyData.add(item5);
        dummyData.add(item6);
        dummyData.add(item7);
        dummyData.add(item8);
        dummyData.add(item9);

        return dummyData;
    }
}
