package com.pwr.bzapps.plwordnetmobile.database.entity.sense;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetExampleEntity;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.io.Serializable;
import java.util.Collection;
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
@android.arch.persistence.room.Entity(tableName = "sense_attributes")
public class SenseAttributeEntity implements Entity, Serializable {
    @PrimaryKey
    @ColumnInfo(name = "sense_id")
    private Long senseId;
    @ColumnInfo(name = "comment")
    private String comment;
    @ColumnInfo(name = "definition")
    private String definition;
    @ColumnInfo(name = "link")
    private String link;
    @ColumnInfo(name = "register_id")
    private Integer registerId;
    @ColumnInfo(name = "aspect_id")
    private Integer aspectId;
    @ColumnInfo(name = "user_id")
    private Integer userId;
    @ColumnInfo(name = "error_comment")
    private String errorComment;
    @ColumnInfo(name = "proper_name")
    private boolean properName;

    @Ignore
    private List<SenseExampleEntity> senseExamples;

    public Long getSenseId() {
        return senseId;
    }

    public void setSenseId(Long senseId) {
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
            senseExamples = SQLiteConnector.getDatabaseInstance().senseExampleDAO().findAllForSenseAttribute(senseId);
        return senseExamples;
    }

    public void setSenseExamples(List<SenseExampleEntity> senseExamples) {
        this.senseExamples = senseExamples;
    }

    @Override
    public String getEntityID() {
        return "SeAE:"+ getSenseId();
    }
}
