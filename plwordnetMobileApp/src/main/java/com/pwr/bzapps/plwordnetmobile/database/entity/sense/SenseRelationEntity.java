package com.pwr.bzapps.plwordnetmobile.database.entity.sense;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeEntity;

import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `child_sense_id` bigint(20) NOT NULL,
 *   `parent_sense_id` bigint(20) NOT NULL,
 *   `relation_type_id` bigint(20) NOT NULL,
 * */
@Table(name = "sense_relation", id = "id")
public class SenseRelationEntity extends Model implements Entity, Serializable {
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "child_sense_id")
    private Long childSenseId;
    @Column(name = "parent_sense_id")
    private Long parentSenseId;
    @Column(name = "relation_type_id")
    private RelationTypeEntity relationTypeId;

    public Long getSenseRelationId() {
        return id;
    }

    public void setSenseRelationId(Long id) {
        this.id = id;
    }

    public Long getChildSenseId() {
        return childSenseId;
    }

    public void setChildSenseId(Long childSenseId) {
        this.childSenseId = childSenseId;
    }

    public Long getParentSenseId() {
        return parentSenseId;
    }

    public void setParentSenseId(Long parentSenseId) {
        this.parentSenseId = parentSenseId;
    }

    public RelationTypeEntity getRelationTypeId() {
        return relationTypeId;
    }

    public void setRelationTypeId(RelationTypeEntity relationTypeId) {
        this.relationTypeId = relationTypeId;
    }

    @Override
    public String getEntityID() {
        return "SeR:" + getSenseRelationId();
    }
}
