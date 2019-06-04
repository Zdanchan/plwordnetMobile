package com.pwr.bzapps.plwordnetmobile.database.entity.synset;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
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
@android.arch.persistence.room.Entity(tableName = "synset_attributes")
public class SynsetAttributeEntity implements Entity, Serializable {
    @PrimaryKey
    @ColumnInfo(name = "synset_id")
    private Long synsetId;
    @ColumnInfo(name = "comment")
    private String comment;
    @ColumnInfo(name = "definition")
    private String definition;
    @ColumnInfo(name = "princeton_id")
    private String princetonId;
    @ColumnInfo(name = "owner_id")
    private Integer ownerId;
    @ColumnInfo(name = "error_comment")
    private String errorComment;
    @ColumnInfo(name = "ili_id")
    private String iliId;

    @Ignore
    private List<SynsetExampleEntity> synsetExamples;

    public Long getSynsetId() {
        return synsetId;
    }

    public void setSynsetId(Long synsetId) {
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
            synsetExamples = SQLiteConnector.getDatabaseInstance().synsetExampleDAO().findAllForSynsetAttribute(synsetId);
        return synsetExamples;
    }

    public void setSynsetExamples(List<SynsetExampleEntity> synsetExamples) {
        this.synsetExamples = synsetExamples;
    }

    @Override
    public String getEntityID() {
        return "SyA:" + getSynsetId();
    }
}
