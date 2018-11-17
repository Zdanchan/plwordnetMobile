package com.pwr.bzapps.plwordnetmobile.database.entity.sense;


import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;

import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `sense_attribute_id` bigint(20) NOT NULL,
 *   `example` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `type` varchar(30) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
 * */
public class SenseExampleEntity implements Entity, Serializable {
    private Integer id;
    private Integer sense_attribute_id;
    private String example;
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSense_attribute_id() {
        return sense_attribute_id;
    }

    public void setSense_attribute_id(Integer sense_attribute_id) {
        this.sense_attribute_id = sense_attribute_id;
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
        return "SeEx:" + getId();
    }
}
