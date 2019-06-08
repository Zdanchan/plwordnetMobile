package com.pwr.bzapps.plwordnetmobile.database.entity.sense;



import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense.SenseExampleDAO;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.io.Serializable;
import java.util.List;

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
@Table(name = "sense_attributes", id = "sense_id")
public class SenseAttributeEntity extends Model implements Entity, Serializable {
    @Column(name = "sense_id", unique = true)
    private Long senseId;
    @Column(name = "comment")
    private String comment;
    @Column(name = "definition")
    private String definition;
    @Column(name = "link")
    private String link;
    @Column(name = "register_id")
    private Integer registerId;
    @Column(name = "aspect_id")
    private Integer aspectId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "error_comment")
    private String errorComment;
    @Column(name = "proper_name")
    private boolean properName;

    private List<SenseExampleEntity> senseExamples;

    public Long getSenseAttributeId() {
        return senseId;
    }

    public void setSenseAttributeId(Long senseId) {
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

    public List<SenseExampleEntity> getSenseExamples() {
        if(Settings.isOfflineMode() && senseExamples==null)
            senseExamples = SenseExampleDAO.findAllForSenseAttribute(senseId);
        return senseExamples;
    }

    public void setSenseExamples(List<SenseExampleEntity> senseExamples) {
        this.senseExamples = senseExamples;
    }

    @Override
    public String getEntityID() {
        return "SeAE:"+ getSenseAttributeId();
    }
}
