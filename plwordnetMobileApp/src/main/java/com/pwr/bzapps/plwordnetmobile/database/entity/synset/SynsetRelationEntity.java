package com.pwr.bzapps.plwordnetmobile.database.entity.synset;


import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeEntity;

import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `child_synset_id` bigint(20) NOT NULL,
 *   `parent_synset_id` bigint(20) NOT NULL,
 *   `synset_relation_type_id` bigint(20) NOT NULL,
 * */
public class SynsetRelationEntity implements Entity, Serializable {
    private Integer id;
    private Integer childSynsetId;
    private Integer parentSynsetId;
    private RelationTypeEntity synsetRelationTypeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChildSynsetId() {
        return childSynsetId;
    }

    public void setChildSynsetId(Integer childSynsetId) {
        this.childSynsetId = childSynsetId;
    }

    public Integer getParentSynsetId() {
        return parentSynsetId;
    }

    public void setParentSynsetId(Integer parentSynsetId) {
        this.parentSynsetId = parentSynsetId;
    }

    public RelationTypeEntity getSynsetRelationTypeId() {
        return synsetRelationTypeId;
    }

    public void setSynsetRelationTypeId(RelationTypeEntity synsetRelationTypeId) {
        this.synsetRelationTypeId = synsetRelationTypeId;
    }

    @Override
    public String getEntityID() {
        return "SyR:" + getId();
    }
}
