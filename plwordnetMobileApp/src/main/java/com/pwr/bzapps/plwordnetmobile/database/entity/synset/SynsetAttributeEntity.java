package com.pwr.bzapps.plwordnetmobile.database.entity.synset;


import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;

import java.io.Serializable;
import java.util.Collection;

/**
 *   `synset_id` bigint(20) NOT NULL,
 *   `comment` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `definition` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `princeton_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL COMMENT 'External original Princeton Id',
 *   `owner_id` bigint(20) DEFAULT NULL COMMENT 'Synset owner',
 *   `error_comment` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `ili_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL COMMENT 'OMW id',
 * */
public class SynsetAttributeEntity implements Entity, Serializable {
    private Integer synset_id;
    private SynsetEntity synset;
    private String comment;
    private String definition;
    private String princeton_id;
    private Integer owner_id;
    private String error_comment;
    private String ili_id;

    private Collection<SynsetExampleEntity> synset_examples;

    public Integer getSynset_id() {
        return synset_id;
    }

    public void setSynset_id(Integer synset_id) {
        this.synset_id = synset_id;
    }

    public SynsetEntity getSynset() {
        return synset;
    }

    public void setSynset(SynsetEntity synset) {
        this.synset = synset;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = "null".equals(comment) ? null : comment;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = "null".equals(definition) ? null : definition;
    }

    public String getPrinceton_id() {
        return princeton_id;
    }

    public void setPrinceton_id(String princeton_id) {
        this.princeton_id = "null".equals(princeton_id) ? null : princeton_id;
    }

    public Integer getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
    }

    public String getError_comment() {
        return error_comment;
    }

    public void setError_comment(String error_comment) {
        this.error_comment = "null".equals(error_comment) ? null : error_comment;
    }

    public String getIli_id() {
        return ili_id;
    }

    public void setIli_id(String ili_id) {
        this.ili_id = "null".equals(ili_id) ? null : ili_id;
    }

    public Collection<SynsetExampleEntity> getSynset_examples() {
        return synset_examples;
    }

    public void setSynset_examples(Collection<SynsetExampleEntity> synset_examples) {
        this.synset_examples = synset_examples;
    }

    @Override
    public String getEntityID() {
        return "SyA:" + getSynset_id();
    }
}
