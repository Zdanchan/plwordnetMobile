package com.pwr.bzapps.plwordnetmobile.service.database.entity.grammar;

import javax.persistence.*;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `name_id` bigint(20) DEFAULT NULL COMMENT 'Name of part of speech',
 *   `color` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL COMMENT 'Color displayed on visualisation',
 * */
@Entity
@Table(name = "part_of_speech")
public class PartOfSpeechEntity {
    @Id
    private Long id;
    @Column(name = "name_id")
    private Long nameId;
    @Column(name = "color")
    private String color;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNameId() {
        return nameId;
    }

    public void setNameId(Long nameId) {
        this.nameId = nameId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String toString(){
        String string = "";
        string+="POSE{";
        string+="id:" + id + ";";
        string+="nameId:" + nameId.toString() + ";";
        string+="color:" + color;
        string+="}POSE";

        return string;
    }
}
