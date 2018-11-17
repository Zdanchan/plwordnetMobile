package com.pwr.bzapps.plwordnetmobile.database.aggregator;

import android.content.Context;

import com.pwr.bzapps.plwordnetmobile.R;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.WordEntity;

public class WordAggregator {
    private Context context;
    private SenseEntity senseEntity;
    private WordEntity wordEntity;

    //temporary attributes
    private Long id;
    private String name;
    private Integer variant;
    private String description;
    private String shortDescription;
    private Long partOfSpeechId;
    private Integer language;

    public WordAggregator(){ }

    public WordAggregator(Context context){
        this.context=context;
    }

    public String getPartOfSpeechString() {
        switch((int)getPartOfSpeechId().longValue()){
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

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVariant() {
        return variant;
    }

    public void setVariant(Integer meaning) {
        this.variant = meaning;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Long getPartOfSpeechId() {
        return partOfSpeechId;
    }

    public void setPartOfSpeechId(Long partOfSpeechId) {
        this.partOfSpeechId = partOfSpeechId;
    }

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }
}
