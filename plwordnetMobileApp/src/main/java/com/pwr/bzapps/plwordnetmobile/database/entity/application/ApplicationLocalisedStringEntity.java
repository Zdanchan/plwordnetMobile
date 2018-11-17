package com.pwr.bzapps.plwordnetmobile.database.entity.application;

import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.utils.StringUtil;

import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `value` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `language` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
 * */
public class ApplicationLocalisedStringEntity implements Entity, Serializable {
    private Integer id;
    private String value;
    private String language;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
