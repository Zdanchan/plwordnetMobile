package com.pwr.bzapps.plwordnetmobile.database.entity.application;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;


import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `value` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `language` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
 * */
@android.arch.persistence.room.Entity(tableName = "application_localised_string")
public class ApplicationLocalisedStringEntity implements Entity, Serializable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "value")
    private String value;
    @ColumnInfo(name = "language")
    private String language;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
