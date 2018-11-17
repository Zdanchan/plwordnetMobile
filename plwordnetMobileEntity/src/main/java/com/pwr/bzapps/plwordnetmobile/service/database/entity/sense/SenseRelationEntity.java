package com.pwr.bzapps.plwordnetmobile.service.database.entity.sense;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.relation.RelationTypeEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `child_sense_id` bigint(20) NOT NULL,
 *   `parent_sense_id` bigint(20) NOT NULL,
 *   `relation_type_id` bigint(20) NOT NULL,
 * */
@Entity
@Table(name = "sense_relation")
public class SenseRelationEntity implements com.pwr.bzapps.plwordnetmobile.service.database.entity.Entity, Serializable {
    @Id
    private Integer id;
    @Column(name = "child_sense_id")
    private Integer child_sense_id;
    @Column(name = "parent_sense_id")
    private Integer parent_sense_id;
    @ManyToOne
    @JoinColumn(name = "relation_type_id", referencedColumnName = "id")
    private RelationTypeEntity relation_type_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChild_sense_id() {
        return child_sense_id;
    }

    public void setChild_sense_id(Integer child_sense_id) {
        this.child_sense_id = child_sense_id;
    }

    public Integer getParent_sense_id() {
        return parent_sense_id;
    }

    public void setParent_sense_id(Integer parent_sense_id) {
        this.parent_sense_id = parent_sense_id;
    }

    public RelationTypeEntity getRelation_type_id() {
        return relation_type_id;
    }

    public void setRelation_type_id(RelationTypeEntity relation_type_id) {
        this.relation_type_id = relation_type_id;
    }

    @Override
    public String getEntityID() {
        return "SeR:" + getId();
    }
}
