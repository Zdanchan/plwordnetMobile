package com.pwr.bzapps.plwordnetmobile.service.database.entity.application;

import javax.persistence.*;

/**
 *   `dtype` varchar(31) CHARACTER SET utf8 COLLATE utf8_polish_ci NOT NULL,
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `description_id` bigint(20) DEFAULT NULL COMMENT 'Dictionary description',
 *   `name_id` bigint(20) DEFAULT NULL COMMENT 'Dictionary name',
 *   `tag` varchar(20) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
 *   `value` bigint(20) DEFAULT NULL,
 * */
@Entity
@Table(name = "dictionaries")
public class DictionaryEntity {
    @Id
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
        this.dtype = dtype;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        this.tag = tag;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String toString(){
        String string = "";
        string+="DiE{";
        string+="id:" + id + ";";
        string+="dtype:" + dtype + ";";
        string+="descriptionId:" + descriptionId.toString() + ";";
        string+="nameId:" + nameId.toString() + ";";
        string+="tag:" + tag + ";";
        string+="value:" + value;
        string+="}DiE";

        return string;
    }
}
