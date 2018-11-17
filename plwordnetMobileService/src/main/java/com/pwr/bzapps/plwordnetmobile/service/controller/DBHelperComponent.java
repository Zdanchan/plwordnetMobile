package com.pwr.bzapps.plwordnetmobile.service.controller;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.ApplicationLocalisedStringEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.DictionaryEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.DomainEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.LexiconEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.grammar.EmotionalAnnotationEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.grammar.PartOfSpeechEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.grammar.WordEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.relation.RelationTypeAllowedLexiconEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.relation.RelationTypeAllowedPartOfSpeechEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.relation.RelationTypeEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.sense.SenseAttributeEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.sense.SenseExampleEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.sense.SenseRelationEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.synset.SynsetAttributeEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.synset.SynsetEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.synset.SynsetExampleEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.synset.SynsetRelationEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.export.SQLExporter;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.application.ApplicationLocalisedStringRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.application.DictionaryRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.application.DomainRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.application.LexiconRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.grammar.EmotionalAnnotationRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.grammar.PartOfSpeechRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.grammar.WordRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.properties.TablesRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.relation.RelationTypeAllowedLexiconRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.relation.RelationTypeAllowedPartOfSpeechRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.relation.RelationTypeRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.sense.SenseAttributeRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.sense.SenseExampleRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.sense.SenseRelationRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.sense.SenseRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.synset.SynsetAttributeRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.synset.SynsetExampleRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.synset.SynsetRelationRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.synset.SynsetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBHelperComponent {

    @Autowired
    private ApplicationLocalisedStringRepository applicationLocalisedStringRepository;
    @Autowired
    private DictionaryRepository dictionaryRepository;
    @Autowired
    private DomainRepository domainRepository;
    @Autowired
    private LexiconRepository lexiconRepository;
    @Autowired
    private EmotionalAnnotationRepository emotionalAnnotationRepository;
    @Autowired
    private PartOfSpeechRepository partOfSpeechRepository;
    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private RelationTypeAllowedLexiconRepository relationTypeAllowedLexiconRepository;
    @Autowired
    private RelationTypeAllowedPartOfSpeechRepository relationTypeAllowedPartOfSpeechRepository;
    @Autowired
    private RelationTypeRepository relationTypeRepository;
    @Autowired
    private SenseAttributeRepository senseAttributeRepository;
    @Autowired
    private SenseRepository senseRepository;
    @Autowired
    private SenseExampleRepository senseExampleRepository;
    @Autowired
    private SenseRelationRepository senseRelationRepository;
    @Autowired
    private SynsetAttributeRepository synsetAttributeRepository;
    @Autowired
    private SynsetRepository synsetRepository;
    @Autowired
    private SynsetExampleRepository synsetExampleRepository;
    @Autowired
    private SynsetRelationRepository synsetRelationRepository;
    @Autowired
    private TablesRepository tablesRepository;

    public <T> List<T> findAllForEntity(Class<T> clazz){
        switch(clazz.getSimpleName()){
            case "ApplicationLocalisedStringEntity":
                return ((List<T>)applicationLocalisedStringRepository.findAll());
            case "DictionaryEntity":
                return ((List<T>)dictionaryRepository.findAll());
            case "DomainEntity":
                return ((List<T>)domainRepository.findAll());
            case "LexiconEntity":
                return ((List<T>)lexiconRepository.findAll());
            case "EmotionalAnnotationEntity":
                return ((List<T>)emotionalAnnotationRepository.findAll());
            case "PartOfSpeechEntity":
                return ((List<T>)partOfSpeechRepository.findAll());
            case "WordEntity":
                return ((List<T>)wordRepository.findAll());
            case "RelationTypeAllowedLexiconEntity":
                return ((List<T>)relationTypeAllowedLexiconRepository.findAll());
            case "RelationTypeAllowedPartOfSpeechEntity":
                return ((List<T>)relationTypeAllowedPartOfSpeechRepository.findAll());
            case "RelationTypeEntity":
                return ((List<T>)relationTypeRepository.findAll());
            case "SenseAttributeEntity":
                return ((List<T>)senseAttributeRepository.findAll());
            case "SenseEntity":
                return ((List<T>)senseRepository.findAll());
            case "SenseExampleEntity":
                return ((List<T>)senseExampleRepository.findAll());
            case "SenseRelationEntity":
                return ((List<T>)senseRelationRepository.findAll());
            case "SynsetAttributeEntity":
                return ((List<T>)synsetAttributeRepository.findAll());
            case "SynsetEntity":
                return ((List<T>)synsetRepository.findAll());
            case "SynsetExampleEntity":
                return ((List<T>)synsetExampleRepository.findAll());
            case "SynsetRelationEntity":
                return ((List<T>)synsetRelationRepository.findAll());
        }
        return null;
    }
    public <T> List<T> findSynsetAndSensesAllByRelatedIds(Class<T> clazz, Integer[] ids){
        switch(clazz.getSimpleName()){
            case "SenseAttributeEntity":
                return ((List<T>)senseAttributeRepository.findMultipleBySenseId(ids));
            case "SenseEntity":
                return ((List<T>)senseRepository.findMultipleByIds(ids));
            case "SenseExampleEntity":
                return ((List<T>)senseExampleRepository.findMultipleBySenseAttributeId(ids));
            case "SenseRelationEntity":
                return ((List<T>)senseRelationRepository.findMultipleBySenseId(ids));
            case "SynsetAttributeEntity":
                return ((List<T>)synsetAttributeRepository.findMultipleBySynsetId(ids));
            case "SynsetEntity":
                return ((List<T>)synsetRepository.findAllForLanguage(ids));
            case "SynsetExampleEntity":
                return ((List<T>)synsetExampleRepository.findMultipleBySynsetAttributeId(ids));
            case "SynsetRelationEntity":
                return ((List<T>)synsetRelationRepository.findMultipleBySynsetId(ids));
        }
        return null;
    }
    public <T> String generateSQLInsertForEntity(List<T> entities, Class<T> clazz){
        switch(clazz.getSimpleName()){
            case "ApplicationLocalisedStringEntity":
                return (SQLExporter.getInsertApplicationLocalisedQuery((List<ApplicationLocalisedStringEntity>)entities
                        ,SQLExporter.getTableNameForEntity(clazz)));
            case "DictionaryEntity":
                return (SQLExporter.getInsertDictionaryQuery((List<DictionaryEntity>)entities
                        ,SQLExporter.getTableNameForEntity(clazz)));
            case "DomainEntity":
                return (SQLExporter.getInsertDomainQuery((List<DomainEntity>)entities
                        ,SQLExporter.getTableNameForEntity(clazz)));
            case "LexiconEntity":
                return (SQLExporter.getInsertLexiconQuery((List<LexiconEntity>)entities
                        ,SQLExporter.getTableNameForEntity(clazz)));
            case "EmotionalAnnotationEntity":
                return (SQLExporter.getInsertEmotionalAnnotationQuery((List<EmotionalAnnotationEntity>)entities
                        ,SQLExporter.getTableNameForEntity(clazz)));
            case "PartOfSpeechEntity":
                return (SQLExporter.getInsertPartOfSpeechQuery((List<PartOfSpeechEntity>)entities
                        ,SQLExporter.getTableNameForEntity(clazz)));
            case "WordEntity":
                return (SQLExporter.getInsertWordQuery((List<WordEntity>)entities
                        ,SQLExporter.getTableNameForEntity(clazz)));
            case "RelationTypeAllowedLexiconEntity":
                return (SQLExporter.getInsertRelationTypeAllowedLexiconQuery((List<RelationTypeAllowedLexiconEntity>)entities
                        ,SQLExporter.getTableNameForEntity(clazz)));
            case "RelationTypeAllowedPartOfSpeechEntity":
                return (SQLExporter.getInsertRelationTypeAllowedPartOfSpeechQuery((List<RelationTypeAllowedPartOfSpeechEntity>)entities
                        ,SQLExporter.getTableNameForEntity(clazz)));
            case "RelationTypeEntity":
                return (SQLExporter.getInsertRelationTypeQuery((List<RelationTypeEntity>)entities
                        ,SQLExporter.getTableNameForEntity(clazz)));
            case "SenseAttributeEntity":
                return (SQLExporter.getInsertSenseAttributeQuery((List<SenseAttributeEntity>)entities
                        ,SQLExporter.getTableNameForEntity(clazz)));
            case "SenseEntity":
                return (SQLExporter.getInsertSenseQuery((List<SenseEntity>)entities
                        ,SQLExporter.getTableNameForEntity(clazz)));
            case "SenseExampleEntity":
                return (SQLExporter.getInsertSenseExampleQuery((List<SenseExampleEntity>)entities
                        ,SQLExporter.getTableNameForEntity(clazz)));
            case "SenseRelationEntity":
                return (SQLExporter.getInsertSenseRelationQuery((List<SenseRelationEntity>)entities
                        ,SQLExporter.getTableNameForEntity(clazz)));
            case "SynsetAttributeEntity":
                return (SQLExporter.getInsertSynsetAttributeQuery((List<SynsetAttributeEntity>)entities
                        ,SQLExporter.getTableNameForEntity(clazz)));
            case "SynsetEntity":
                return (SQLExporter.getInsertSynsetQuery((List<SynsetEntity>)entities
                        ,SQLExporter.getTableNameForEntity(clazz)));
            case "SynsetExampleEntity":
                return (SQLExporter.getInsertSynsetExampleQuery((List<SynsetExampleEntity>)entities
                        ,SQLExporter.getTableNameForEntity(clazz)));
            case "SynsetRelationEntity":
                return (SQLExporter.getInsertSynsetRelationQuery((List<SynsetRelationEntity>)entities
                        ,SQLExporter.getTableNameForEntity(clazz)));
        }
        return "";
    }

    public List<SynsetRelationEntity> getSynsetRelationsByRelationIds(Integer[] relation_ids){
        return synsetRelationRepository.findByRelationTypes(relation_ids);
    }

    public List<SenseRelationEntity> getSenseRelationsByRelationIds(Integer[] relation_ids){
        return senseRelationRepository.findByRelationTypes(relation_ids);
    }

    public List<SynsetRelationEntity> getSynsetRelationsExcludingRelationIds(Integer[] relation_ids){
        return synsetRelationRepository.findExcludingRelationTypes(relation_ids);
    }

    public List<SenseRelationEntity> getSenseRelationsExcludingRelationIds(Integer[] relation_ids){
        return senseRelationRepository.findExcludingRelationTypes(relation_ids);
    }

    public Integer[] getIdsOfSensesByLanguage(Integer[] lexicons){
        return senseRepository.findIdsForLanguage(lexicons).toArray(new Integer[0]);
    }

    public Integer[] getIdsOfSynsetsByLanguage(Integer[] lexicons){
        return synsetRepository.findIdsForLanguage(lexicons).toArray(new Integer[0]);
    }

    public Integer[] getIdsOfSenseAttributesByLanguage(Integer[] sense_ids){
        return senseAttributeRepository.findIdsMultipleBySenseId(sense_ids).toArray(new Integer[0]);
    }

    public Integer[] getIdsOfSynsetAttributesByLanguage(Integer[] synset_ids){
        return synsetAttributeRepository.findIdsMultipleBySynsetId(synset_ids).toArray(new Integer[0]);
    }
}
