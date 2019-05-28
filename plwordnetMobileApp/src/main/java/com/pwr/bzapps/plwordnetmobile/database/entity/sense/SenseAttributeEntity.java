package com.pwr.bzapps.plwordnetmobile.database.entity.sense;


import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;

import java.io.Serializable;
import java.util.Collection;

/**
 *   `sense_id` bigint(20) NOT NULL,
 *   `comment` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `definition` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `link` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL,
 *   `register_id` bigint(20) DEFAULT NULL,
 *   `aspect_id` bigint(20) DEFAULT NULL,
 *   `user_id` bigint(20) DEFAULT NULL,
 *   `error_comment` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `proper_name` bit(1) NOT NULL DEFAULT b'0',
 *
 * */
public class SenseAttributeEntity implements Entity, Serializable {
    private Integer senseId;
    private String comment;
    private String definition;
    private String link;
    private Integer registerId;
    private Integer aspectId;
    private Integer userId;
    private String errorComment;
    private boolean properName;

    private Collection<SenseExampleEntity> senseExamples;

    public Integer getSenseId() {
        return senseId;
    }

    public void setSenseId(Integer senseId) {
        this.senseId = senseId;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = "null".equals(link) ? null : link;
    }

    public Integer getRegisterId() {
        return registerId;
    }

    public void setRegisterId(Integer registerId) {
        this.registerId = registerId;
    }

    public Integer getAspectId() {
        return aspectId;
    }

    public void setAspectId(Integer aspectId) {
        this.aspectId = aspectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getErrorComment() {
        return errorComment;
    }

    public void setErrorComment(String errorComment) {
        this.errorComment = "null".equals(errorComment) ? null : errorComment;
    }

    public boolean isProperName() {
        return properName;
    }

    public void setProperName(boolean properName) {
        this.properName = properName;
    }

    public Collection<SenseExampleEntity> getSenseExamples() {
        return senseExamples;
    }

    public void setSenseExamples(Collection<SenseExampleEntity> senseExamples) {
        this.senseExamples = senseExamples;
    }

    @Override
    public String getEntityID() {
        return "SeAE:"+ getSenseId();
    }
}
