package com.pwr.bzapps.plwordnetmobile.database.entity.sense;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.RoomWarnings;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeEntity;

import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `child_sense_id` bigint(20) NOT NULL,
 *   `parent_sense_id` bigint(20) NOT NULL,
 *   `relation_type_id` bigint(20) NOT NULL,
 * */
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
@android.arch.persistence.room.Entity(tableName = "sense_relation")
public class SenseRelationEntity implements Entity, Serializable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "child_sense_id")
    private Long childSenseId;
    @ColumnInfo(name = "parent_sense_id")
    private Long parentSenseId;
    @Embedded(prefix = "relation_type_")
    private RelationTypeEntity relationTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        return "SeR:" + getId();
    }
}
