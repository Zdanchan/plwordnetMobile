package com.pwr.bzapps.plwordnetmobile.service.database.entity.synset;

import javax.persistence.*;
import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `synset_attributes_id` bigint(20) NOT NULL,
 *   `example` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `type` varchar(30) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
 * */
@Entity
@Table(name = "synset_examples")
public class SynsetExampleEntity implements com.pwr.bzapps.plwordnetmobile.service.database.entity.Entity, Serializable {
    @Id
    private Integer id;
    @Column(name = "synset_attributes_id")
    private Integer synset_attributes_id;
    @Column(name = "example")
    private String example;
    @Column(name = "type")
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSynset_attributes_id() {
        return synset_attributes_id;
    }

    public void setSynset_attributes_id(Integer synset_attributes_id) {
        this.synset_attributes_id = synset_attributes_id;
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
