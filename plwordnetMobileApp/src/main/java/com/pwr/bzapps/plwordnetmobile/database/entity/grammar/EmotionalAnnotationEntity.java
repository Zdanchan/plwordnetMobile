package com.pwr.bzapps.plwordnetmobile.database.entity.grammar;

import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;

import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `sense_id` bigint(20) NOT NULL,
 *   `hasEmotional_characteristic` bit(1) NOT NULL DEFAULT b'0',
 *   `super_anotation` bit(1) NOT NULL DEFAULT b'0',
 *   `emotions` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
 *   `valuations` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
 *   `markedness` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
 *   `example1` varchar(512) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
 *   `example2` varchar(512) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
 * */
public class EmotionalAnnotationEntity implements Entity, Serializable{
    private Integer id;
    private Integer senseId;
    private boolean hasEmotionalCharacteristic;
    private boolean superAnotation;
    private String emotions;
    private String valuations;
    private String markedness;
    private String example1;
    private String example2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSenseId() {
        return senseId;
    }

    public void setSenseId(Integer senseId) {
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
        return "EA:" + getId();
    }
}
