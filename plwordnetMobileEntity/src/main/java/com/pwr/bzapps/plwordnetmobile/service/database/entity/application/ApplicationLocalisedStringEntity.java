package com.pwr.bzapps.plwordnetmobile.service.database.entity.application;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.id.ApplicationLocalisedStringId;

import javax.persistence.*;
import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `value` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `language` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
 * */
@Entity
@Table(name = "application_localised_string")
@IdClass(ApplicationLocalisedStringId.class)
public class ApplicationLocalisedStringEntity implements com.pwr.bzapps.plwordnetmobile.service.database.entity.Entity, Serializable {
    private Integer id;
    @Column(name = "value")
    private String value;
    @Id
    @Column(name = "language")
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
