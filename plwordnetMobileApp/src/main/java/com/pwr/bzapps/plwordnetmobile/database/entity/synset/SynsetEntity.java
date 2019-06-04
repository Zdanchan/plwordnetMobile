package com.pwr.bzapps.plwordnetmobile.database.entity.synset;


import android.arch.persistence.room.*;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.LexiconEntity;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `split` int(11) DEFAULT NULL COMMENT 'Position of line splitting synset head',
 *   `lexicon_id` bigint(20) NOT NULL,
 *   `status_id` bigint(20) DEFAULT NULL,
 *   `abstract` tinyint(1) DEFAULT NULL COMMENT 'is synset abstract',
 * */
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
@android.arch.persistence.room.Entity(tableName = "synset")
public class SynsetEntity implements Entity, Serializable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "split")
    private Integer split;
    @Embedded(prefix = "lexicon_")
    private LexiconEntity lexiconId;
    @ColumnInfo(name = "status_id")
    private Integer statusId;
    @ColumnInfo(name = "abstract")
    private Short abstract_;

    @Ignore
    private List<SynsetRelationEntity> relationChild;
    @Ignore
    private List<SynsetRelationEntity> relationParent;
    @Ignore
    private List<SynsetAttributeEntity> synsetAttributes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSplit() {
        return split;
    }

    public void setSplit(Integer split) {
        this.split = split;
    }

    public LexiconEntity getLexiconId() {
        return lexiconId;
    }

    public void setLexiconId(LexiconEntity lexiconId) {
        this.lexiconId = lexiconId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Short getAbstract_() {
        return getAbstract();
    }

    public void setAbstract_(Short abstract_) {
        setAbstract(abstract_);
    }

    public Short getAbstract() {
        return abstract_;
    }

    public void setAbstract(Short abstract_) {
        this.abstract_ = abstract_;
    }

    public List<SynsetRelationEntity> getRelationChild() {
        if(Settings.isOfflineMode() && relationChild==null)
            relationChild = SQLiteConnector.getDatabaseInstance().synsetRelationDAO().findParentsByChildId(id);
        return relationChild;
    }

    public void setRelationChild(List<SynsetRelationEntity> relationChild) {
        this.relationChild = relationChild;
    }

    public List<SynsetRelationEntity> getRelationParent() {
        if(Settings.isOfflineMode() && relationParent==null)
            relationParent = SQLiteConnector.getDatabaseInstance().synsetRelationDAO().findParentsByChildId(id);
        return relationParent;
    }

    public void setRelationParent(List<SynsetRelationEntity> relationParent) {
        if(Settings.isOfflineMode() && synsetAttributes==null)
            synsetAttributes = SQLiteConnector.getDatabaseInstance().synsetAttributeDAO().findAllForSynsetId(id);
        this.relationParent = relationParent;
    }

    public List<SynsetAttributeEntity> getSynsetAttributes() {
        return synsetAttributes;
    }

    public void setSynsetAttributes(List<SynsetAttributeEntity> synsetAttributes) {
        this.synsetAttributes = synsetAttributes;
    }

    @Override
    public String getEntityID() {
        return "Sy:" + getId();
    }
}
