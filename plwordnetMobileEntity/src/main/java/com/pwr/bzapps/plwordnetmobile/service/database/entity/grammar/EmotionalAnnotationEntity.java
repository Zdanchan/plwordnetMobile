package com.pwr.bzapps.plwordnetmobile.service.database.entity.grammar;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.sense.SenseEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `sense_id` bigint(20) NOT NULL,
 *   `has_emotional_characteristic` bit(1) NOT NULL DEFAULT b'0',
 *   `super_anotation` bit(1) NOT NULL DEFAULT b'0',
 *   `emotions` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
 *   `valuations` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
 *   `markedness` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
 *   `example1` varchar(512) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
 *   `example2` varchar(512) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
 * */
@Entity
@Table(name = "emotional_annotations")
public class EmotionalAnnotationEntity implements com.pwr.bzapps.plwordnetmobile.service.database.entity.Entity, Serializable {
    @Id
    private Integer id;
    @Column(name = "sense_id")
    private Integer sense_id;
    @Column(name = "has_emotional_characteristic")
    private boolean has_emotional_characteristic;
    @Column(name = "super_anotation")
    private boolean super_anotation;
    @Column(name = "emotions")
    private String emotions;
    @Column(name = "valuations")
    private String valuations;
    @Column(name = "markedness")
    private String markedness;
    @Column(name = "example1")
    private String example1;
    @Column(name = "example2")
    private String example2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSense_id() {
        return sense_id;
    }

    public void setSense_id(Integer sense_id) {
        this.sense_id = sense_id;
    }

    public boolean isHas_emotional_characteristic() {
        return has_emotional_characteristic;
    }

    public void setHas_emotional_characteristic(boolean has_emotional_characteristic) {
        this.has_emotional_characteristic = has_emotional_characteristic;
    }

    public boolean isSuper_anotation() {
        return super_anotation;
    }

    public void setSuper_anotation(boolean super_anotation) {
        this.super_anotation = super_anotation;
    }

    public String getEmotions() {
        return emotions;
    }

    public void setEmotions(String emotions) {
        this.emotions = "null".equals(emotions) ? null : emotions;
    }

    public String getValuations() {
        return valuations;
    }

    public void setValuations(String valuations) {
        this.valuations = "null".equals(valuations) ? null : valuations;
    }

    public String getMarkedness() {
        return markedness;
    }

    public void setMarkedness(String markedness) {
        this.markedness = "null".equals(markedness) ? null : markedness;
    }

    public String getExample1() {
        return example1;
    }

    public void setExample1(String example1) {
        this.example1 = "null".equals(example1) ? null : example1;
    }

    public String getExample2() {
        return example2;
    }

    public void setExample2(String example2) {
        this.example2 = "null".equals(example2) ? null : example2;
    }

    @Override
    public String getEntityID() {
        return "EA:" + getId();
    }
}
