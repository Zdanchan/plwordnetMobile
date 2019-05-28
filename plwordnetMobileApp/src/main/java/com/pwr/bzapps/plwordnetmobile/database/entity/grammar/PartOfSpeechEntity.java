package com.pwr.bzapps.plwordnetmobile.database.entity.grammar;


import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;

import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `name_id` bigint(20) DEFAULT NULL COMMENT 'Name of part of speech',
 *   `color` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL COMMENT 'Color displayed on visualisation',
 * */
public class PartOfSpeechEntity implements Entity, Serializable {
    private Integer id;
    private Integer nameId;
    private String color;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNameId() {
        return nameId;
    }

    public void setNameId(Integer nameId) {
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
        return "POS:" + getId();
    }
}
