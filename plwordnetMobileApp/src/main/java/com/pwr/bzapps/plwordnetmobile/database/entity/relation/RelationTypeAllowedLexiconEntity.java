package com.pwr.bzapps.plwordnetmobile.database.entity.relation;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.LexiconEntity;

import java.io.Serializable;

/**
 *   `relation_type_id` bigint(20) NOT NULL,
 *   `lexicon_id` bigint(20) NOT NULL,
 * */
@Table(name = "relation_type_allowed_lexicons", id = "id")
public class RelationTypeAllowedLexiconEntity extends Model implements Serializable,Entity {
    @Column(name = "relation_type_id")
    private RelationTypeEntity relationTypeId;
    @Column(name = "lexicon_id")
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

    @Override
    public String getEntityID() {
        return "RTAL:" + relationTypeId.getRelationTypeId() + "" + lexiconId.getLexiconId();
    }
}
