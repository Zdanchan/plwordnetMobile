package com.pwr.bzapps.plwordnetmobile.database.entity.synset;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;

import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `synset_attributes_id` bigint(20) NOT NULL,
 *   `example` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `type` varchar(30) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
 * */
@android.arch.persistence.room.Entity(tableName = "synset_examples")
public class SynsetExampleEntity implements Entity, Serializable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "synset_attributes_id")
    private Long synsetAttributeId;
    @ColumnInfo(name = "example")
    private String example;
    @ColumnInfo(name = "type")
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSynsetAttributeId() {
        return synsetAttributeId;
    }

    public void setSynsetAttributeId(Long synsetAttributeId) {
        this.synsetAttributeId = synsetAttributeId;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = "null".equals(example) ? null : example;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = "null".equals(type) ? null : type;
    }

    @Override
    public String getEntityID() {
        return "SynEx:" + getId();
    }
}
