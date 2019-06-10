package com.pwr.bzapps.plwordnetmobile.service.database.entity.sense;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.DomainEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.LexiconEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.grammar.EmotionalAnnotationEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.grammar.PartOfSpeechEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.grammar.WordEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.synset.SynsetEntity;
import org.springframework.lang.NonNull;

import javax.persistence.*;
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
@Entity
@Table(name = "sense")
public class SenseEntity implements Comparable<SenseEntity>{
    @Id
    private Long id;
    @Column(name = "synset_position")
    private Integer synsetPosition;
    @Column(name = "variant")
    private Integer variant;
    @ManyToOne
    @JoinColumn(name = "domain_id", referencedColumnName = "id")
    private DomainEntity domainId;
    @ManyToOne
    @JoinColumn(name = "lexicon_id", referencedColumnName = "id")
    private LexiconEntity lexiconId;
    @ManyToOne
    @JoinColumn(name = "part_of_speech_id", referencedColumnName = "id")
    private PartOfSpeechEntity partOfSpeechId;
    @ManyToOne
    @JoinColumn(name = "synset_id", referencedColumnName = "id")
    private SynsetEntity synsetId;
    @ManyToOne
    @JoinColumn(name = "word_id", referencedColumnName = "id")
    private WordEntity wordId;
    @Column(name = "status_id")
    private Integer statusId;

    @OneToMany(mappedBy = "senseId")
    private Collection<SenseAttributeEntity> senseAttributes;
    @OneToMany(mappedBy = "senseId")
    private Collection<EmotionalAnnotationEntity> emotionalAnnotations;

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

    public Collection<SenseAttributeEntity> getSenseAttributes() {
        return senseAttributes;
    }

    public void setSenseAttributes(Collection<SenseAttributeEntity> senseAttributes) {
        this.senseAttributes = senseAttributes;
    }

    public Collection<EmotionalAnnotationEntity> getEmotionalAnnotations() {
        return emotionalAnnotations;
    }

    public void setEmotionalAnnotations(Collection<EmotionalAnnotationEntity> emotionalAnnotations) {
        this.emotionalAnnotations = emotionalAnnotations;
    }

    public String toString(){
        String string = "";
        string+="SeE{";
        string+="id:" + id + ";";
        string+="synsetPosition:" + synsetPosition + ";";
        string+="variant:" + variant + ";";
        string+="domainId:" + domainId.toString() + ";";
        string+="lexiconId:" + lexiconId.toString() + ";";
        string+="partOfSpeechId:" + partOfSpeechId.toString() + ";";
        string+="synsetId:" + synsetId.toString() + ";";
        string+="wordId:" + wordId.toString() + ";";
        string+="statusId:" + statusId + ";";
        //string+="relation_child:" + relation_child.toString() + ";";
        //string+="relation_parent:" + relation_parent.toString()  + ";";
        string+="senseAttributes:" + senseAttributes.toString() ;
        string+="}SeE";

        return string;
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

