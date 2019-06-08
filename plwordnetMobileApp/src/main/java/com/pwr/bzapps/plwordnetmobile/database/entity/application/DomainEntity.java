package com.pwr.bzapps.plwordnetmobile.database.entity.application;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;

import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `description_id` bigint(20) DEFAULT NULL,
 *   `name_id` bigint(20) DEFAULT NULL,
 * */
@Table(name = "domain", id = "id")
public class DomainEntity extends Model implements Entity, Serializable {
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "description_id")
    private Integer descriptionId;
    @Column(name = "name_id")
    private Integer nameId;

    public Long getDomainId() {
        return id;
    }

    public void setDomainId(Long id) {
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

    @Override
    public String getEntityID() {
        return "Do:"+id;
    }
}
