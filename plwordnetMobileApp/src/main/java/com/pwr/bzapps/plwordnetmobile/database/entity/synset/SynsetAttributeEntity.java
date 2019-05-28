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
    private Integer synsetId;
    private SynsetEntity synset;
    private String comment;
    private String definition;
    private String princetonId;
    private Integer ownerId;
    private String errorComment;
    private String iliId;

    private Collection<SynsetExampleEntity> synset_examples;

    public Integer getSynsetId() {
        return synsetId;
    }

    public void setSynsetId(Integer synsetId) {
        this.synsetId = synsetId;
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

    public String getPrincetonId() {
        return princetonId;
    }

    public void setPrincetonId(String princetonId) {
        this.princetonId = "null".equals(princetonId) ? null : princetonId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getErrorComment() {
        return errorComment;
    }

    public void setErrorComment(String errorComment) {
        this.errorComment = "null".equals(errorComment) ? null : errorComment;
    }

    public String getIliId() {
        return iliId;
    }

    public void setIliId(String iliId) {
        this.iliId = "null".equals(iliId) ? null : iliId;
    }

    public Collection<SynsetExampleEntity> getSynset_examples() {
        return synset_examples;
    }

    public void setSynset_examples(Collection<SynsetExampleEntity> synset_examples) {
        this.synset_examples = synset_examples;
    }

    @Override
    public String getEntityID() {
        return "SyA:" + getSynsetId();
    }
}
