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
    private RelationTypeEntity relation_type_id;
    @Id
    @ManyToOne
    @JoinColumn(name = "lexicon_id", referencedColumnName = "id")
    private LexiconEntity lexicon_id;

    public RelationTypeEntity getRelation_type_id() {
        return relation_type_id;
    }

    public void setRelation_type_id(RelationTypeEntity relation_type_id) {
        this.relation_type_id = relation_type_id;
    }

    public LexiconEntity getLexicon_id() {
        return lexicon_id;
    }

    public void setLexicon_id(LexiconEntity lexicon_id) {
        this.lexicon_id = lexicon_id;
    }

    public String toString(){
        String string = "";
        string+="RTALE{";
        string+="relation_type_id:" + relation_type_id.toString() + ";";
        string+="lexicon_id:" + lexicon_id.toString();
        string+="}RTALE";

        return string;
    }
}
