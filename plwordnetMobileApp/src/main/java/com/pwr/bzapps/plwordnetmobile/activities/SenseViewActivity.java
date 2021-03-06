package com.pwr.bzapps.plwordnetmobile.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.activities.template.DrawerMenuActivity;
import com.pwr.bzapps.plwordnetmobile.database.access.task.RetrieveSensesBySynsetsTask;
import com.pwr.bzapps.plwordnetmobile.database.access.task.RetrieveSynonymsTask;
import com.pwr.bzapps.plwordnetmobile.database.access.task.RetrieveWordRelatedSensesTask;
import com.pwr.bzapps.plwordnetmobile.database.adapter.SenseAdapter;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.EmotionalAnnotationEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseAttributeEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseExampleEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetAttributeEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetExampleEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetRelationEntity;
import com.pwr.bzapps.plwordnetmobile.database.interpretation.EmotionalAnnotationsInterpreter;
import com.pwr.bzapps.plwordnetmobile.database.interpretation.RelationInterpreter;
import com.pwr.bzapps.plwordnetmobile.language.LanguageManager;
import com.pwr.bzapps.plwordnetmobile.layout.custom.WrapedMultilineTextWiew;
import com.pwr.bzapps.plwordnetmobile.layout.listener.OnClickExpander;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

public class SenseViewActivity extends DrawerMenuActivity {

