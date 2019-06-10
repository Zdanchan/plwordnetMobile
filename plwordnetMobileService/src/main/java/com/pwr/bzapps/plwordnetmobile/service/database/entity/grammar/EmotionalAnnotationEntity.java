package com.pwr.bzapps.plwordnetmobile.service.database.entity.grammar;

import javax.persistence.*;

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
public class EmotionalAnnotationEntity {
    @Id
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
        this.emotions = emotions;
    }

    public String getValuations() {
        return valuations;
    }

    public void setValuations(String valuations) {
        this.valuations = valuations;
    }

    public String getMarkedness() {
        return markedness;
    }

    public void setMarkedness(String markedness) {
        this.markedness = markedness;
    }

    public String getExample1() {
        return example1;
    }

    public void setExample1(String example1) {
        this.example1 = example1;
    }

    public String getExample2() {
        return example2;
    }

    public void setExample2(String example2) {
        this.example2 = example2;
    }

    public String toString(){
        String string = "";
        string+="EAE{";
        string+="id:" + id + ";";
        string+="senseId:" + senseId.toString() + ";";
        string+="hasEmotionalCharacteristic:" + hasEmotionalCharacteristic + ";";
        string+="superAnotation:" + superAnotation + ";";
        string+="emotions:" + emotions + ";";
        string+="valuations:" + valuations + ";";
        string+="markedness:" + markedness + ";";
        string+="example1:" + example1 + ";";
        string+="example2:" + example2;
        string+="}EAE";

        return string;
    }
}
