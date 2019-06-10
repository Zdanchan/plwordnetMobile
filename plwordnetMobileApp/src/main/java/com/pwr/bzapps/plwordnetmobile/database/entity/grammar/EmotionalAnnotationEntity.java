package com.pwr.bzapps.plwordnetmobile.database.entity.grammar;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;

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
@Table(name = "emotional_annotations", id = "id")
public class EmotionalAnnotationEntity extends Model implements Entity, Serializable{
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "sense_id")
    private Long senseId;
    @Column(name = "has_emotional_characteristic")
    private boolean hasEmotionalCharacteristic;
    @Column(name = "super_anotation")
    private boolean superAnotation;
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

    public Long getEmotionalAnnotationId() {
        return id;
    }

    public void setEmotionalAnnotationId(Long id) {
        this.id = id;
    }

    public Long getSenseId() {
        return senseId;
    }

    public void setSenseId(Long senseId) {
        this.senseId = senseId;
    }

    public boolean isHasEmotionalCharacteristic() {
        return hasEmotionalCharacteristic;
    }

    public void setHasEmotionalCharacteristic(boolean hasEmotionalCharacteristic) {
        this.hasEmotionalCharacteristic = hasEmotionalCharacteristic;
    }

    public boolean isSuperAnotation() {
        return superAnotation;
    }

    public void setSuperAnotation(boolean superAnotation) {
        this.superAnotation = superAnotation;
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
        return "EA:" + getEmotionalAnnotationId();
    }
}
