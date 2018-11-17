package com.pwr.bzapps.plwordnetmobile.service.database.entity.synset;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.LexiconEntity;

import javax.persistence.*;
import java.io.Serializable;
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
public class SynsetEntity implements com.pwr.bzapps.plwordnetmobile.service.database.entity.Entity, Serializable {
    @Id
    private Integer id;
    @Column(name = "split")
    private Integer split;
    @ManyToOne
    @JoinColumn(name = "lexicon_id", referencedColumnName = "id")
    private LexiconEntity lexicon_id;
    @Column(name = "status_id")
    private Integer status_id;
    @Column(name = "abstract")
    private Short abstract_;

    @OneToMany(mappedBy = "child_synset_id")
    private Collection<SynsetRelationEntity> relation_child;
    @OneToMany(mappedBy = "parent_synset_id")
    private Collection<SynsetRelationEntity> relation_parent;
    @OneToMany(mappedBy = "synset_id")
    private Collection<SynsetAttributeEntity> synset_attributes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSplit() {
        return split;
    }

    public void setSplit(Integer split) {
        this.split = split;
    }

    public LexiconEntity getLexicon_id() {
        return lexicon_id;
    }

    public void setLexicon_id(LexiconEntity lexicon_id) {
        this.lexicon_id = lexicon_id;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public Short getAbstract() {
        return abstract_;
    }

    public void setAbstract(Short abstract_) {
        this.abstract_ = abstract_;
    }

    public Collection<SynsetRelationEntity> getRelation_child() {
        return relation_child;
    }

    public void setRelation_child(Collection<SynsetRelationEntity> relation_child) {
        this.relation_child = relation_child;
    }

    public Collection<SynsetRelationEntity> getRelation_parent() {
        return relation_parent;
    }

    public void setRelation_parent(Collection<SynsetRelationEntity> relation_parent) {
        this.relation_parent = relation_parent;
    }

    public Collection<SynsetAttributeEntity> getSynset_attributes() {
        return synset_attributes;
    }

    public void setSynset_attributes(Collection<SynsetAttributeEntity> synset_attributes) {
        this.synset_attributes = synset_attributes;
    }

    @Override
    public String getEntityID() {
        return "Sy:" + getId();
    }
}
