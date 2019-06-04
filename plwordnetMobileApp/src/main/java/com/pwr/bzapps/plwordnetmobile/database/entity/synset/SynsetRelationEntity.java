package com.pwr.bzapps.plwordnetmobile.database.entity.synset;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.RoomWarnings;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeEntity;

import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `child_synset_id` bigint(20) NOT NULL,
 *   `parent_synset_id` bigint(20) NOT NULL,
 *   `synset_relation_type_id` bigint(20) NOT NULL,
 * */
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
@android.arch.persistence.room.Entity(tableName = "synset_relation")
public class SynsetRelationEntity implements Entity, Serializable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "child_synset_id")
    private Long childSynsetId;
    @ColumnInfo(name = "parent_synset_id")
    private Long parentSynsetId;
    @Embedded(prefix = "synset_relation_type_")
    private RelationTypeEntity synsetRelationTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        return "SyR:" + getId();
    }
}
