package com.pwr.bzapps.plwordnetmobile.database.entity.synset;


import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.LexiconEntity;

import java.io.Serializable;
import java.util.Collection;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `split` int(11) DEFAULT NULL COMMENT 'Position of line splitting synset head',
 *   `lexicon_id` bigint(20) NOT NULL,
 *   `status_id` bigint(20) DEFAULT NULL,
 *   `abstract` tinyint(1) DEFAULT NULL COMMENT 'is synset abstract',
 * */
public class SynsetEntity implements Entity, Serializable {
    private Integer id;
    private Integer split;
    private LexiconEntity lexiconId;
    private Integer statusId;
    private Short abstract_;

    private Collection<SynsetRelationEntity> relationChild;
    private Collection<SynsetRelationEntity> relationParent;
    private Collection<SynsetAttributeEntity> synsetAttributes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSplit() {
        return split;
    }

    public void setSplit(Integer split) {
        this.split = split;
    }

    public LexiconEntity getLexiconId() {
        return lexiconId;
    }

    public void setLexiconId(LexiconEntity lexiconId) {
        this.lexiconId = lexiconId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Short getAbstract() {
        return abstract_;
    }

    public void setAbstract(Short abstract_) {
        this.abstract_ = abstract_;
    }

    public Collection<SynsetRelationEntity> getRelationChild() {
        return relationChild;
    }

    public void setRelationChild(Collection<SynsetRelationEntity> relationChild) {
        this.relationChild = relationChild;
    }

    public Collection<SynsetRelationEntity> getRelationParent() {
        return relationParent;
    }

    public void setRelationParent(Collection<SynsetRelationEntity> relationParent) {
        this.relationParent = relationParent;
    }

    public Collection<SynsetAttributeEntity> getSynsetAttributes() {
        return synsetAttributes;
    }

    public void setSynsetAttributes(Collection<SynsetAttributeEntity> synsetAttributes) {
        this.synsetAttributes = synsetAttributes;
    }

    @Override
    public String getEntityID() {
        return "Sy:" + getId();
    }
}
