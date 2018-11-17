package com.pwr.bzapps.plwordnetmobile.database.entity.relation;


import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.PartOfSpeechEntity;

import java.io.Serializable;

/**
 *   `relation_type_id` bigint(20) NOT NULL,
 *   `part_of_speech_id` bigint(20) NOT NULL,
 * */
public class RelationTypeAllowedPartOfSpeechEntity implements Serializable, Entity {
    private RelationTypeEntity relation_type_id;
    private PartOfSpeechEntity part_of_speech_id;

    public RelationTypeEntity getRelation_type_id() {
        return relation_type_id;
    }

    public void setRelation_type_id(RelationTypeEntity relation_type_id) {
        this.relation_type_id = relation_type_id;
    }

    public PartOfSpeechEntity getPart_of_speech_id() {
        return part_of_speech_id;
    }

    public void setPart_of_speech_id(PartOfSpeechEntity part_of_speech_id) {
        this.part_of_speech_id = part_of_speech_id;
    }

    @Override
    public String getEntityID() {
        return "RTAPOS:" + relation_type_id.getId() + "" + part_of_speech_id.getId();
    }
}
