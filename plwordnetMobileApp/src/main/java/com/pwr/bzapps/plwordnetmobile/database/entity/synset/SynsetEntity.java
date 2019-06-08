package com.pwr.bzapps.plwordnetmobile.database.entity.synset;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset.SynsetAttributeDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset.SynsetRelationDAO;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.LexiconEntity;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.io.Serializable;
import java.util.List;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `split` int(11) DEFAULT NULL COMMENT 'Position of line splitting synset head',
 *   `lexicon_id` bigint(20) NOT NULL,
 *   `status_id` bigint(20) DEFAULT NULL,
 *   `abstract` tinyint(1) DEFAULT NULL COMMENT 'is synset abstract',
 * */
@Table(name = "synset", id = "id")
public class SynsetEntity extends Model implements Entity, Serializable {
    @Column(name = "id", unique = true)
    private Long id;
    @Column(name = "split")
    private Integer split;
    @Column(name = "lexicon_")
    private LexiconEntity lexiconId;
    @Column(name = "status_id")
    private Integer statusId;
    @Column(name = "abstract")
    private Short abstract_;


    private List<SynsetRelationEntity> relationChild;
    private List<SynsetRelationEntity> relationParent;
    private List<SynsetAttributeEntity> synsetAttributes;

    public Long getSynsetId() {
        return id;
    }

    public void setSynsetId(Long id) {
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
            relationChild = SynsetRelationDAO.findParentsByChildId(id);
        return relationChild;
    }

    public void setRelationChild(List<SynsetRelationEntity> relationChild) {
        this.relationChild = relationChild;
    }

    public List<SynsetRelationEntity> getRelationParent() {
        if(Settings.isOfflineMode() && relationParent==null)
            relationParent = SynsetRelationDAO.findChildrenByParentId(id);
        return relationParent;
    }

    public void setRelationParent(List<SynsetRelationEntity> relationParent) {
        this.relationParent = relationParent;
    }

    public List<SynsetAttributeEntity> getSynsetAttributes() {
        if(Settings.isOfflineMode() && synsetAttributes==null)
            synsetAttributes = SynsetAttributeDAO.findAllForSynsetId(id);
        return synsetAttributes;
    }

    public void setSynsetAttributes(List<SynsetAttributeEntity> synsetAttributes) {
        this.synsetAttributes = synsetAttributes;
    }

    @Override
    public String getEntityID() {
        return "Sy:" + getSynsetId();
    }
}
