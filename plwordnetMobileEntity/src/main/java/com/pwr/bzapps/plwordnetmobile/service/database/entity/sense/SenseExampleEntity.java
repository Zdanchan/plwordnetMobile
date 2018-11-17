package com.pwr.bzapps.plwordnetmobile.service.database.entity.sense;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `sense_attribute_id` bigint(20) NOT NULL,
 *   `example` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `type` varchar(30) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
 * */
@Entity
@Table(name = "sense_examples")
public class SenseExampleEntity implements com.pwr.bzapps.plwordnetmobile.service.database.entity.Entity, Serializable {
    @Id
    private Integer id;
    @Column(name = "sense_attribute_id")
    private Integer sense_attribute_id;
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
