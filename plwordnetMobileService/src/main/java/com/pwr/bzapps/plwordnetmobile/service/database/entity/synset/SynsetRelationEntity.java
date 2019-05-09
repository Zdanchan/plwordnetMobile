package com.pwr.bzapps.plwordnetmobile.service.database.entity.synset;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.relation.RelationTypeEntity;

import javax.persistence.*;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `child_synset_id` bigint(20) NOT NULL,
 *   `parent_synset_id` bigint(20) NOT NULL,
 *   `synset_relation_type_id` bigint(20) NOT NULL,
 * */
@Entity
@Table(name = "synset_relation")
public class SynsetRelationEntity {
    @Id
    private Long id;
    @Column(name = "child_synset_id")
    private Long child_synset_id;
    @Column(name = "parent_synset_id")
    private Long parent_synset_id;
    @ManyToOne
    @JoinColumn(name = "synset_relation_type_id", referencedColumnName = "id")
    private RelationTypeEntity synset_relation_type_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChild_synset_id() {
        return child_synset_id;
    }

    public void setChild_synset_id(Long child_synset_id) {
        this.child_synset_id = child_synset_id;
    }

    public Long getParent_synset_id() {
        return parent_synset_id;
    }

    public void setParent_synset_id(Long parent_synset_id) {
        this.parent_synset_id = parent_synset_id;
    }

    public RelationTypeEntity getSynset_relation_type_id() {
        return synset_relation_type_id;
    }

    public void setSynset_relation_type_id(RelationTypeEntity synset_relation_type_id) {
        this.synset_relation_type_id = synset_relation_type_id;
    }

    public String toString(){
        String string = "";
        string+="SyRE{";
        string+="id:" + id + ";";
        string+="child_synset_id:" + child_synset_id + ";";
        string+="parent_synset_id:" + parent_synset_id + ";";
        string+="synset_relation_type_id:" + synset_relation_type_id.toString();
        string+="}SyRE";

        return string;
    }
}
