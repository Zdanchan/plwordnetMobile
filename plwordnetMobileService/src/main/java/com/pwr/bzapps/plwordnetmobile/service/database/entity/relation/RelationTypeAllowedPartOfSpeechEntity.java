package com.pwr.bzapps.plwordnetmobile.service.database.entity.relation;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.grammar.PartOfSpeechEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.relation.id.RelationTypeAllowedPartOfSpeechId;

import javax.persistence.*;
import java.io.Serializable;

/**
 *   `relation_type_id` bigint(20) NOT NULL,
 *   `part_of_speech_id` bigint(20) NOT NULL,
 * */
@Entity
@Table(name = "relation_type_allowed_parts_of_speech")
@IdClass(RelationTypeAllowedPartOfSpeechId.class)
public class RelationTypeAllowedPartOfSpeechEntity implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "relation_type_id", referencedColumnName = "id")
    private RelationTypeEntity relationTypeId;
    @Id
    @ManyToOne
    @JoinColumn(name = "part_of_speech_id", referencedColumnName = "id")
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

    public String toString(){
        String string = "";
        string+="RTAPOSE{";
        string+="relationTypeId:" + relationTypeId.toString() + ";";
        string+="lexicon_id:" + partOfSpeechId.toString();
        string+="}RTAPOSE";

        return string;
    }
}
