package com.pwr.bzapps.plwordnetmobile.database.entity.synset;


import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.utils.StringUtil;

import java.io.Serializable;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `child_synset_id` bigint(20) NOT NULL,
 *   `parent_synset_id` bigint(20) NOT NULL,
 *   `synset_relation_type_id` bigint(20) NOT NULL,
 * */
public class SynsetRelationEntity implements Entity, Serializable {
    private Integer id;
    private Integer child_synset_id;
    private Integer parent_synset_id;
    private RelationTypeEntity synset_relation_type_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChild_synset_id() {
        return child_synset_id;
    }

    public void setChild_synset_id(Integer child_synset_id) {
        this.child_synset_id = child_synset_id;
    }

    public Integer getParent_synset_id() {
        return parent_synset_id;
    }

    public void setParent_synset_id(Integer parent_synset_id) {
        this.parent_synset_id = parent_synset_id;
    }

    public RelationTypeEntity getSynset_relation_type_id() {
        return synset_relation_type_id;
    }

    public void setSynset_relation_type_id(RelationTypeEntity synset_relation_type_id) {
        this.synset_relation_type_id = synset_relation_type_id;
    }

    @Override
    public String getEntityID() {
        return "SyR:" + getId();
    }
}
