package com.pwr.bzapps.plwordnetmobile.service.database.entity.application;

import javax.persistence.*;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `description_id` bigint(20) DEFAULT NULL,
 *   `name_id` bigint(20) DEFAULT NULL,
 * */
@Entity
@Table(name = "domain")
public class DomainEntity {
    @Id
    private Long id;
    @Column(name = "description_id")
    private Integer descriptionId;
    @Column(name = "name_id")
    private Integer nameId;

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

    public String toString(){
        String string = "";
        string+="DoE{";
        string+="id:" + id + ";";
        string+="descriptionId:" + descriptionId.toString() + ";";
        string+="variant:" + nameId.toString();
        string+="}DoE";

        return string;
    }
}
