package com.pwr.bzapps.plwordnetmobile.service.database.entity.sense;

import javax.persistence.*;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `sense_attribute_id` bigint(20) NOT NULL,
 *   `example` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `type` varchar(30) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
 * */
@Entity
@Table(name = "sense_examples")
public class SenseExampleEntity {
    @Id
    private Long id;
    @Column(name = "sense_attribute_id")
    private Long senseAttributeId;
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

    public Long getSenseAttributeId() {
        return senseAttributeId;
    }

    public void setSenseAttributeId(Long senseAttributeId) {
        this.senseAttributeId = senseAttributeId;
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
        string+="SenExE{";
        string+="id:" + id + ";";
        string+="senseAttributeId:" + senseAttributeId + ";";
        string+="example:" + example + ";";
        string+="type:" + type;
        string+="}SenExE";

        return string;
    }
}
