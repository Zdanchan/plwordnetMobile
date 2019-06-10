package com.pwr.bzapps.plwordnetmobile.database.entity.synset;



import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset.SynsetExampleDAO;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.io.Serializable;
import java.util.List;

/**
 *   `synset_id` bigint(20) NOT NULL,
 *   `comment` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `definition` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `princeton_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL COMMENT 'External original Princeton Id',
 *   `owner_id` bigint(20) DEFAULT NULL COMMENT 'Synset owner',
 *   `error_comment` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `ili_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL COMMENT 'OMW id',
 * */
@Table(name = "synset_attributes", id = "synset_id")
public class SynsetAttributeEntity extends Model implements Entity, Serializable {
    @Column(name = "synset_id", unique = true)
    private Long synsetId;
    @Column(name = "comment")
    private String comment;
    @Column(name = "definition")
    private String definition;
    @Column(name = "princeton_id")
    private String princetonId;
    @Column(name = "owner_id")
    private Integer ownerId;
    @Column(name = "error_comment")
    private String errorComment;
    @Column(name = "ili_id")
    private String iliId;

    private List<SynsetExampleEntity> synsetExamples;

    public Long getSynsetAttributeId() {
        return synsetId;
    }

    public void setSynsetAttributeId(Long synsetId) {
        this.synsetId = synsetId;
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

    public List<SynsetExampleEntity> getSynsetExamples() {
        if(Settings.isOfflineMode() && synsetExamples ==null)
            synsetExamples = SynsetExampleDAO.findAllForSynsetAttribute(synsetId);
        return synsetExamples;
    }

    public void setSynsetExamples(List<SynsetExampleEntity> synsetExamples) {
        this.synsetExamples = synsetExamples;
    }

    @Override
    public String getEntityID() {
        return "SyA:" + getSynsetAttributeId();
    }
}
