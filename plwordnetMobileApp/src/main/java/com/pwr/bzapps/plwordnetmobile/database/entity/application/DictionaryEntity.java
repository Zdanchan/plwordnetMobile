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
    private Integer description_id;
    private Integer name_id;
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

    public Integer getDescription_id() {
        return description_id;
    }

    public void setDescription_id(Integer description_id) {
        this.description_id = description_id;
    }

    public Integer getName_id() {
        return name_id;
    }

    public void setName_id(Integer name_id) {
        this.name_id = name_id;
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
