package com.pwr.bzapps.plwordnetmobile.database.entity.synset;

import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;

import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `synset_attributes_id` bigint(20) NOT NULL,
 *   `example` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `type` varchar(30) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
 * */
public class SynsetExampleEntity implements Entity, Serializable {
    private Integer id;
    private Integer synsetAttributeId;
    private String example;
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSynsetAttributeId() {
        return synsetAttributeId;
    }

    public void setSynsetAttributeId(Integer synsetAttributeId) {
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
