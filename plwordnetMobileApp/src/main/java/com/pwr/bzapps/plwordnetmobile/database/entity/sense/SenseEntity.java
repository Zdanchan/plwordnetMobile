package com.pwr.bzapps.plwordnetmobile.database.entity.sense;

import android.support.annotation.NonNull;

import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.DomainEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.LexiconEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.EmotionalAnnotationEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.PartOfSpeechEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.WordEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetEntity;
import com.pwr.bzapps.plwordnetmobile.utils.StringUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * `id` bigint(20) NOT NULL AUTO_INCREMENT,
 * `synset_position` int(11) DEFAULT NULL COMMENT 'Position order in synset',
 * `variant` int(11) NOT NULL DEFAULT '1' COMMENT 'Sense variant number',
 * `domain_id` bigint(20) NOT NULL COMMENT 'Domain Id',
 * `lexicon_id` bigint(20) NOT NULL COMMENT 'Lexicon Id',
 * `part_of_speech_id` bigint(20) NOT NULL COMMENT 'Part of speech Id',
 * `synset_id` bigint(20) DEFAULT NULL COMMENT 'Synset Id',
 * `word_id` bigint(20) NOT NULL,
 * `status_id` bigint(20) DEFAULT NULL,
 * */
public class SenseEntity implements Entity, Comparable<SenseEntity>, Serializable{
    private Integer id;
    private Integer synset_position;
    private Integer variant;
    private DomainEntity domain_id;
    private LexiconEntity lexicon_id;
    private PartOfSpeechEntity part_of_speech_id;
    private SynsetEntity synset_id;
    private WordEntity word_id;
    private Integer status_id;

    //private Collection<SenseRelationEntity> relation_child;
    //private Collection<SenseRelationEntity> relation_parent;
    private Collection<SenseAttributeEntity> sense_attributes;
    private Collection<EmotionalAnnotationEntity> emotional_annotations;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSynset_position() {
        return synset_position;
    }

    public void setSynset_position(Integer synset_position) {
        this.synset_position = synset_position;
    }

    public Integer getVariant() {
        return variant;
    }

    public void setVariant(Integer variant) {
        this.variant = variant;
    }

    public DomainEntity getDomain_id() {
        return domain_id;
    }

    public void setDomain_id(DomainEntity domain_id) {
        this.domain_id = domain_id;
    }

    public LexiconEntity getLexicon_id() {
        return lexicon_id;
    }

    public void setLexicon_id(LexiconEntity lexicon_id) {
        this.lexicon_id = lexicon_id;
    }

    public PartOfSpeechEntity getPart_of_speech_id() {
        return part_of_speech_id;
    }

    public void setPart_of_speech_id(PartOfSpeechEntity part_of_speech_id) {
        this.part_of_speech_id = part_of_speech_id;
    }

    public SynsetEntity getSynset_id() {
        return synset_id;
    }

    public void setSynset_id(SynsetEntity synset_id) {
        this.synset_id = synset_id;
    }

    public WordEntity getWord_id() {
        return word_id;
    }

    public void setWord_id(WordEntity word_id) {
        this.word_id = word_id;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    //public Collection<SenseRelationEntity> getRelation_child() {
    //    return relation_child;
    //}
//
    //public void setRelation_child(Collection<SenseRelationEntity> relation_child) {
    //    this.relation_child = relation_child;
    //}
//
    //public Collection<SenseRelationEntity> getRelation_parent() {
    //    return relation_parent;
    //}
//
    //public void setRelation_parent(Collection<SenseRelationEntity> relation_parent) {
    //    this.relation_parent = relation_parent;
    //}

    public Collection<SenseAttributeEntity> getSense_attributes() {
        return sense_attributes;
    }

    public void setSense_attributes(Collection<SenseAttributeEntity> sense_attributes) {
        this.sense_attributes = sense_attributes;
    }

    public Collection<EmotionalAnnotationEntity> getEmotional_annotations() {
        return emotional_annotations;
    }

    public void setEmotional_annotations(Collection<EmotionalAnnotationEntity> emotional_annotations) {
        this.emotional_annotations = emotional_annotations;
    }

    @Override
    public String getEntityID() {
        return "Se:" + getId();
    }

    @Override
    public int compareTo(@NonNull SenseEntity entity) {
        int comparison = 0;
        if(this.getWord_id().getWord().length()<entity.getWord_id().getWord().length()){
            comparison = -1;
        }
        else if(this.getWord_id().getWord().length()>entity.getWord_id().getWord().length()){
            comparison = 1;
        }
        else{
            comparison = 0;
        }
        if(comparison==0){
            comparison = this.getWord_id().getWord().toLowerCase().compareTo(entity.getWord_id().getWord().toLowerCase());
        }
        if(comparison==0){
            if(this.getVariant()<entity.getVariant()){
                comparison = -1;
            }
            else if(this.getVariant()>entity.getVariant()){
                comparison = 1;
            }
            else{
                comparison = 0;
            }
        }
        return comparison;
    }
}

