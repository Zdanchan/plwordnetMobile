package com.pwr.bzapps.plwordnetmobile.service.database.entity.sense;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.relation.RelationTypeEntity;

import javax.persistence.*;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `child_sense_id` bigint(20) NOT NULL,
 *   `parent_sense_id` bigint(20) NOT NULL,
 *   `relation_type_id` bigint(20) NOT NULL,
 * */
@Entity
@Table(name = "sense_relation")
public class SenseRelationEntity {
    @Id
    private Long id;
    @Column(name = "child_sense_id")
    private Long childSenseId;
    @Column(name = "parent_sense_id")
    private Long parentSenseId;
    @ManyToOne
    @JoinColumn(name = "relation_type_id", referencedColumnName = "id")
    private RelationTypeEntity relationTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChildSenseId() {
        return childSenseId;
    }

    public void setChildSenseId(Long childSenseId) {
        this.childSenseId = childSenseId;
    }

    public Long getParentSenseId() {
        return parentSenseId;
    }

    public void setParentSenseId(Long parentSenseId) {
        this.parentSenseId = parentSenseId;
    }

    public RelationTypeEntity getRelationTypeId() {
        return relationTypeId;
    }

    public void setRelationTypeId(RelationTypeEntity relationTypeId) {
        this.relationTypeId = relationTypeId;
    }

    public String toString(){
        String string = "";
        string+="SeRE{";
        string+="id:" + id + ";";
        string+="childSenseId:" + childSenseId + ";";
        string+="parentSenseId:" + parentSenseId + ";";
        string+="relationTypeId:" + relationTypeId.toString();
        string+="}SeRE";

        return string;
    }
}
