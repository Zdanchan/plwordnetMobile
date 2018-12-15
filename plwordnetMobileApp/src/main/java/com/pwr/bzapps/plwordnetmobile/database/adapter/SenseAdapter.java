package com.pwr.bzapps.plwordnetmobile.database.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseAttributeEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetAttributeEntity;

import java.util.ArrayList;

public class SenseAdapter extends ArrayAdapter<SenseEntity> implements View.OnClickListener {

    private Context context;
    private ArrayList<SenseEntity> data;


    public SenseAdapter(Context context, ArrayList<SenseEntity> data){
        super(context, R.layout.result_row_template, data);
        this.context=context;
        this.data=data;
    }

    public ArrayList<SenseEntity> getData(){
        return data;
    }

    @Override
    public void onClick(View v){

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SenseEntity senseEntity = getItem(position);
        ResultRowItem rowResultItem = new ResultRowItem();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.result_row_template, parent, false);
        rowResultItem.word_name = ((TextView) convertView.findViewById(R.id.word_name));
        rowResultItem.word_meaning_id = ((TextView) convertView.findViewById(R.id.word_meaning_id));
        rowResultItem.part_of_speech = ((TextView) convertView.findViewById(R.id.part_of_speech));
        rowResultItem.short_description = ((TextView) convertView.findViewById(R.id.short_description));
        rowResultItem.language_icon = ((ImageView) convertView.findViewById(R.id.language_icon));


        rowResultItem.word_name.setText(senseEntity.getWord_id().getWord() + "-" + senseEntity.getVariant());
        //rowResultItem.word_meaning_id.setText("-" + senseEntity.getVariant());
        rowResultItem.part_of_speech.setText(getPartOfSpeechString(senseEntity.getPart_of_speech_id().getId()));
        if(senseEntity.getSense_attributes().size()>0 && ((ArrayList<SenseAttributeEntity>)(senseEntity.getSense_attributes())).get(0)!=null){
            String description = "";
            if("Polish".equals(senseEntity.getLexicon_id().getLanguage_name())){
                description = shortenDescription(((ArrayList<SenseAttributeEntity>)(senseEntity.getSense_attributes())).get(0).getDefinition());
            }
            else {
                description = shortenDescription(((ArrayList<SynsetAttributeEntity>)(senseEntity.getSynset_id().getSynset_attributes())).get(0).getDefinition());
            }
            rowResultItem.short_description.setText(description);
        }
        rowResultItem.language_icon.setImageResource(getFlagResource(senseEntity.getLexicon_id().getLanguage_name()));
        return convertView;
    }

    public static int getFlagResource(String languageName){
        switch (languageName) {
            case "Polish":
                return R.drawable.flag_polish;
            case "English":
                return R.drawable.flag_english;
        }
        return R.drawable.flag_unknown;
    }

    private String getPartOfSpeechString(Integer id){
        return getPartOfSpeechString(id,context);
    }

    public static String getPartOfSpeechString(Integer id, Context context) {
        switch(id.intValue()){
            case 1:
                return context.getResources().getString(R.string.verb);
            case 2:
                return context.getResources().getString(R.string.noun);
            case 3:
                return context.getResources().getString(R.string.adverb);
            case 4:
                return context.getResources().getString(R.string.adjective);
        }

        return "Err_unrecognizedPartOfSpeech";
    }

    public static String shortenDescription(String description){
        if(description==null)
            return null;
        if(description.length()<=60)
            return description;
        int last_white_space = description.substring(0,60).lastIndexOf(" ");
        description = description.substring(0,last_white_space);
        if(description.charAt(description.length()-1) == ';' || description.charAt(description.length()-1) == ',' || description.charAt(description.length()-1) == '.'){
            description = description.substring(0,description.length()-1);
        }
        return description + "...";
    }

    private static class ResultRowItem{
        TextView word_name;
        TextView word_meaning_id;
        ImageView language_icon;
        TextView part_of_speech;
        TextView short_description;
    }
}
