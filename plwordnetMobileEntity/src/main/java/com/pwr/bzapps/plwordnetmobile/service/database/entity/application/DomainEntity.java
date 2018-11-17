package com.pwr.bzapps.plwordnetmobile.service.database.entity.application;

import javax.persistence.*;
import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `description_id` bigint(20) DEFAULT NULL,
 *   `name_id` bigint(20) DEFAULT NULL,
 * */
@Entity
@Table(name = "domain")
public class DomainEntity implements com.pwr.bzapps.plwordnetmobile.service.database.entity.Entity, Serializable {
    @Id
    private Integer id;
    @Column(name = "description_id")
    private Integer description_id;
    @Column(name = "name_id")
    private Integer name_id;

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

    @Override
    public String getEntityID() {
        return "Do:"+id;
    }
}
