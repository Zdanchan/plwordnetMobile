package com.pwr.bzapps.plwordnetmobile.database.entity.relation;


import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.PartOfSpeechEntity;

import java.io.Serializable;

/**
 *   `relation_type_id` bigint(20) NOT NULL,
 *   `part_of_speech_id` bigint(20) NOT NULL,
 * */
public class RelationTypeAllowedPartOfSpeechEntity implements Serializable, Entity {
    private RelationTypeEntity relationTypeId;
    private PartOfSpeechEntity partOfSpeechId;

    public RelationTypeEntity getRelationTypeId() {
        return relationTypeId;
    }

    public void setRelationTypeId(RelationTypeEntity relationTypeId) {
        this.relationTypeId = relationTypeId;
    }

    public PartOfSpeechEntity getPartOfSpeechId() {
        return partOfSpeechId;
    }

    public void setPartOfSpeechId(PartOfSpeechEntity partOfSpeechId) {
        this.partOfSpeechId = partOfSpeechId;
    }

    @Override
    public String getEntityID() {
        return "RTAPOS:" + relationTypeId.getId() + "" + partOfSpeechId.getId();
    }
}
