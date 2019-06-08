package com.pwr.bzapps.plwordnetmobile.database.entity.application;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
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
@Table(name = "dictionaries", id = "id")
public class DictionaryEntity extends Model implements Entity, Serializable {
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "dtype")
    private String dtype;
    @Column(name = "description_id")
    private Integer descriptionId;
    @Column(name = "name_id")
    private Integer nameId;
    @Column(name = "tag")
    private String tag;
    @Column(name = "value")
    private Long value;

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = "null".equals(dtype) ? null : dtype;
    }

    public Long getDictionaryId() {
        return id;
    }

    public void setDictionaryId(Long id) {
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
