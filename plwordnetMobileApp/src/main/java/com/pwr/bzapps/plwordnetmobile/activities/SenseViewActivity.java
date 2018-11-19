package com.pwr.bzapps.plwordnetmobile.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.activities.template.BackButtonActivity;
import com.pwr.bzapps.plwordnetmobile.database.access.task.RetrieveSynonymsTask;
import com.pwr.bzapps.plwordnetmobile.database.access.task.RetrieveWordRelatedSensesTask;
import com.pwr.bzapps.plwordnetmobile.database.access.task.RetriveSelectedSensesTask;
import com.pwr.bzapps.plwordnetmobile.database.adapter.SenseAdapter;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.EmotionalAnnotationEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseAttributeEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseExampleEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseRelationEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetAttributeEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetExampleEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetRelationEntity;
import com.pwr.bzapps.plwordnetmobile.database.interpretation.EmotionalAnnotationsInterpreter;
import com.pwr.bzapps.plwordnetmobile.layout.custom.WrapedMultilineTextWiew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SenseViewActivity extends BackButtonActivity {

    private SenseEntity entity;
    private ArrayList<SenseEntity> word_related_senses;
    private ArrayList<SenseEntity> related;
    private ArrayList<SenseEntity> synonyms;
    private ArrayList<SynsetRelationEntity> relations;
    private ProgressBar progress_bar;
    private AsyncTask retrieveSelectedSensesTask, retrieveSynonymsTask, retrieveWordRelatedSensesTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_sense_view);
        super.onCreate(savedInstanceState);
        progress_bar = findViewById(R.id.loading_spinner);
        SenseEntity entity = (SenseEntity)getIntent().getSerializableExtra("sense_entity");
        word_related_senses = (ArrayList<SenseEntity>)getIntent().getSerializableExtra("word_related_senses");
        if(entity!=null){
            setSenseEntity(entity);
        }
        relations = new ArrayList<SynsetRelationEntity>();
        relations.addAll(entity.getSynset_id().getRelation_child());
        relations.addAll(entity.getSynset_id().getRelation_parent());

        LinkedList ids = new LinkedList();

        for(SynsetRelationEntity relation : entity.getSynset_id().getRelation_child()){
            ids.add(relation.getParent_synset_id());
        }
        for(SynsetRelationEntity relation : entity.getSynset_id().getRelation_parent()){
            ids.add(relation.getChild_synset_id());
        }

        retrieveSelectedSensesTask = new RetriveSelectedSensesTask(this,getApplicationContext()).execute(ids.toString());
        retrieveSynonymsTask = new RetrieveSynonymsTask(this,getApplicationContext()).execute(entity.getSynset_id().getId().toString());
        if(word_related_senses==null){
            retrieveWordRelatedSensesTask = new RetrieveWordRelatedSensesTask(this,getApplicationContext()).execute(entity.getWord_id().getWord());
        }
        prepareLowerBar();
    }

    @Override
    protected void onDestroy(){
        if(retrieveSelectedSensesTask!=null){
            retrieveSelectedSensesTask.cancel(true);
        }
        if(retrieveSynonymsTask!=null){
            retrieveSynonymsTask.cancel(true);
        }
        if(retrieveWordRelatedSensesTask!=null){
            retrieveWordRelatedSensesTask.cancel(true);
        }
        super.onDestroy();
    }

    public void setRelated(ArrayList<SenseEntity> related) {
        this.related = related;
        handleRelatedSenses();
        progress_bar.setVisibility(View.INVISIBLE);
    }

    private void handleRelatedSenses(){
        LinearLayout sense_relations_container = findViewById(R.id.sense_relations_container);

        Map<Integer,ArrayList<SynsetRelationEntity>> relations_sorted = new HashMap<>();

        for(SynsetRelationEntity relation : relations){
            if(!relations_sorted.containsKey(relation.getSynset_relation_type_id().getId())){
                relations_sorted.put(relation.getSynset_relation_type_id().getId(), new ArrayList<SynsetRelationEntity>());
            }
            ArrayList<SynsetRelationEntity> list = relations_sorted.get(relation.getSynset_relation_type_id().getId());
            list.add(relation);
        }

        for(ArrayList<SynsetRelationEntity> list : relations_sorted.values()){
            RelativeLayout type = (RelativeLayout)LayoutInflater.from(getApplicationContext()).inflate(R.layout.relation_type_template, null);
            ((TextView)type.findViewById(R.id.relation_type_name)).setText(
                    getResources().getString(getResources().getIdentifier("als_" + list.get(0).getSynset_relation_type_id().getName_id(),"string",getPackageName())));
            LinearLayout cell_container = type.findViewById(R.id.relations_container);
            for(SynsetRelationEntity relation : list){
                RelativeLayout cell = (RelativeLayout)LayoutInflater.from(getApplicationContext()).inflate(R.layout.relation_template, null);
                Integer other = (relation.getChild_synset_id().equals(entity.getSynset_id().getId()) ? relation.getParent_synset_id() : relation.getChild_synset_id());
                final SenseEntity sense = findRelatedBySynsetId(other);
                ((TextView)cell.findViewById(R.id.relation_sense_id)).setText(sense.getWord_id().getWord());
                ((ImageView)cell.findViewById(R.id.language_icon)).setImageResource(SenseAdapter.getFlagResource(sense.getLexicon_id().getLanguage_name()));
                if("Polish".equals(sense.getLexicon_id().getLanguage_name())){
                    ((TextView) cell.findViewById(R.id.relation_description)).setText(
                            SenseAdapter.shortenDescription(((ArrayList<SenseAttributeEntity>) sense.getSense_attributes()).get(0).getDefinition()));
                }
                else {
                    ((TextView) cell.findViewById(R.id.relation_description)).setText(
                            SenseAdapter.shortenDescription(((ArrayList<SynsetAttributeEntity>) sense.getSynset_id().getSynset_attributes()).get(0).getDefinition()));
                }
                cell_container.addView(cell);
                cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), SenseViewActivity.class);
                        ArrayList<SenseEntity> word_related_senses = new ArrayList<SenseEntity>();
                        intent.putExtra("sense_entity",sense);
                        startActivity(intent);
                    }
                });
            }
            sense_relations_container.addView(type);
        }
    }

    private SenseEntity findRelatedById(Integer id){
        for(SenseEntity sense : related){
            if(sense.getId().equals(id)){
                return sense;
            }
        }
        return null;
    }

    private SenseEntity findRelatedBySynsetId(Integer id){
        for(SenseEntity sense : related){
            if(sense.getSynset_id().getId().equals(id)){
                return sense;
            }
        }
        return null;
    }

    public void setSenseEntity(SenseEntity entity){
        this.entity=entity;

        TextView sense_word= findViewById(R.id.sense_word);
        TextView sense_description= findViewById(R.id.sense_description);
        TextView sense_part_of_speech= findViewById(R.id.sense_attribute_part_of_speech);
        ImageView language_flag= findViewById(R.id.language_icon);
        TextView sense_domain = findViewById(R.id.sense_domain);
        LinearLayout sense_examples_container = findViewById(R.id.sense_examples_container);
        //LinearLayout sense_synonyms_container = findViewById(R.id.sense_synonyms_container);
        LinearLayout annotation_container = (LinearLayout)findViewById(R.id.emotional_annotations_container);
        TextView sense_source = findViewById(R.id.sense_source);


        sense_word.setText(entity.getWord_id().getWord() + "-" + entity.getVariant());
        sense_part_of_speech.setText(SenseAdapter.getPartOfSpeechString(entity.getPart_of_speech_id().getId(), getApplicationContext()).toUpperCase());
        String language = entity.getLexicon_id().getLanguage_name();
        language_flag.setImageResource(SenseAdapter.getFlagResource(language));
        if("Polish".equals(language)){
            sense_description.setText(((ArrayList<SenseAttributeEntity>)entity.getSense_attributes()).get(0).getDefinition());
        }
        else{
            sense_description.setText(((ArrayList<SynsetAttributeEntity>)entity.getSynset_id().getSynset_attributes()).get(0).getDefinition());
        }
        sense_domain.setText(getResources().getString(getResources().getIdentifier("als_" + entity.getDomain_id().getName_id(),"string", getPackageName())));
        sense_source.setText(entity.getLexicon_id().getName());

        setExamples(sense_examples_container);

        setEmotionalAnnotations(annotation_container);



    }

    private void setExamples(LinearLayout sense_examples_container){
        if("Polish".equals(entity.getLexicon_id().getLanguage_name())){
            ArrayList<SenseExampleEntity> examples = (ArrayList<SenseExampleEntity>)((ArrayList<SenseAttributeEntity>)entity.getSense_attributes())
                    .get(0).getSense_examples();
            if(examples.size()<1){
                ((RelativeLayout)findViewById(R.id.examples_row)).setVisibility(View.GONE);
                ((TextView)findViewById(R.id.sense_attribute_examples)).setVisibility(View.GONE);
                sense_examples_container.setVisibility(View.GONE);
            }
            else {
                for (SenseExampleEntity exampleEntity : examples) {
                    WrapedMultilineTextWiew example = new WrapedMultilineTextWiew(getApplicationContext());
                    example.setText(exampleEntity.getExample());
                    example.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDescriptionText));
                    example.setTextSize(14);
                    example.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                    sense_examples_container.addView(example);
                }
            }
        }
        else{
            ArrayList<SynsetExampleEntity> examples = (ArrayList<SynsetExampleEntity>)((ArrayList<SynsetAttributeEntity>)entity.getSynset_id()
                    .getSynset_attributes()).get(0).getSynset_examples();
            if(examples.size()<1){
                ((RelativeLayout)findViewById(R.id.examples_row)).setVisibility(View.GONE);
                ((TextView)findViewById(R.id.sense_attribute_examples)).setVisibility(View.GONE);
                sense_examples_container.setVisibility(View.GONE);
            }
            else{
                for(SynsetExampleEntity exampleEntity : examples){
                    WrapedMultilineTextWiew example = new WrapedMultilineTextWiew(getApplicationContext());
                    example.setText(exampleEntity.getExample());
                    example.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorDescriptionText));
                    example.setTextSize(14);
                    example.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                    sense_examples_container.addView(example);
                }
            }
        }
    }

    private void setEmotionalAnnotations(LinearLayout annotation_container){
        boolean no_emotions = true;
        for(EmotionalAnnotationEntity emo : entity.getEmotional_annotations()){
            RelativeLayout annotation = (RelativeLayout)LayoutInflater.from(getApplicationContext()).inflate(R.layout.emotional_annotation_template, null);
            ConstraintLayout annotation_attributes = annotation.findViewById(R.id.annotation_attributes);
            if(emo.isHas_emotional_characteristic()) {
                int counter=1;
                if (emo.getMarkedness() != null && !"".equals(emo.getMarkedness())) {
                    TextView markedness_value = (TextView)annotation_attributes.findViewById(R.id.polarity_value);
                    markedness_value.setText(EmotionalAnnotationsInterpreter.interpretMarkedness(getApplicationContext(), emo.getMarkedness()));
                    if(counter%2==0)
                        markedness_value.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBackgroundAccent));
                    counter++;
                }
                else{
                    ((TextView)annotation_attributes.findViewById(R.id.polarity_label)).setVisibility(View.GONE);
                    ((TextView)annotation_attributes.findViewById(R.id.polarity_value)).setVisibility(View.GONE);
                }

                if (emo.getEmotions() != null && !"".equals(emo.getEmotions())) {
                    TextView emotions_value = (TextView)annotation_attributes.findViewById(R.id.emotions_value);
                    emotions_value.setText(EmotionalAnnotationsInterpreter.interpretEmotions(getApplicationContext(), emo.getEmotions()));
                    if(counter%2==0)
                        emotions_value.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBackgroundAccent));
                    counter++;
                }
                else{
                    ((TextView)annotation_attributes.findViewById(R.id.emotions_label)).setVisibility(View.GONE);
                    ((TextView)annotation_attributes.findViewById(R.id.emotions_value)).setVisibility(View.GONE);
                }

                if (emo.getValuations() != null && !"".equals(emo.getValuations())) {
                    TextView valuations_value = (TextView)annotation_attributes.findViewById(R.id.valuations_value);
                    valuations_value.setText(EmotionalAnnotationsInterpreter.interpretValuations(getApplicationContext(), emo.getValuations()));
                    if(counter%2==0)
                        valuations_value.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBackgroundAccent));
                    counter++;
                }
                else{
                    ((TextView)annotation_attributes.findViewById(R.id.valuations_label)).setVisibility(View.GONE);
                    ((TextView)annotation_attributes.findViewById(R.id.valuations_value)).setVisibility(View.GONE);
                }

                if ((emo.getExample1() != null && !"".equals(emo.getExample1())) || (emo.getExample2() != null && !"".equals(emo.getExample2()))) {
                    TextView examples_value = (TextView)annotation_attributes.findViewById(R.id.examples_value);
                    examples_value.setText(EmotionalAnnotationsInterpreter.prepareExamples(emo.getExample1(), emo.getExample2()));
                    if(counter%2==0)
                        examples_value.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBackgroundAccent));
                    counter++;
                }
                else{
                    ((TextView)annotation_attributes.findViewById(R.id.examples_label)).setVisibility(View.GONE);
                    ((TextView)annotation_attributes.findViewById(R.id.examples_value)).setVisibility(View.GONE);
                }
                annotation_container.addView(annotation);
                no_emotions=false;
            }
        }
        if(entity.getEmotional_annotations().size()==0 || no_emotions){
            ((TextView)findViewById(R.id.emotional_annotation_attribute)).setVisibility(View.GONE);
            annotation_container.setVisibility(View.GONE);
        }
    }

    public void setSynonyms(ArrayList<SenseEntity> synonyms){
        this.synonyms=synonyms;
        LinearLayout synonyms_container = findViewById(R.id.sense_synonyms_container);
        if(synonyms.size()<2){
            ((RelativeLayout)findViewById(R.id.synonyms_row)).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.sense_attribute_synonyms)).setVisibility(View.GONE);
            synonyms_container.setVisibility(View.GONE);
        }
        else {
            for (final SenseEntity synonym : synonyms) {
                if (!synonym.getWord_id().getWord().equals(entity.getWord_id().getWord())) {
                    RelativeLayout synonym_cell = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.synonym_template, null);
                    ((TextView) synonym_cell.findViewById(R.id.synonym_name)).setText(synonym.getWord_id().getWord() + "-" + synonym.getVariant());
                    if ("Polish".equals(synonym.getLexicon_id().getLanguage_name())) {
                        ((TextView) synonym_cell.findViewById(R.id.synonym_description)).setText(
                                ((ArrayList<SenseAttributeEntity>) synonym.getSense_attributes()).get(0).getDefinition());
                    } else {
                        ((TextView) synonym_cell.findViewById(R.id.synonym_description)).setText(
                                ((ArrayList<SynsetAttributeEntity>) synonym.getSynset_id().getSynset_attributes()).get(0).getDefinition());
                    }
                    synonyms_container.addView(synonym_cell);
                    synonym_cell.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), SenseViewActivity.class);
                            ArrayList<SenseEntity> word_related_senses = new ArrayList<SenseEntity>();
                            intent.putExtra("sense_entity",synonym);
                            startActivity(intent);
                        }
                    });
                }
            }
        }
    }

    public void setWordRelatedSenses(final ArrayList<SenseEntity> word_related_senses){
        this.word_related_senses=word_related_senses;
        Button other_senses = findViewById(R.id.other_senses_button);
        other_senses.setText(entity.getVariant() + "/" + word_related_senses.size());
        other_senses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(SenseViewActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.other_senses_popup, null);
                alertDialog.setView(convertView);
                alertDialog.setTitle(getResources().getString(R.string.other_senses));
                ListView lv = (ListView) convertView.findViewById(R.id.other_senses_list);
                SenseAdapter adapter = new SenseAdapter(getApplicationContext(),word_related_senses);
                lv.setAdapter(adapter);
                final AlertDialog dialog = alertDialog.show();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getApplicationContext(), SenseViewActivity.class);
                        intent.putExtra("sense_entity",word_related_senses.get(i));
                        intent.putExtra("word_related_senses", word_related_senses);
                        dialog.dismiss();
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void prepareLowerBar(){
        Button other_senses = findViewById(R.id.other_senses_button);
        ImageButton view_graph = findViewById(R.id.graph_button);
        ImageButton search = findViewById(R.id.search_button);

        if(word_related_senses!=null)
            other_senses.setText(entity.getVariant() + "/" + word_related_senses.size());

        other_senses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(SenseViewActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.other_senses_popup, null);
                alertDialog.setView(convertView);
                alertDialog.setTitle(getResources().getString(R.string.other_senses));
                ListView lv = (ListView) convertView.findViewById(R.id.other_senses_list);
                SenseAdapter adapter = new SenseAdapter(getApplicationContext(),word_related_senses);
                lv.setAdapter(adapter);
                final AlertDialog dialog = alertDialog.show();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(getApplicationContext(), SenseViewActivity.class);
                        intent.putExtra("sense_entity",word_related_senses.get(i));
                        intent.putExtra("word_related_senses", word_related_senses);
                        dialog.dismiss();
                        startActivity(intent);
                    }
                });
            }
        });

        view_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), GraphBrowserActivity.class);
                intent.putExtra("synset_id",entity.getSynset_id().getId().intValue());
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchResultsListActivity.class);
                intent.putExtra("search_value","");
                startActivity(intent);
            }
        });
    }
}