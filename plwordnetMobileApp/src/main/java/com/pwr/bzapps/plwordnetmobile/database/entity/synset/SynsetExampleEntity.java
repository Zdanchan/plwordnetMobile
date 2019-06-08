package com.pwr.bzapps.plwordnetmobile.database.entity.synset;



import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;

import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `synset_attributes_id` bigint(20) NOT NULL,
 *   `example` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `type` varchar(30) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
 * */
@Table(name = "synset_examples", id = "id")
public class SynsetExampleEntity extends Model implements Entity, Serializable {
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "synset_attributes_id")
    private Long synsetAttributeId;
    @Column(name = "example")
    private String example;
    @Column(name = "type")
    private String type;

    public Long getSynsetExampleId() {
        return id;
    }

    public void setSynsetExampleId(Long id) {
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
        return "SynEx:" + getSynsetExampleId();
    }
}
