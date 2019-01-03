package com.pwr.bzapps.plwordnetmobile.database.access.sqlite;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application.DomainDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application.LexiconDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.grammar.EmotionalAnnotationDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.grammar.PartOfSpeechDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.grammar.WordDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.relation.RelationTypeDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense.SenseAttributeDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense.SenseExampleDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset.SynsetAttributeDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset.SynsetDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset.SynsetExampleDAO;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset.SynsetRelationDAO;
import com.pwr.bzapps.plwordnetmobile.database.entity.Entity;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.ApplicationLocalisedStringEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.DictionaryEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.DomainEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.LexiconEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.EmotionalAnnotationEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.PartOfSpeechEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.WordEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeAllowedLexiconEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeAllowedPartOfSpeechEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseAttributeEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseExampleEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseRelationEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetAttributeEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetExampleEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetRelationEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class SQLiteEntityWrapper {

    public static <T extends Entity> T wrapResultSetWithEntity(ResultSet resultSet, Class<T> clazz){
        try {
            switch (clazz.getSimpleName()) {
                case "ApplicationLocalisedStringEntity":
                    return ((T) wrapApplicationLocalisedStringEntity(
                            resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3)));
                case "DictionaryEntity":
                    return ((T) wrapDictionaryEntity(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getLong(4),
                            resultSet.getInt(5),
                            resultSet.getInt(6)));
                case "DomainEntity":
                    return ((T) wrapDomainEntity(resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getInt(3)));
                case "LexiconEntity":
                    return ((T) wrapLexiconEntity(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5)));
                case "EmotionalAnnotationEntity":
                    return ((T) wrapEmotionalAnnotationEntity(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getBoolean(3),
                            resultSet.getString(4),
                            resultSet.getBoolean(5),
                            resultSet.getString(6),
                            resultSet.getString(7),
                            resultSet.getString(8),
                            resultSet.getInt(9)));
                case "PartOfSpeechEntity":
                    return ((T) wrapPartOfSpeechEntity(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getInt(3)));
                case "WordEntity":
                    return ((T) wrapWordEntity(resultSet.getInt(1),
                            resultSet.getString(2)));
                case "RelationTypeAllowedLexiconEntity":
                    return ((T) wrapRelationTypeAllowedLexiconEntity(resultSet.getInt(1),
                            resultSet.getInt(2)));
                case "RelationTypeAllowedPartOfSpeechEntity":
                    return ((T) wrapRelationTypeAllowedPartOfSpeechEntity(resultSet.getInt(1),
                            resultSet.getInt(2)));
                case "RelationTypeEntity":
                    return ((T) wrapRelationTypeEntity(resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getBoolean(6),
                            resultSet.getBoolean(7),
                            resultSet.getInt(8),
                            resultSet.getInt(9),
                            resultSet.getInt(10),
                            resultSet.getInt(11),
                            resultSet.getInt(12),
                            resultSet.getInt(13)));
                case "SenseAttributeEntity":
                    return ((T) wrapSenseAttributeEntity(resultSet.getInt(1),
                            resultSet.getBoolean(2),
                            resultSet.getString(3),
                            resultSet.getInt(4),
                            resultSet.getInt(5),
                            resultSet.getInt(6),
                            resultSet.getString(7),
                            resultSet.getString(8),
                            resultSet.getString(9)));
                case "SenseEntity":
                    return ((T) wrapSenseEntity(resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4),
                            resultSet.getInt(5),
                            resultSet.getInt(6),
                            resultSet.getInt(7),
                            resultSet.getInt(8),
                            resultSet.getInt(9)));
                case "SenseExampleEntity":
                    return ((T) wrapSenseExampleEntity(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4)));
                case "SenseRelationEntity":
                    return ((T) wrapSenseRelationEntity(resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4)));
                case "SynsetAttributeEntity":
                    return ((T) wrapSynsetAttributeEntity(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5),
                            resultSet.getInt(6),
                            resultSet.getString(7)));
                case "SynsetEntity":
                    return ((T) wrapSynsetEntity(resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4),
                            resultSet.getInt(5)));
                case "SynsetExampleEntity":
                    return ((T) wrapSynsetExampleEntity(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4)));
                case "SynsetRelationEntity":
                    return ((T) wrapSynsetRelationEntity(resultSet.getInt(1),
                            resultSet.getInt(2),
                            resultSet.getInt(3),
                            resultSet.getInt(4)));
            }
        }catch (SQLException e){
            return null;
        }
        return null;
    }

    public static <T extends Entity> Collection<T> wrapResultSetCollectionWithEntity(ResultSet resultSet, Class<T> clazz, int resultsLimit){
        Collection<T> results = new LinkedList<T>();
        try {
            for(int i=0; i<resultsLimit && resultSet.next(); i++) {
                results.add(wrapResultSetWithEntity(resultSet, clazz));
            }
        }catch (SQLException e){
            return null;
        }
        return results;
    }

    public static <T extends Entity> Collection<T> wrapResultSetCollectionWithEntity(ResultSet resultSet, Class<T> clazz){
        return wrapResultSetCollectionWithEntity(resultSet, clazz, Integer.MAX_VALUE);
    }

    public static ApplicationLocalisedStringEntity wrapApplicationLocalisedStringEntity(Integer id, String language, String value){
        if(EntityManager.contains("ALS:"+id)){
            return (ApplicationLocalisedStringEntity)EntityManager.getEntity("ALS:"+id);
        }
        else{
            ApplicationLocalisedStringEntity applicationLocalisedStringEntity = new ApplicationLocalisedStringEntity();
            applicationLocalisedStringEntity.setId(id);
            applicationLocalisedStringEntity.setLanguage(language);
            applicationLocalisedStringEntity.setValue(value);

            EntityManager.putEntity(applicationLocalisedStringEntity);
            return applicationLocalisedStringEntity;
        }
    }

    public static DictionaryEntity wrapDictionaryEntity(Integer id, String dtype, String tag, Long value, Integer name_id, Integer description_id){
        if(EntityManager.contains("Di:"+id)){
            return (DictionaryEntity) EntityManager.getEntity("Di:"+id);
        }
        else{
            DictionaryEntity dictionaryEntity = new DictionaryEntity();
            dictionaryEntity.setId(id);
            dictionaryEntity.setDtype(dtype);
            dictionaryEntity.setTag(tag);
            dictionaryEntity.setValue(value);
            dictionaryEntity.setName_id(handleNullableValue(name_id));
            dictionaryEntity.setDescription_id(handleNullableValue(description_id));

            EntityManager.putEntity(dictionaryEntity);
            return dictionaryEntity;
        }
    }

    public static DomainEntity wrapDomainEntity(Integer id, Integer name_id, Integer description_id){
        if(EntityManager.contains("Do:"+id)){
            return (DomainEntity) EntityManager.getEntity("Do:"+id);
        }
        else{
            DomainEntity domainEntity = new DomainEntity();
            domainEntity.setId(id);
            domainEntity.setName_id(handleNullableValue(name_id));
            domainEntity.setDescription_id(handleNullableValue(description_id));

            EntityManager.putEntity(domainEntity);
            return domainEntity;
        }
    }

    public static LexiconEntity wrapLexiconEntity(Integer id, String name, String identifier, String language_name, String lexicon_version){
        if(EntityManager.contains("Le:"+id)){
            return (LexiconEntity) EntityManager.getEntity("Le:"+id);
        }
        else{
            LexiconEntity lexiconEntity = new LexiconEntity();
            lexiconEntity.setId(id);
            lexiconEntity.setName(name);
            lexiconEntity.setIdentifier(identifier);
            lexiconEntity.setLanguage_name(language_name);
            lexiconEntity.setLexicon_version(lexicon_version);

            EntityManager.putEntity(lexiconEntity);
            return lexiconEntity;
        }
    }

    public static EmotionalAnnotationEntity wrapEmotionalAnnotationEntity(Integer id, String emotions, boolean has_emotional_characteristic, String markedness,
                                                                          boolean super_anotation, String valuations, String example1,
                                                                          String example2, Integer sense_id){
        if(EntityManager.contains("EA:"+id)){
            return (EmotionalAnnotationEntity) EntityManager.getEntity("EA:"+id);
        }
        else{
            EmotionalAnnotationEntity emotionalAnnotationEntity = new EmotionalAnnotationEntity();
            emotionalAnnotationEntity.setId(id);
            emotionalAnnotationEntity.setEmotions(emotions);
            emotionalAnnotationEntity.setHas_emotional_characteristic(has_emotional_characteristic);
            emotionalAnnotationEntity.setMarkedness(markedness);
            emotionalAnnotationEntity.setSuper_anotation(super_anotation);
            emotionalAnnotationEntity.setValuations(valuations);
            emotionalAnnotationEntity.setExample1(example1);
            emotionalAnnotationEntity.setExample2(example2);
            emotionalAnnotationEntity.setSense_id(sense_id);

            EntityManager.putEntity(emotionalAnnotationEntity);
            return emotionalAnnotationEntity;
        }
    }

    public static PartOfSpeechEntity wrapPartOfSpeechEntity(Integer id, String color, Integer name_id){
        if(EntityManager.contains("POS:"+id)){
            return (PartOfSpeechEntity) EntityManager.getEntity("POS:"+id);
        }
        else{
            PartOfSpeechEntity partOfSpeechEntity = new PartOfSpeechEntity();
            partOfSpeechEntity.setId(id);
            partOfSpeechEntity.setColor(color);
            partOfSpeechEntity.setName_id(handleNullableValue(name_id));

            EntityManager.putEntity(partOfSpeechEntity);
            return partOfSpeechEntity;
        }
    }

    public static WordEntity wrapWordEntity(Integer id, String word){
        if(EntityManager.contains("Wo:"+id)){
            return (WordEntity) EntityManager.getEntity("Wo:"+id);
        }
        else{
            WordEntity wordEntity = new WordEntity();
            wordEntity.setId(id);
            wordEntity.setWord(word);

            EntityManager.putEntity(wordEntity);
            return wordEntity;
        }
    }

    public static RelationTypeAllowedLexiconEntity wrapRelationTypeAllowedLexiconEntity(Integer lexicon_id, Integer relation_type_id){
        RelationTypeAllowedLexiconEntity relationTypeAllowedLexiconEntity = new RelationTypeAllowedLexiconEntity();
        LexiconDAO lexiconDAO = new LexiconDAO();
        RelationTypeDAO relationTypeDAO = new RelationTypeDAO();

        relationTypeAllowedLexiconEntity.setLexicon_id(lexiconDAO.findById(lexicon_id));
        relationTypeAllowedLexiconEntity.setRelation_type_id(relationTypeDAO.findById(relation_type_id));

        if(EntityManager.contains(relationTypeAllowedLexiconEntity.getEntityID())){
            return (RelationTypeAllowedLexiconEntity) EntityManager.getEntity(relationTypeAllowedLexiconEntity.getEntityID());
        }
        else{
            EntityManager.putEntity(relationTypeAllowedLexiconEntity);
            return relationTypeAllowedLexiconEntity;
        }
    }

    public static RelationTypeAllowedPartOfSpeechEntity wrapRelationTypeAllowedPartOfSpeechEntity(Integer part_of_speech_id, Integer relation_type_id){
        RelationTypeAllowedPartOfSpeechEntity relationTypeAllowedLexiconEntity = new RelationTypeAllowedPartOfSpeechEntity();
        PartOfSpeechDAO partOfSpeechDAO = new PartOfSpeechDAO();
        RelationTypeDAO relationTypeDAO = new RelationTypeDAO();

        relationTypeAllowedLexiconEntity.setPart_of_speech_id(partOfSpeechDAO.findById(part_of_speech_id));
        relationTypeAllowedLexiconEntity.setRelation_type_id(relationTypeDAO.findById(relation_type_id));

        if(EntityManager.contains(relationTypeAllowedLexiconEntity.getEntityID())){
            return (RelationTypeAllowedPartOfSpeechEntity) EntityManager.getEntity(relationTypeAllowedLexiconEntity.getEntityID());
        }
        else{
            EntityManager.putEntity(relationTypeAllowedLexiconEntity);
            return relationTypeAllowedLexiconEntity;
        }
    }

    public static RelationTypeEntity wrapRelationTypeEntity(Integer id, Integer priority, String node_position, String color, String relation_argument,
                                                                   boolean multilingual, boolean auto_reverse, Integer reverse_relation_type_id, Integer name_id,
                                                                   Integer description_id, Integer display_text_id, Integer short_display_text_id,
                                                                   Integer parent_relation_type_id){
        if(EntityManager.contains("RT:"+id)){
            return (RelationTypeEntity) EntityManager.getEntity("RT:"+id);
        }
        else{
            RelationTypeEntity relationTypeEntity = new RelationTypeEntity();
            relationTypeEntity.setId(id);
            relationTypeEntity.setPriority(priority);
            relationTypeEntity.setNode_position(node_position);
            relationTypeEntity.setColor(color);
            relationTypeEntity.setRelation_argument(relation_argument);
            relationTypeEntity.setMultilingual(multilingual);
            relationTypeEntity.setAuto_reverse(auto_reverse);
            relationTypeEntity.setReverse_relation_type_id(reverse_relation_type_id);

            relationTypeEntity.setName_id(handleNullableValue(name_id));
            relationTypeEntity.setDescription_id(handleNullableValue(description_id));
            relationTypeEntity.setDisplay_text_id(handleNullableValue(display_text_id));
            relationTypeEntity.setShort_display_text_id(handleNullableValue(short_display_text_id));
            relationTypeEntity.setParent_relation_type_id(handleNullableValue(parent_relation_type_id));

            EntityManager.putEntity(relationTypeEntity);
            return relationTypeEntity;
        }
    }

    public static SenseAttributeEntity wrapSenseAttributeEntity(Integer sense_id, boolean proper_name, String error_comment,
                                                                       Integer user_id, Integer aspect_id, Integer register_id,
                                                                       String link, String definition, String comment){
        if(EntityManager.contains("SeA:"+sense_id)){
            return (SenseAttributeEntity) EntityManager.getEntity("SeA:"+sense_id);
        }
        else{
            SenseExampleDAO senseExampleDAO = new SenseExampleDAO();

            SenseAttributeEntity senseAttributeEntity = new SenseAttributeEntity();
            senseAttributeEntity.setSense_id(sense_id);
            senseAttributeEntity.setProper_name(proper_name);
            senseAttributeEntity.setError_comment(error_comment);
            senseAttributeEntity.setUser_id(user_id);
            senseAttributeEntity.setAspect_id(aspect_id);
            senseAttributeEntity.setRegister_id(register_id);
            senseAttributeEntity.setLink(link);
            senseAttributeEntity.setDefinition(definition);
            senseAttributeEntity.setComment(comment);

            senseAttributeEntity.setSense_examples(senseExampleDAO.findAllForSenseAttribute(sense_id));

            EntityManager.putEntity(senseAttributeEntity);
            return senseAttributeEntity;
        }
    }

    public static SenseEntity wrapSenseEntity(Integer id, Integer status_id, Integer synset_position,
                                                     Integer variant, Integer synset_id, Integer domain_id,
                                                     Integer lexicon_id, Integer word_id, Integer part_of_speech_id){
        if(EntityManager.contains("Se:"+id)){
            return (SenseEntity)EntityManager.getEntity("Se:"+id);
        }
        else {
            SynsetDAO synsetDAO = new SynsetDAO();
            DomainDAO domainDAO = new DomainDAO();
            LexiconDAO lexiconDAO = new LexiconDAO();
            WordDAO wordDAO = new WordDAO();
            PartOfSpeechDAO partOfSpeechDAO = new PartOfSpeechDAO();
            SenseAttributeDAO senseAttributeDAO = new SenseAttributeDAO();
            EmotionalAnnotationDAO emotionalAnnotationDAO = new EmotionalAnnotationDAO();

            SenseEntity senseEntity = new SenseEntity();
            senseEntity.setId(id);
            senseEntity.setStatus_id(status_id);
            senseEntity.setSynset_position(handleNullableValue(synset_position));
            senseEntity.setVariant(variant);
            senseEntity.setSynset_id(synsetDAO.findById(synset_id));
            senseEntity.setDomain_id(domainDAO.findById(domain_id));
            senseEntity.setLexicon_id(lexiconDAO.findById(lexicon_id));
            senseEntity.setWord_id(wordDAO.findById(word_id));
            senseEntity.setPart_of_speech_id(partOfSpeechDAO.findById(part_of_speech_id));
            senseEntity.setSense_attributes(senseAttributeDAO.findAllForSenseId(id));
            senseEntity.setEmotional_annotations(emotionalAnnotationDAO.findAllForSenseId(id));

            EntityManager.putEntity(senseEntity);
            return senseEntity;
        }
    }

    public static SenseExampleEntity wrapSenseExampleEntity(Integer id, String example, String type, Integer sense_attribute_id){
        if(EntityManager.contains("SeEx:"+id)){
            return (SenseExampleEntity) EntityManager.getEntity("SeEx:"+id);
        }
        else{
            SenseExampleEntity senseExampleEntity = new SenseExampleEntity();
            senseExampleEntity.setId(id);
            senseExampleEntity.setExample(example);
            senseExampleEntity.setType(type);
            senseExampleEntity.setSense_attribute_id(handleNullableValue(sense_attribute_id));

            EntityManager.putEntity(senseExampleEntity);
            return senseExampleEntity;
        }
    }

    public static SenseRelationEntity wrapSenseRelationEntity(Integer id, Integer parent_sense_id, Integer child_sense_id, Integer relation_type_id){
        if(EntityManager.contains("SeR:"+id)){
            return (SenseRelationEntity) EntityManager.getEntity("SeR:"+id);
        }
        else{
            RelationTypeDAO relationTypeDAO = new RelationTypeDAO();

            SenseRelationEntity senseRelationEntity = new SenseRelationEntity();
            senseRelationEntity.setId(id);
            senseRelationEntity.setParent_sense_id(handleNullableValue(parent_sense_id));
            senseRelationEntity.setChild_sense_id(handleNullableValue(child_sense_id));
            senseRelationEntity.setRelation_type_id(relationTypeDAO.findById(relation_type_id));

            EntityManager.putEntity(senseRelationEntity);
            return senseRelationEntity;
        }
    }

    public static SynsetAttributeEntity wrapSynsetAttributeEntity(Integer synset_id, String definition, String comment, String error_comment,
                                                                         String ili_id, Integer owner_id, String princeton_id){
        if(EntityManager.contains("SyA:"+synset_id)){
            return (SynsetAttributeEntity) EntityManager.getEntity("SyA:"+synset_id);
        }
        else{
            SynsetExampleDAO synsetExampleDAO = new SynsetExampleDAO();

            SynsetAttributeEntity synsetAttributeEntity = new SynsetAttributeEntity();
            synsetAttributeEntity.setSynset_id(synset_id);
            synsetAttributeEntity.setDefinition(definition);
            synsetAttributeEntity.setComment(comment);
            synsetAttributeEntity.setError_comment(error_comment);
            synsetAttributeEntity.setIli_id(ili_id);
            synsetAttributeEntity.setOwner_id(owner_id);
            synsetAttributeEntity.setPrinceton_id(princeton_id);
            synsetAttributeEntity.setSynset_examples(synsetExampleDAO.findAllForSynsetAttribute(synset_id));

            EntityManager.putEntity(synsetAttributeEntity);
            return synsetAttributeEntity;
        }
    }

    public static SynsetEntity wrapSynsetEntity(Integer id, Integer split, Integer status_id, Integer abstract_, Integer lexicon_id){
        if(EntityManager.contains("Sy:"+id)){
            return (SynsetEntity) EntityManager.getEntity("Sy:"+id);
        }
        else{
            LexiconDAO lexiconDAO = new LexiconDAO();
            SynsetRelationDAO synsetRelationDAO = new SynsetRelationDAO();
            SynsetAttributeDAO synsetAttributeDAO = new SynsetAttributeDAO();

            SynsetEntity synsetEntity = new SynsetEntity();
            synsetEntity.setId(id);
            synsetEntity.setSplit(handleNullableValue(split));
            synsetEntity.setStatus_id(handleNullableValue(status_id));
            synsetEntity.setAbstract(handleNullableValue(abstract_).shortValue());
            synsetEntity.setLexicon_id(lexiconDAO.findById(lexicon_id));
            synsetEntity.setRelation_parent(synsetRelationDAO.findChildrenByParentId(id));
            synsetEntity.setRelation_child(synsetRelationDAO.findParentsByChildId(id));
            synsetEntity.setSynset_attributes(synsetAttributeDAO.findAllForSynsetId(id));

            EntityManager.putEntity(synsetEntity);
            return synsetEntity;
        }
    }

    public static SynsetExampleEntity wrapSynsetExampleEntity(Integer id, String example, String type, Integer synset_attributes_id){
        if(EntityManager.contains("SynEx:"+id)){
            return (SynsetExampleEntity) EntityManager.getEntity("SynEx:"+id);
        }
        else{
            SynsetExampleEntity synsetExampleEntity = new SynsetExampleEntity();
            synsetExampleEntity.setId(id);
            synsetExampleEntity.setExample(example);
            synsetExampleEntity.setType(type);
            synsetExampleEntity.setSynset_attributes_id(handleNullableValue(synset_attributes_id));

            EntityManager.putEntity(synsetExampleEntity);
            return synsetExampleEntity;
        }
    }

    public static SynsetRelationEntity wrapSynsetRelationEntity(Integer id, Integer parent_synset_id,
                                                                       Integer child_synset_id, Integer synset_attributes_id){
        if(EntityManager.contains("SyR:"+id)){
            return (SynsetRelationEntity) EntityManager.getEntity("SyR:"+id);
        }
        else{
            RelationTypeDAO relationTypeDAO = new RelationTypeDAO();

            SynsetRelationEntity synsetRelationEntity = new SynsetRelationEntity();
            synsetRelationEntity.setId(id);
            synsetRelationEntity.setParent_synset_id(handleNullableValue(parent_synset_id));
            synsetRelationEntity.setChild_synset_id(handleNullableValue(child_synset_id));
            synsetRelationEntity.setSynset_relation_type_id(relationTypeDAO.findById(synset_attributes_id));

            EntityManager.putEntity(synsetRelationEntity);
            return synsetRelationEntity;
        }
    }

    private static Integer handleNullableValue(Integer value){
        return value.intValue()!=0 ? null : value.intValue();
    }

}
