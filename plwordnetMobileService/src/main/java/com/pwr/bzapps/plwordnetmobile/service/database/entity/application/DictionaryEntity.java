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
    private Integer id;
    @Column(name = "dtype")
    private String dtype;
    @Column(name = "description_id")
    private Integer description_id;
    @Column(name = "name_id")
    private Integer name_id;
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
        string+="description_id:" + description_id.toString() + ";";
        string+="name_id:" + name_id.toString() + ";";
        string+="tag:" + tag + ";";
        string+="value:" + value;
        string+="}DiE";

        return string;
    }
}
