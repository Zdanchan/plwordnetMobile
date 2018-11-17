package com.pwr.bzapps.plwordnetmobile.service.database.entity.grammar;

import javax.persistence.*;
import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `name_id` bigint(20) DEFAULT NULL COMMENT 'Name of part of speech',
 *   `color` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL COMMENT 'Color displayed on visualisation',
 * */
@Entity
@Table(name = "part_of_speech")
public class PartOfSpeechEntity implements com.pwr.bzapps.plwordnetmobile.service.database.entity.Entity, Serializable {
    @Id
    private Integer id;
    @Column(name = "name_id")
    private Integer name_id;
    @Column(name = "color")
    private String color;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getName_id() {
        return name_id;
    }

    public void setName_id(Integer name_id) {
        this.name_id = name_id;
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
