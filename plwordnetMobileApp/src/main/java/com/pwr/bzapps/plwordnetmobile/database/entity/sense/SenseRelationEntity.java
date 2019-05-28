package com.pwr.bzapps.plwordnetmobile.database.entity.sense;


import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeEntity;

import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `child_sense_id` bigint(20) NOT NULL,
 *   `parent_sense_id` bigint(20) NOT NULL,
 *   `relation_type_id` bigint(20) NOT NULL,
 * */
public class SenseRelationEntity implements Entity, Serializable {
    private Integer id;
    private Integer childSenseId;
    private Integer parentSenseId;
    private RelationTypeEntity relationTypeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChildSenseId() {
        return childSenseId;
    }

    public void setChildSenseId(Integer childSenseId) {
        this.childSenseId = childSenseId;
    }

    public Integer getParentSenseId() {
        return parentSenseId;
    }

    public void setParentSenseId(Integer parentSenseId) {
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
        return "SeR:" + getId();
    }
}
