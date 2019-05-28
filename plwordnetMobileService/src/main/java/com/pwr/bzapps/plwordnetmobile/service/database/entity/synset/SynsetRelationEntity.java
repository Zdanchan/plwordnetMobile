package com.pwr.bzapps.plwordnetmobile.service.database.entity.synset;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.relation.RelationTypeEntity;

import javax.persistence.*;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `child_synse_id` bigint(20) NOT NULL,
 *   `parent_synset_id` bigint(20) NOT NULL,
 *   `synset_relation_type_id` bigint(20) NOT NULL,
 * */
@Entity
@Table(name = "synset_relation")
public class SynsetRelationEntity {
    @Id
    private Long id;
    @Column(name = "child_synse_id")
    private Long childSynseId;
    @Column(name = "parent_synset_id")
    private Long parentSynsetId;
    @ManyToOne
    @JoinColumn(name = "synset_relation_type_id", referencedColumnName = "id")
    private RelationTypeEntity synsetRelationTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChildSynseId() {
        return childSynseId;
    }

    public void setChildSynseId(Long childSynseId) {
        this.childSynseId = childSynseId;
    }

    public Long getParentSynsetId() {
        return parentSynsetId;
    }

    public void setParentSynsetId(Long parentSynsetId) {
        this.parentSynsetId = parentSynsetId;
    }

    public RelationTypeEntity getSynsetRelationTypeId() {
        return synsetRelationTypeId;
    }

    public void setSynsetRelationTypeId(RelationTypeEntity synsetRelationTypeId) {
        this.synsetRelationTypeId = synsetRelationTypeId;
    }

    public String toString(){
        String string = "";
        string+="SyRE{";
        string+="id:" + id + ";";
        string+="childSynseId:" + childSynseId + ";";
        string+="parentSynsetId:" + parentSynsetId + ";";
        string+="synsetRelationTypeId:" + synsetRelationTypeId.toString();
        string+="}SyRE";

        return string;
    }
}
