package com.pwr.bzapps.plwordnetmobile.database.entity.relation;


import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.PartOfSpeechEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.id.RelationTypeAllowedPartOfSpeechId;

import java.io.Serializable;

/**
 *   `relation_type_id` bigint(20) NOT NULL,
 *   `part_of_speech_id` bigint(20) NOT NULL,
 * */
@android.arch.persistence.room.Entity(tableName = "relation_type_allowed_parts_of_speech")
public class RelationTypeAllowedPartOfSpeechEntity implements Serializable, Entity {
    @PrimaryKey
    @Embedded
    @NonNull
    private RelationTypeAllowedPartOfSpeechId id;

    public RelationTypeAllowedPartOfSpeechId getId() {
        return id;
    }

    public void setId(RelationTypeAllowedPartOfSpeechId id) {
        this.id = id;
    }

    public Long getRelationTypeId() {
        return id.getRelation_type_id();
    }

    public void setRelationTypeId(RelationTypeEntity relationTypeId) {
        if(id==null)
            id = new RelationTypeAllowedPartOfSpeechId();
        this.id.setRelation_type_id(relationTypeId.getId());
    }

    public Long getPartOfSpeechId() {
        return id.getPart_of_speech_id();
    }

    public void setPartOfSpeechId(PartOfSpeechEntity partOfSpeechId) {
        if(id==null)
            id = new RelationTypeAllowedPartOfSpeechId();
        this.id.setPart_of_speech_id(partOfSpeechId.getId());
    }

    @Override
    public String getEntityID() {
        return "RTAPOS:" + getRelationTypeId() + "" + getPartOfSpeechId();
    }
}
