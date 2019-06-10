package com.pwr.bzapps.plwordnetmobile.database.entity.relation.id;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class RelationTypeAllowedLexiconId implements Serializable {
    @NonNull
    private Long relation_type_id;
    @NonNull
    private Long lexicon_id;

    public Long getRelation_type_id() {
        return relation_type_id;
    }

    public void setRelation_type_id(Long relation_type_id) {
        this.relation_type_id = relation_type_id;
    }

    public Long getLexicon_id() {
        return lexicon_id;
    }

    public void setLexicon_id(Long lexicon_id) {
        this.lexicon_id = lexicon_id;
    }
}
