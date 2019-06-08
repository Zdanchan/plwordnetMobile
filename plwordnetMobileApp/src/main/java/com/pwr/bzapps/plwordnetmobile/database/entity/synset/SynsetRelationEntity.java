package com.pwr.bzapps.plwordnetmobile.database.entity.synset;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeEntity;

import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `child_synset_id` bigint(20) NOT NULL,
 *   `parent_synset_id` bigint(20) NOT NULL,
 *   `synset_relation_type_id` bigint(20) NOT NULL,
 * */
@Table(name = "synset_relation", id = "id")
public class SynsetRelationEntity extends Model implements Entity, Serializable {
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "child_synset_id")
    private Long childSynsetId;
    @Column(name = "parent_synset_id")
    private Long parentSynsetId;
    @Column(name = "synset_relation_type_id")
    private RelationTypeEntity synsetRelationTypeId;

    public Long getSynsetRelationId() {
        return id;
    }

    public void setSYnsetRelationId(Long id) {
        this.id = id;
    }

    public Long getChildSynsetId() {
        return childSynsetId;
    }

    public void setChildSynsetId(Long childSynsetId) {
        this.childSynsetId = childSynsetId;
    }

    public Long getParentSynsetId() {
        return parentSynsetId;
    }

    public void setParentSynsetId(Long parentSynsetId) {
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
        return "SyR:" + getSynsetRelationId();
    }
}
