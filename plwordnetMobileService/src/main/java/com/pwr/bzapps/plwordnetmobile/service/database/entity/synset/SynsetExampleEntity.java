package com.pwr.bzapps.plwordnetmobile.service.database.entity.synset;

import javax.persistence.*;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `synset_attributes_id` bigint(20) NOT NULL,
 *   `example` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `type` varchar(30) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
 * */
@Entity
@Table(name = "synset_examples")
public class SynsetExampleEntity {
    @Id
    private Long id;
    @Column(name = "synset_attributes_id")
    private Integer synsetAttributeId;
    @Column(name = "example")
    private String example;
    @Column(name = "type")
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        this.example = example;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString(){
        String string = "";
        string+="SynExE{";
        string+="id:" + id + ";";
        string+="synsetAttributeId:" + synsetAttributeId + ";";
        string+="example:" + example + ";";
        string+="type:" + type;
        string+="}SynExE";

        return string;
    }
}