    private SenseEntity entity;
    private ImageButton bookmark_button;
    private ImageView bookmark_tape;
    private ArrayList<SenseEntity> word_related_senses;
    private ArrayList<SenseEntity> related;
    private ArrayList<SenseEntity> synonyms;
    private ArrayList<SynsetRelationEntity> relations;
    private ProgressBar progress_bar;
    private AsyncTask retrieveSelectedSensesTask, retrieveSynonymsTask, retrieveWordRelatedSensesTask;
    private boolean bookmarked, isPopupUp = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_sense_view);
        super.onCreate(savedInstanceState);
        ((TextView)findViewById(R.id.sense_attribute_synonyms)).setVisibility(View.GONE);
        progress_bar = findViewById(R.id.loading_spinner);
        bookmark_button = findViewById(R.id.bookmark_button);
        bookmark_tape = findViewById(R.id.bookmark_tape);
        final SenseEntity entity = (SenseEntity)getIntent().getSerializableExtra("sense_entity");
        word_related_senses = (ArrayList<SenseEntity>)getIntent().getSerializableExtra("word_related_senses");
        if(entity!=null){
            setSenseEntity(entity);
        }
        relations = new ArrayList<SynsetRelationEntity>();
        relations.addAll(entity.getSynsetId().getRelationChild());
        //relations.addAll(entity.getSynsetAttributeId().getRelationParent());

        LinkedList ids = new LinkedList();

        for(SynsetRelationEntity relation : entity.getSynsetId().getRelationChild()){
            ids.add(relation.getParentSynsetId());
        }
        //for(SynsetRelationEntity relation : entity.getSynsetAttributeId().getRelationParent()){
        //    ids.add(relation.getChildSynsetId());
        //}

        bookmarked = Settings.isSenseBookmarked(getApplicationContext(),entity.getSenseId());

        if(!bookmarked){
            Animation slideAnimation = (Animation) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.remove_bookmark_tape_no_anim);
            bookmark_tape.startAnimation(slideAnimation);
            slideAnimation.setFillAfter(true);
        }

        retrieveSelectedSensesTask = new RetrieveSensesBySynsetsTask(this,getApplicationContext()).execute(ids.toString());
        retrieveSynonymsTask = new RetrieveSynonymsTask(this,getApplicationContext()).execute(entity.getSynsetId().getSynsetId().toString());
        if(word_related_senses==null){
            retrieveWordRelatedSensesTask = new RetrieveWordRelatedSensesTask(this,getApplicationContext())
                    .execute(entity.getWordId().getWord(),
                            entity.getLexiconId().getLanguageName(),
                            entity.getPartOfSpeechId().getPartOfSpeechId() + "");
        }
        bookmark_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bookmarked){
                    Animation rotateAnimation = (Animation) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.remove_bookmark_anim);
                    bookmark_button.startAnimation(rotateAnimation);
                    Animation slideAnimation = (Animation) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.remove_bookmark_tape_anim);
                    bookmark_tape.startAnimation(slideAnimation);
                    slideAnimation.setFillAfter(true);
                }
                else{
                    Animation rotateAnimation = (Animation) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.add_bookmark_anim);
                    bookmark_button.startAnimation(rotateAnimation);
                    Animation slideAnimation = (Animation) AnimationUtils.loadAnimation(getApplicationContext(),R.anim.add_bookmark_tape_anim);
                    slideAnimation.setFillBefore(true);
                    bookmark_tape.startAnimation(slideAnimation);
                    //slideAnimation.setFillAfter(true);
                }
                Settings.addOrRemoveBookmark(getApplication(),entity.getSenseId());
                bookmarked = !bookmarked;
            }
        });

        prepareLowerBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Settings.getLocaleName()!=this.curr_language) {
            recreate();
        }
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

        Map<Long,ArrayList<SynsetRelationEntity>> relations_grouped = new HashMap<>();

        for(SynsetRelationEntity relation : relations){
            if(!relations_grouped.containsKey(relation.getSynsetRelationTypeId().getRelationTypeId())){
                relations_grouped.put(relation.getSynsetRelationTypeId().getRelationTypeId(), new ArrayList<SynsetRelationEntity>());
            }
            ArrayList<SynsetRelationEntity> list = relations_grouped.get(relation.getSynsetRelationTypeId().getRelationTypeId());
            list.add(relation);
        }
        ArrayList<Long> keys = new ArrayList<Long>();
        keys.addAll(relations_grouped.keySet());
        Collections.sort(keys);
        for(Long key : keys){
            ArrayList<SynsetRelationEntity> list = relations_grouped.get(key);
            if(getResources().getIdentifier("rel_type_" + list.get(0).getSynsetRelationTypeId().getRelationTypeId(),"string",getPackageName())!=0) {
                RelativeLayout type = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.relation_type_template, null);
                ((TextView) type.findViewById(R.id.relation_type_name)).setText(RelationInterpreter.getRelationTypeLabel(getApplicationContext(),list.get(0).getSynsetRelationTypeId().getRelationTypeId(),getPackageName()));
                final LinearLayout cell_container = type.findViewById(R.id.relations_container);
                final ImageView expander = ((ImageView) type.findViewById(R.id.drawer_icon));
                //expander.setOnClickListener(new OnClickExpander(false,cell_container,expander,getApplicationContext()));
                type.findViewById(R.id.relative_type_header).setOnClickListener(new OnClickExpander(false,cell_container,expander,getApplicationContext()));
                boolean contains_senses = false;
                for (SynsetRelationEntity relation : list) {
                    RelativeLayout cell = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.relation_template, null);
                    Long other = (relation.getChildSynsetId().equals(entity.getSynsetId().getSynsetId()) ? relation.getParentSynsetId() : relation.getChildSynsetId());
                    final SenseEntity sense = findRelatedBySynsetId(other);
                    if(sense==null)
                        continue;
                    ((TextView) cell.findViewById(R.id.relation_sense_id)).setText(sense.getWordId().getWord());
                    ((ImageView) cell.findViewById(R.id.language_icon)).setImageResource(SenseAdapter.getFlagResource(
                            getApplicationContext(),
                            sense.getLexiconId().getLanguageName()));
                    String description = "";
                    if (!sense.getSenseAttributes().isEmpty()) {
                        description+=(SenseAdapter.shortenDescription(((ArrayList<SenseAttributeEntity>) sense.getSenseAttributes()).get(0).getDefinition()));
                    }
                    else if(!sense.getSynsetId().getSynsetAttributes().isEmpty()){
                        description+=(SenseAdapter.shortenDescription(((ArrayList<SynsetAttributeEntity>) sense.getSynsetId()
                                .getSynsetAttributes()).get(0).getDefinition()));
                    }
                    if(!description.isEmpty() && !"null".equals(description)) {
                        ((TextView) cell.findViewById(R.id.relation_description)).setText(description);
                    }
                    else {
                        String domain_label = getResources().getText(R.string.domain).toString().toLowerCase();
                        ((TextView) cell.findViewById(R.id.relation_description)).setText(domain_label + ": "
                                + LanguageManager.getStringByResourceName(getApplicationContext(), "dom_" + sense.getDomainId().getDomainId()));
                    }
                    cell_container.addView(cell);
                    cell.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), SenseViewActivity.class);
                            ArrayList<SenseEntity> word_related_senses = new ArrayList<SenseEntity>();
                            intent.putExtra("sense_entity", sense);
                            startActivity(intent);
                        }
                    });
                    contains_senses = true;
                }
                if(contains_senses) {
                    cell_container.setVisibility(View.GONE);
                    sense_relations_container.addView(type);
                }
            }

        }
    }

    private SenseEntity findRelatedById(Long id){
        for(SenseEntity sense : related){
            if(sense.getSenseId().equals(id)){
                return sense;
            }
        }
        return null;
    }

    private SenseEntity findRelatedBySynsetId(Long id){
        for(SenseEntity sense : related){
            if(sense.getSynsetId().getSynsetId().equals(id)){
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


        sense_word.setText(entity.getWordId().getWord() + "-" + entity.getVariant());
        sense_part_of_speech.setText(SenseAdapter.getPartOfSpeechString(entity.getPartOfSpeechId().getPartOfSpeechId(), getApplicationContext()).toUpperCase());
        String language = entity.getLexiconId().getLanguageName();
        language_flag.setImageResource(SenseAdapter.getFlagResource(getApplicationContext(),language));
        if(SenseAdapter.checkIfContainsSenseAttributes(entity)) {
            sense_description.setText(((ArrayList<SenseAttributeEntity>) entity.getSenseAttributes()).get(0).getDefinition());
        }
        else if(SenseAdapter.checkIfContainsSynsetAttributes(entity)){
            sense_description.setText(((ArrayList<SynsetAttributeEntity>) entity.getSynsetId().getSynsetAttributes()).get(0).getDefinition());
        }
        else{
            sense_description.setText("");
            sense_description.setVisibility(View.GONE);
        }
        sense_domain.setText(LanguageManager.getStringByResourceName(getApplicationContext(),"dom_" + entity.getDomainId().getDomainId()));
        sense_source.setText(entity.getLexiconId().getName());

        setExamples(sense_examples_container);

        setEmotionalAnnotations(annotation_container);



    }

    private void setExamples(LinearLayout sense_examples_container){
        if(!entity.getSenseAttributes().isEmpty() && ((ArrayList<SenseAttributeEntity>)entity.getSenseAttributes()).get(0).getSenseExamples() != null
                && !((ArrayList<SenseAttributeEntity>)entity.getSenseAttributes()).get(0).getSenseExamples().isEmpty()){
            ArrayList<SenseExampleEntity> examples = (ArrayList<SenseExampleEntity>)((ArrayList<SenseAttributeEntity>)entity.getSenseAttributes())
                    .get(0).getSenseExamples();
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
        else if(!entity.getSynsetId().getSynsetAttributes().isEmpty()){
            ArrayList<SynsetExampleEntity> examples = (ArrayList<SynsetExampleEntity>)((ArrayList<SynsetAttributeEntity>)entity.getSynsetId()
                    .getSynsetAttributes()).get(0).getSynsetExamples();
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
        else{
            ((RelativeLayout)findViewById(R.id.examples_row)).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.sense_attribute_examples)).setVisibility(View.GONE);
            sense_examples_container.setVisibility(View.GONE);
        }
    }

    private void setEmotionalAnnotations(final LinearLayout annotation_container){
        boolean no_emotions = true;
        final ImageView expander = ((ImageView) findViewById(R.id.drawer_icon));
        findViewById(R.id.emotional_annotations_header).setOnClickListener(new OnClickExpander(false,annotation_container,expander,getApplicationContext(),0,2));
        //expander.setOnClickListener(new OnClickExpander(false,annotation_container,expander,getApplicationContext(),3,3));

        for(EmotionalAnnotationEntity emo : entity.getEmotionalAnnotations()){
            RelativeLayout annotation = (RelativeLayout)LayoutInflater.from(getApplicationContext()).inflate(R.layout.emotional_annotation_template, null);
            ConstraintLayout annotation_attributes = annotation.findViewById(R.id.annotation_attributes);
            if(emo.isHasEmotionalCharacteristic()) {
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
                    emotions_value.setText(EmotionalAnnotationsInterpreter.interpretEmotions(emo.getEmotions()));
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
                    valuations_value.setText(EmotionalAnnotationsInterpreter.interpretValuations(emo.getValuations()));
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
        annotation_container.setVisibility(View.GONE);
        if(entity.getEmotionalAnnotations().size()==0 || no_emotions){
            ((RelativeLayout)findViewById(R.id.emotional_annotations_view)).setVisibility(View.GONE);
        }
    }

    public void setSynonyms(ArrayList<SenseEntity> synonyms){
        Collections.sort((ArrayList<SenseEntity>) synonyms);
        this.synonyms=synonyms;
        LinearLayout synonyms_container = findViewById(R.id.sense_synonyms_container);
        if(synonyms.size()<2){
            ((RelativeLayout)findViewById(R.id.synonyms_row)).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.sense_attribute_synonyms)).setVisibility(View.GONE);
            synonyms_container.setVisibility(View.GONE);
        }
        else {
            ((TextView)findViewById(R.id.sense_attribute_synonyms)).setVisibility(View.VISIBLE);
            for (final SenseEntity synonym : synonyms) {
                if (!synonym.getWordId().getWord().equals(entity.getWordId().getWord())) {
                    RelativeLayout synonym_cell = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.synonym_template, null);
                    ((TextView) synonym_cell.findViewById(R.id.synonym_name)).setText(synonym.getWordId().getWord() + "-" + synonym.getVariant());
                    if (!synonym.getSenseAttributes().isEmpty()) {
                        ((TextView) synonym_cell.findViewById(R.id.synonym_description)).setText(
                                ((ArrayList<SenseAttributeEntity>) synonym.getSenseAttributes()).get(0).getDefinition());
                    }
                    else if(!synonym.getSynsetId().getSynsetAttributes().isEmpty()) {
                        ((TextView) synonym_cell.findViewById(R.id.synonym_description)).setText(
                                ((ArrayList<SynsetAttributeEntity>) synonym.getSynsetId().getSynsetAttributes()).get(0).getDefinition());
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
        Collections.sort((ArrayList<SenseEntity>) word_related_senses);
        this.word_related_senses=word_related_senses;
        Button other_senses = findViewById(R.id.other_senses_button);
        other_senses.setText(entity.getVariant() + "/" + word_related_senses.size());
        other_senses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(SenseViewActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.other_senses_popup, null);
                //((TextView) convertView.findViewById(R.id.title)).setText(R.string.other_senses);
                ListView lv = (ListView) convertView.findViewById(R.id.other_senses_list);
                ImageButton close_button = (ImageButton) convertView.findViewById(R.id.close_button);
                SenseAdapter adapter = new SenseAdapter(getApplicationContext(),word_related_senses);
                lv.setAdapter(adapter);
                builder.setView(convertView);
                final AlertDialog dialog = builder.create();
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
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
                close_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
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
                if(word_related_senses!=null) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(SenseViewActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View convertView = (View) inflater.inflate(R.layout.other_senses_popup, null);
                    //((TextView) convertView.findViewById(R.id.title)).setText(R.string.other_senses);
                    ListView lv = (ListView) convertView.findViewById(R.id.other_senses_list);
                    ImageButton close_button = (ImageButton) convertView.findViewById(R.id.close_button);
                    SenseAdapter adapter = new SenseAdapter(getApplicationContext(), word_related_senses);
                    lv.setAdapter(adapter);
                    builder.setView(convertView);
                    final AlertDialog dialog = builder.create();
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.show();
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(getApplicationContext(), SenseViewActivity.class);
                            intent.putExtra("sense_entity", word_related_senses.get(i));
                            intent.putExtra("word_related_senses", word_related_senses);
                            dialog.dismiss();
                            startActivity(intent);
                        }
                    });
                    close_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.not_loaded_yet), Toast.LENGTH_LONG).show();
                }
            }
        });

        view_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Settings.isOfflineMode()){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(SenseViewActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View convertView = (View) inflater.inflate(R.layout.warning_popup, null);
                    Button ok_button = (Button) convertView.findViewById(R.id.ok_button);
                    TextView title = (TextView) convertView.findViewById(R.id.title);
                    TextView reason = (TextView) convertView.findViewById(R.id.reason);
                    title.setText(R.string.connection_required);
                    reason.setText(R.string.connection_required_reason);
                    ok_button.setText(R.string.ok_text);
                    builder.setView(convertView);
                    final AlertDialog dialog = builder.create();
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.show();
                    ok_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), GraphBrowserActivity.class);
                    intent.putExtra("synset_id", entity.getSynsetId().getSynsetId().longValue());
                    startActivity(intent);
                }
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

    public void showWarningPopup(){
        if(!isPopupUp) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(SenseViewActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            View convertView = (View) inflater.inflate(R.layout.warning_popup, null);
            Button ok_button = (Button) convertView.findViewById(R.id.ok_button);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            TextView reason = (TextView) convertView.findViewById(R.id.reason);
            title.setText(R.string.local_db_error_title);
            reason.setText(R.string.local_db_error_content);
            ok_button.setText(R.string.ok_text);
            builder.setView(convertView);
            final AlertDialog dialog = builder.create();
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            isPopupUp = true;
            dialog.show();
            ok_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isPopupUp = false;
                    dialog.dismiss();
                }
            });
        }
    }
}
