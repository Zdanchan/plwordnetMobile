package com.pwr.bzapps.plwordnetmobile.database.entity.grammar;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
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
@android.arch.persistence.room.Entity(tableName = "emotional_annotations")
public class EmotionalAnnotationEntity implements Entity, Serializable{
    @PrimaryKey
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "sense_id")
    private Long senseId;
    @ColumnInfo(name = "has_emotional_characteristic")
    private boolean hasEmotionalCharacteristic;
    @ColumnInfo(name = "super_anotation")
    private boolean superAnotation;
    @ColumnInfo(name = "emotions")
    private String emotions;
    @ColumnInfo(name = "valuations")
    private String valuations;
    @ColumnInfo(name = "markedness")
    private String markedness;
    @ColumnInfo(name = "example1")
    private String example1;
    @ColumnInfo(name = "example2")
    private String example2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        return "EA:" + getId();
    }
}
