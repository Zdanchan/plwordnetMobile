package com.pwr.bzapps.plwordnetmobile.database.entity.relation.id;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class RelationTypeAllowedPartOfSpeechId implements Serializable {
    @NonNull
    private Long relation_type_id;
    @NonNull
    private Long part_of_speech_id;

    public Long getRelation_type_id() {
        return relation_type_id;
    }

    public void setRelation_type_id(Long relation_type_id) {
        this.relation_type_id = relation_type_id;
    }

    public Long getPart_of_speech_id() {
        return part_of_speech_id;
    }

    public void setPart_of_speech_id(Long part_of_speech_id) {
        this.part_of_speech_id = part_of_speech_id;
    }
}
