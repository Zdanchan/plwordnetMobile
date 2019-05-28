package com.pwr.bzapps.plwordnetmobile.database.entity.relation;


import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.LexiconEntity;

import java.io.Serializable;

/**
 *   `relation_type_id` bigint(20) NOT NULL,
 *   `lexicon_id` bigint(20) NOT NULL,
 * */
public class RelationTypeAllowedLexiconEntity implements Serializable,Entity {
    private RelationTypeEntity relationTypeId;
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
        return "RTAL:" + relationTypeId.getId() + "" + lexiconId.getId();
    }
}
