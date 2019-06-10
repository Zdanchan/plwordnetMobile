package com.pwr.bzapps.plwordnetmobile.database.entity.grammar;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;

import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `name_id` bigint(20) DEFAULT NULL COMMENT 'Name of part of speech',
 *   `color` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL COMMENT 'Color displayed on visualisation',
 * */
@Table(name = "part_of_speech", id = "id")
public class PartOfSpeechEntity extends Model implements Entity, Serializable {
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "name_id")
    private Long nameId;
    @Column(name = "color")
    private String color;

    public Long getPartOfSpeechId() {
        return id;
    }

    public void setPartOfSpeechId(Long id) {
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
        this.color = "null".equals(color) ? null : color;
    }

    @Override
    public String getEntityID() {
        return "POS:" + getPartOfSpeechId();
    }
}
