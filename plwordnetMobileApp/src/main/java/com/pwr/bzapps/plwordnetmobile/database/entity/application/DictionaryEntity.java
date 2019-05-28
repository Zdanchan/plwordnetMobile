package com.pwr.bzapps.plwordnetmobile.database.entity.application;


import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;

import java.io.Serializable;

/**
 *   `dtype` varchar(31) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `description_id` bigint(20) DEFAULT NULL COMMENT 'Dictionary description',
 *   `name_id` bigint(20) DEFAULT NULL COMMENT 'Dictionary name',
 *   `tag` varchar(20) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
 *   `value` bigint(20) DEFAULT NULL,
 * */
public class DictionaryEntity implements Entity, Serializable {
    private Integer id;
    private String dtype;
    private Integer descriptionId;
    private Integer nameId;
    private String tag;
    private Long value;

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = "null".equals(dtype) ? null : dtype;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(Integer descriptionId) {
        this.descriptionId = descriptionId;
    }

    public Integer getNameId() {
        return nameId;
    }

    public void setNameId(Integer nameId) {
        this.nameId = nameId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = "null".equals(tag) ? null : tag;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    @Override
    public String getEntityID() {
        return "Di:" +id;
    }
}
