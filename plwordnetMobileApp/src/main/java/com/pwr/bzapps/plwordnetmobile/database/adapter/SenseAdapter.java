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


        rowResultItem.word_name.setText(senseEntity.getWordId().getWord() + "-" + senseEntity.getVariant());
        //rowResultItem.word_meaning_id.setText("-" + senseEntity.getVariant());
        rowResultItem.part_of_speech.setText(getPartOfSpeechString(senseEntity.getPartOfSpeechId().getId()));
        String description = "";
        if(checkIfContainsSenseAttributes(senseEntity)) {
            description = shortenDescription(((ArrayList<SenseAttributeEntity>)(senseEntity.getSenseAttributes())).get(0).getDefinition());
        }
        else if(checkIfContainsSynsetAttributes(senseEntity)){
            description = shortenDescription(((ArrayList<SynsetAttributeEntity>)(senseEntity.getSynsetId().getSynsetAttributes())).get(0).getDefinition());
        }
        rowResultItem.short_description.setText(description);
        rowResultItem.language_icon.setImageResource(getFlagResource(context,senseEntity.getLexiconId().getLanguageName()));
        return convertView;
    }

    public static boolean checkIfContainsSenseAttributes(SenseEntity entity){
        if(!entity.getSenseAttributes().isEmpty()){
            if(((ArrayList<SenseAttributeEntity>)entity.getSenseAttributes()).get(0).getDefinition() != null) {
                if (!((ArrayList<SenseAttributeEntity>) entity.getSenseAttributes()).get(0).getDefinition().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkIfContainsSynsetAttributes(SenseEntity entity){
        if(!entity.getSynsetId().getSynsetAttributes().isEmpty()){
            if(((ArrayList<SynsetAttributeEntity>)entity.getSynsetId().getSynsetAttributes()).get(0).getDefinition() != null) {
                if (!((ArrayList<SynsetAttributeEntity>) entity.getSynsetId().getSynsetAttributes()).get(0).getDefinition().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int getFlagResource(Context context, String languageName){
        int flag_id = context.getResources().getIdentifier("flag_" + languageName.toLowerCase(),
                "drawable",context.getPackageName());
        //switch (languageName) {
        //    case "Polish":
        //        return R.drawable.flag_polish;
        //    case "English":
        //        return R.drawable.flag_english;
        //}
        return flag_id;
    }

    private String getPartOfSpeechString(Long id){
        return getPartOfSpeechString(id,context);
    }

    public static String getPartOfSpeechString(Long id, Context context) {
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
