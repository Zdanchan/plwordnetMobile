package com.pwr.bzapps.plwordnetmobile.database.entity.relation;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.PartOfSpeechEntity;

import java.io.Serializable;

/**
 *   `relation_type_id` bigint(20) NOT NULL,
 *   `part_of_speech_id` bigint(20) NOT NULL,
 * */
@Table(name = "relation_type_allowed_parts_of_speech", id = "id")
public class RelationTypeAllowedPartOfSpeechEntity extends Model implements Serializable, Entity {
    @Column(name = "relation_type_id")
    private RelationTypeEntity relationTypeId;
    @Column(name = "part_of_speech_id")
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
        return "RTAPOS:" + relationTypeId.getRelationTypeId() + "" + partOfSpeechId.getPartOfSpeechId();
    }
}
