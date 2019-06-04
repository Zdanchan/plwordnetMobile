package com.pwr.bzapps.plwordnetmobile.database.entity.sense;

import android.arch.persistence.room.*;
import android.support.annotation.NonNull;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.DomainEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.LexiconEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.EmotionalAnnotationEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.PartOfSpeechEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.WordEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetEntity;
import com.pwr.bzapps.plwordnetmobile.settings.Settings;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

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
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
@android.arch.persistence.room.Entity(tableName = "sense")
public class SenseEntity implements Entity, Comparable<SenseEntity>, Serializable{
    @PrimaryKey
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "synset_position")
    private Integer synsetPosition;
    @ColumnInfo(name = "variant")
    private Integer variant;
    @Embedded(prefix = "domain_")
    private DomainEntity domainId;
    @Embedded(prefix = "lexicon_")
    private LexiconEntity lexiconId;
    @Embedded(prefix = "part_of_speech_")
    private PartOfSpeechEntity partOfSpeechId;
    @Embedded(prefix = "synset_")
    private SynsetEntity synsetId;
    @Embedded(prefix = "word_")
    private WordEntity wordId;
    @ColumnInfo(name = "status_id")
    private Integer statusId;

    //private Collection<SenseRelationEntity> relationChild;
    //private Collection<SenseRelationEntity> relationParent;
    @Ignore
    private List<SenseAttributeEntity> senseAttributes;
    @Ignore
    private List<EmotionalAnnotationEntity> emotionalAnnotations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSynsetPosition() {
        return synsetPosition;
    }

    public void setSynsetPosition(Integer synsetPosition) {
        this.synsetPosition = synsetPosition;
    }

    public Integer getVariant() {
        return variant;
    }

    public void setVariant(Integer variant) {
        this.variant = variant;
    }

    public DomainEntity getDomainId() {
        return domainId;
    }

    public void setDomainId(DomainEntity domainId) {
        this.domainId = domainId;
    }

    public LexiconEntity getLexiconId() {
        return lexiconId;
    }

    public void setLexiconId(LexiconEntity lexiconId) {
        this.lexiconId = lexiconId;
    }

    public PartOfSpeechEntity getPartOfSpeechId() {
        return partOfSpeechId;
    }

    public void setPartOfSpeechId(PartOfSpeechEntity partOfSpeechId) {
        this.partOfSpeechId = partOfSpeechId;
    }

    public SynsetEntity getSynsetId() {
        return synsetId;
    }

    public void setSynsetId(SynsetEntity synsetId) {
        this.synsetId = synsetId;
    }

    public WordEntity getWordId() {
        return wordId;
    }

    public void setWordId(WordEntity wordId) {
        this.wordId = wordId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    //public Collection<SenseRelationEntity> getRelationChild() {
    //    return relationChild;
    //}
//
    //public void setRelationChild(Collection<SenseRelationEntity> relationChild) {
    //    this.relationChild = relationChild;
    //}
//
    //public Collection<SenseRelationEntity> getRelationParent() {
    //    return relationParent;
    //}
//
    //public void setRelationParent(Collection<SenseRelationEntity> relationParent) {
    //    this.relationParent = relationParent;
    //}

    public List<SenseAttributeEntity> getSenseAttributes() {
        if(Settings.isOfflineMode() && senseAttributes==null)
            senseAttributes = SQLiteConnector.getDatabaseInstance().senseAttributeDAO().findAllForSenseId(id);
        return senseAttributes;
    }

    public void setSenseAttributes(List<SenseAttributeEntity> senseAttributes) {
        this.senseAttributes = senseAttributes;
    }

    public List<EmotionalAnnotationEntity> getEmotionalAnnotations() {
        if(Settings.isOfflineMode() && emotionalAnnotations==null)
            emotionalAnnotations = SQLiteConnector.getDatabaseInstance().emotionalAnnotationDAO().findAllForSenseId(id);
        return emotionalAnnotations;
    }

    public void setEmotionalAnnotations(List<EmotionalAnnotationEntity> emotionalAnnotations) {
        this.emotionalAnnotations = emotionalAnnotations;
    }

    @Override
    public String getEntityID() {
        return "Se:" + getId();
    }

    @Override
    public int compareTo(@NonNull SenseEntity entity) {
        int comparison = 0;
        if(this.getWordId().getWord().length()<entity.getWordId().getWord().length()){
            comparison = -1;
        }
        else if(this.getWordId().getWord().length()>entity.getWordId().getWord().length()){
            comparison = 1;
        }
        else{
            comparison = 0;
        }
        if(comparison==0){
            comparison = this.getWordId().getWord().toLowerCase().compareTo(entity.getWordId().getWord().toLowerCase());
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
        if(comparison==0){
            if(this.getLexiconId().getId()<entity.getLexiconId().getId()){
                comparison = -1;
            }
            else if(this.getLexiconId().getId()>entity.getLexiconId().getId()){
                comparison = 1;
            }
            else{
                comparison = 0;
            }
        }
        if(comparison==0){
            if(this.getPartOfSpeechId().getId()<entity.getPartOfSpeechId().getId()){
                comparison = -1;
            }
            else if(this.getPartOfSpeechId().getId()>entity.getPartOfSpeechId().getId()){
                comparison = 1;
            }
            else{
                comparison = 0;
            }
        }
        return comparison;
    }
}

