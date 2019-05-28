package com.pwr.bzapps.plwordnetmobile.service.database.entity.relation;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.LexiconEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.relation.id.RelationTypeAllowedLexiconId;

import javax.persistence.*;
import java.io.Serializable;

/**
 *   `relation_type_id` bigint(20) NOT NULL,
 *   `lexicon_id` bigint(20) NOT NULL,
 * */
@Entity
@Table(name = "relation_type_allowed_lexicons")
@IdClass(RelationTypeAllowedLexiconId.class)
public class RelationTypeAllowedLexiconEntity implements Serializable{
    @Id
    @ManyToOne
    @JoinColumn(name = "relation_type_id", referencedColumnName = "id")
    private RelationTypeEntity relationTypeId;
    @Id
    @ManyToOne
    @JoinColumn(name = "lexicon_id", referencedColumnName = "id")
    private LexiconEntity lexiconId;

    public RelationTypeEntity getRelationTypeId() {
        return relationTypeId;
    }

    public void setRelationTypeId(RelationTypeEntity relationTypeId) {
        this.relationTypeId = relationTypeId;
    }

    public LexiconEntity getLexiconId() {
        return lexiconId;
    }

    public void setLexiconId(LexiconEntity lexiconId) {
        this.lexiconId = lexiconId;
    }

    public String toString(){
        String string = "";
        string+="RTALE{";
        string+="relationTypeId:" + relationTypeId.toString() + ";";
        string+="lexiconId:" + lexiconId.toString();
        string+="}RTALE";

        return string;
    }
}
