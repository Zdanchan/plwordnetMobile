package com.pwr.bzapps.plwordnetmobile.database.entity.sense;




import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;

import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `sense_attribute_id` bigint(20) NOT NULL,
 *   `example` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `type` varchar(30) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
 * */
@Table(name = "sense_examples", id = "id")
public class SenseExampleEntity extends Model implements Entity, Serializable {
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "sense_attribute_id")
    private Long senseAttributeId;
    @Column(name = "example")
    private String example;
    @Column(name = "type")
    private String type;

    public Long getSenseExampleId() {
        return id;
    }

    public void setSenseExampleId(Long id) {
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
        return "SeEx:" + getSenseExampleId();
    }
}
