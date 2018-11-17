package com.pwr.bzapps.plwordnetmobile.database.entity.relation;


import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.LexiconEntity;

import java.io.Serializable;

/**
 *   `relation_type_id` bigint(20) NOT NULL,
 *   `lexicon_id` bigint(20) NOT NULL,
 * */
public class RelationTypeAllowedLexiconEntity implements Serializable,Entity {
    private RelationTypeEntity relation_type_id;
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

    @Override
    public String getEntityID() {
        return "RTAL:" + relation_type_id.getId() + "" + lexicon_id.getId();
    }
}
