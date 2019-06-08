package com.pwr.bzapps.plwordnetmobile.database.entity.application;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;


import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `value` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `language` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
 * */
@Table(name = "application_localised_string", id = "id")
public class ApplicationLocalisedStringEntity extends Model implements Entity, Serializable {
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "value")
    private String value;
    @Column(name = "language")
    private String language;

    public Long getApplicationLocalisedStringId() {
        return id;
    }

    public void setApplicationLocalisedStringId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = "null".equals(value) ? null : value;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = "null".equals(language) ? null : language;
    }

    @Override
    public String getEntityID() {
        return "ALS:"+ id + language;
    }
}
