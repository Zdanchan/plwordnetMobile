package com.pwr.bzapps.plwordnetmobile.database.entity.relation;


import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.LexiconEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.id.RelationTypeAllowedLexiconId;

import java.io.Serializable;

/**
 *   `relation_type_id` bigint(20) NOT NULL,
 *   `lexicon_id` bigint(20) NOT NULL,
 * */
@android.arch.persistence.room.Entity(tableName = "relation_type_allowed_lexicons")
public class RelationTypeAllowedLexiconEntity implements Serializable,Entity {
    @PrimaryKey
    @Embedded
    @NonNull
    private RelationTypeAllowedLexiconId id;

    public RelationTypeAllowedLexiconId getId() {
        return id;
    }

    public void setId(RelationTypeAllowedLexiconId id) {
        this.id = id;
    }

    public Long getRelationTypeId() {
        return id.getRelation_type_id();
    }

    public void setRelationTypeId(RelationTypeEntity relationTypeId) {
        if(id==null)
            id = new RelationTypeAllowedLexiconId();
        this.id.setRelation_type_id(relationTypeId.getId());
    }

    public Long getLexiconId() {
        return id.getLexicon_id();
    }

    public void setLexiconId(LexiconEntity lexiconId) {
        if(id==null)
            id = new RelationTypeAllowedLexiconId();
        this.id.setLexicon_id(lexiconId.getId());
    }

    @Override
    public String getEntityID() {
        return "RTAL:" + getRelationTypeId() + "" + getLexiconId();
    }
}
