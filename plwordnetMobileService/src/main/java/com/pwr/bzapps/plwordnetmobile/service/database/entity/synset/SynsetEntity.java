package com.pwr.bzapps.plwordnetmobile.service.database.entity.synset;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.LexiconEntity;

import javax.persistence.*;
import java.util.Collection;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `split` int(11) DEFAULT NULL COMMENT 'Position of line splitting synset head',
 *   `lexicon_id` bigint(20) NOT NULL,
 *   `status_id` bigint(20) DEFAULT NULL,
 *   `abstract` tinyint(1) DEFAULT NULL COMMENT 'is synset abstract',
 * */
@Entity
@Table(name = "synset")
public class SynsetEntity {
    @Id
    private Long id;
    @Column(name = "split")
    private Integer split;
    @ManyToOne
    @JoinColumn(name = "lexicon_id", referencedColumnName = "id")
    private LexiconEntity lexiconId;
    @Column(name = "status_id")
    private Integer statusId;
    @Column(name = "abstract")
    private Short abstract_;

    @OneToMany(mappedBy = "childSynsetId")
    private Collection<SynsetRelationEntity> relationChild;
    @OneToMany(mappedBy = "parentSynsetId")
    private Collection<SynsetRelationEntity> relationParent;
    @OneToMany(mappedBy = "synsetId")
    private Collection<SynsetAttributeEntity> synsetAttributes;


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

    public Short getAbstract() {
        return abstract_;
    }

    public void setAbstract(Short abstract_) {
        this.abstract_ = abstract_;
    }

    public Collection<SynsetRelationEntity> getRelationChild() {
        return relationChild;
    }

    public void setRelationChild(Collection<SynsetRelationEntity> relationChild) {
        this.relationChild = relationChild;
    }

    public Collection<SynsetRelationEntity> getRelationParent() {
        return relationParent;
    }

    public void setRelationParent(Collection<SynsetRelationEntity> relationParent) {
        this.relationParent = relationParent;
    }

    public Collection<SynsetAttributeEntity> getSynsetAttributes() {
        return synsetAttributes;
    }

    public void setSynsetAttributes(Collection<SynsetAttributeEntity> synsetAttributes) {
        this.synsetAttributes = synsetAttributes;
    }

    public String toString(){
        String string = "";
        string+="SyE{";
        string+="id:" + id + ";";
        string+="split:" + split + ";";
        string+="lexiconId:" + lexiconId.toString() + ";";
        string+="statusId:" + statusId + ";";
        string+="abstract_:" + abstract_ + ";";
        string+="relationChild:" + relationChild.toString() + ";";
        string+="relationParent:" + relationParent.toString() + ";";
        string+="synsetAttributes:" + synsetAttributes.toString();
        string+="}SyE";

        return string;
    }
}
