package com.pwr.bzapps.plwordnetmobile.service.database.export;

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

import java.util.ArrayList;
import java.util.List;

public class SQLExporter {

    public static final String CREATE_PATTERN = "CREATE TABLE IF NOT EXISTS %s (%s) %s;\n";
    public static final String INSERT_PATTERN = "INSERT INTO %s (%s) VALUES %s;\n";

    public static final String DB_NAME = "plwordnet";
    public static final String APPLICATION_LOCALISED_STRING_NAME =  "[application_localised_string]";
    public static final String DICTIONARY_NAME =  "[dictionaries]";
    public static final String DOMAIN_NAME =  "[domain]";
    public static final String LEXICON_NAME =  "[lexicon]";
    public static final String EMOTIONAL_ANNOTATION_NAME =  "[emotional_annotations]";
    public static final String PART_OF_SPEECH_NAME =  "[part_of_speech]";
    public static final String WORD_NAME =  "[word]";
    public static final String RELATION_TYPE_ALLOWED_LEXICON_NAME =  "[relation_type_allowed_lexicons]";
    public static final String RELATION_TYPE_ALLOWED_PART_OF_SPEECH_NAME =  "[relation_type_allowed_parts_of_speech]";
    public static final String RELATION_TYPE_NAME =  "[relation_type]";
    public static final String SENSE_ATTRIBUTE_NAME =  "[sense_attributes]";
    public static final String SENSE_NAME =  "[sense]";
    public static final String SENSE_EXAMPLE_NAME =  "[sense_examples]";
    public static final String SENSE_RELATION_NAME =  "[sense_relation]";
    public static final String SYNSET_ATTRIBUTE_NAME =  "[synset_attributes]";
    public static final String SYNSET_NAME =  "[synset]";
    public static final String SYNSET_EXAMPLE_NAME =  "[synset_examples]";
    public static final String SYNSET_RELATION_NAME =  "[synset_relation]";

    public static String getCreateApplicationLocalisedQuery(){
        String query = String.format(CREATE_PATTERN,APPLICATION_LOCALISED_STRING_NAME,
                "[id] bigint(20) NOT NULL ,\n" +
                "  [value] text,\n" +
                "  [language] varchar(255)NOT NULL,\n" +
                "  PRIMARY KEY ([id],[language])",
                ""
        );
        return query;
    }

    public static <T> String createInsertQueryWithStrings(List<String> parsedElements, Class<T> clazz){
        String insert = "";
        for(int i = 0; i < parsedElements.size(); i++){
            insert += "(" + parsedElements.get(i) + ")";
            if(i<parsedElements.size()-1)
                insert+=",";
        }
        parsedElements.clear();
        switch(clazz.getSimpleName()){
            case "ApplicationLocalisedStringEntity":
                return  String.format(INSERT_PATTERN,APPLICATION_LOCALISED_STRING_NAME,
                        "[id],[value],[language]",insert);
            case "DictionaryEntity":
                return String.format(INSERT_PATTERN,DICTIONARY_NAME,
                        "[dtype],[id],[description_id],[name_id],[tag],[value]",insert);
            case "DomainEntity":
                return String.format(INSERT_PATTERN,DOMAIN_NAME,
                        "[id],[description_id],[name_id]",insert);
            case "LexiconEntity":
                return String.format(INSERT_PATTERN,LEXICON_NAME,
                        "[id],[identifier],[language_name],[name],[lexicon_version]",insert);
            case "EmotionalAnnotationEntity":
                return String.format(INSERT_PATTERN,EMOTIONAL_ANNOTATION_NAME,
                        "[id],[sense_id],[has_emotional_characteristic],[super_anotation],[emotions],[valuations],[markedness],[example1],[example2]",insert);
            case "PartOfSpeechEntity":
                return String.format(INSERT_PATTERN,PART_OF_SPEECH_NAME,
                        "[id],[name_id],[color]",insert);
            case "WordEntity":
                return String.format(INSERT_PATTERN,WORD_NAME,
                        "[id],[word]",insert);
            case "RelationTypeAllowedLexiconEntity":
                return String.format(INSERT_PATTERN,RELATION_TYPE_ALLOWED_LEXICON_NAME,
                        "[relation_type_id],[lexicon_id]",insert);
            case "RelationTypeAllowedPartOfSpeechEntity":
                return String.format(INSERT_PATTERN,RELATION_TYPE_ALLOWED_PART_OF_SPEECH_NAME,
                        "[relation_type_id],[part_of_speech_id]",insert);
            case "RelationTypeEntity":
                return String.format(INSERT_PATTERN,RELATION_TYPE_NAME,
                        "[id],[auto_reverse],[multilingual],[description_id],[display_text_id],[name_id],[parent_relation_type_id],[relation_argument],[reverse_relation_type_id],[short_display_text_id],[color],[node_position],[priority]",insert);
            case "SenseAttributeEntity":
                return String.format(INSERT_PATTERN,SENSE_ATTRIBUTE_NAME,
                        "[sense_id],[comment],[definition],[link],[register_id],[aspect_id],[user_id],[error_comment],[proper_name]",insert);
            case "SenseEntity":
                return String.format(INSERT_PATTERN,SENSE_NAME,
                        "[id],[synset_position],[variant],[domain_id],[lexicon_id],[part_of_speech_id],[synset_id],[word_id],[status_id]",insert);
            case "SenseExampleEntity":
                return String.format(INSERT_PATTERN,SENSE_EXAMPLE_NAME,
                        "[id],[sense_attribute_id],[example],[type]",insert);
            case "SenseRelationEntity":
                return String.format(INSERT_PATTERN,SENSE_RELATION_NAME,
                        "[id],[child_sense_id],[parent_sense_id],[relation_type_id]",insert);
            case "SynsetAttributeEntity":
                return String.format(INSERT_PATTERN,SYNSET_ATTRIBUTE_NAME,
                        "[synset_id],[comment],[definition],[princeton_id],[owner_id],[error_comment],[ili_id]",insert);
            case "SynsetEntity":
                return String.format(INSERT_PATTERN,SYNSET_NAME,
                        "[id],[split],[lexicon_id],[status_id],[abstract]",insert);
            case "SynsetExampleEntity":
                return String.format(INSERT_PATTERN,SYNSET_EXAMPLE_NAME,
                        "[id],[synset_attributes_id],[example],[type]",insert);
            case "SynsetRelationEntity":
                return String.format(INSERT_PATTERN,SYNSET_RELATION_NAME,
                        "[id],[child_synset_id],[parent_synset_id],[synset_relation_type_id]",insert);
        }
        return "";
    }

    public static String getInsertApplicationLocalisedQuery(List<ApplicationLocalisedStringEntity> elements, String table_name){
        String insert = "";
        for(ApplicationLocalisedStringEntity element : elements){
            insert += "(" + putValuesInString(new Object[]{element.getId(),element.getValue(),element.getLanguage()})
                    + "),";
        }
        String query = String.format(INSERT_PATTERN,table_name,
                "[id],[value],[language]",insert.substring(0,insert.length()-1));
        return query;
    }

    public static String getCreateDictionaryQuery(){
        String query = String.format(CREATE_PATTERN,DICTIONARY_NAME,
                "  [dtype] varchar(31) NOT NULL," +
                "  [id] bigint(20) NOT NULL," +
                "  [description_id] bigint(20) DEFAULT NULL," +
                "  [name_id] bigint(20) DEFAULT NULL," +
                "  [tag] varchar(20) DEFAULT NULL," +
                "  [value] bigint(20) DEFAULT NULL," +
                "  PRIMARY KEY ([id])," +
                //"  KEY `FKflyxm5y0r293f9s1sv4q7weix` ([description_id])," +
                //"  KEY `FK11lr8u8vfj0m3dv9hmxpj5653` ([name_id])," +
                "  CONSTRAINT FK11lr8u8vfj0m3dv9hmxpj5653 FOREIGN KEY ([name_id]) REFERENCES [application_localised_string] ([id])," +
                "  CONSTRAINT FKflyxm5y0r293f9s1sv4q7weix FOREIGN KEY ([description_id]) REFERENCES [application_localised_string] ([id])",
                ""
        );
        return query;
    }

    public static String getInsertDictionaryQuery(List<DictionaryEntity> elements, String table_name){
        String insert = "";
        for(DictionaryEntity element : elements){
            insert += "("
                    + putValuesInString(new Object[]{
                            element.getDtype(),
                            element.getId(),
                            element.getDescription_id(),
                            element.getName_id(),
                            element.getTag(),
                            element.getValue()})
                    + "),";
        }
        String query = String.format(INSERT_PATTERN,table_name,
                "[dtype],[id],[description_id],[name_id],[tag],[value]",insert.substring(0,insert.length()-1));
        return query;
    }

    public static String getCreateDomainQuery(){
        String query = String.format(CREATE_PATTERN,DOMAIN_NAME,
                "  [id] bigint(20) NOT NULL ,\n" +
                "  [description_id] bigint(20) DEFAULT NULL,\n" +
                "  [name_id] bigint(20) DEFAULT NULL,\n" +
                "  PRIMARY KEY ([id]),\n" +
                //"  KEY FKhgtdmfui3wtjng46asuqfa79b ([description_id]),\n" +
                //"  KEY FKilj10y6a5e5wvfxr4otivxy8f ([name_id]),\n" +
                "  CONSTRAINT [FKhgtdmfui3wtjng46asuqfa79b] FOREIGN KEY ([description_id]) REFERENCES [application_localised_string] ([id]),\n" +
                "  CONSTRAINT [FKilj10y6a5e5wvfxr4otivxy8f] FOREIGN KEY ([name_id]) REFERENCES [application_localised_string] ([id])",
                ""
        );
        return query;
    }

    public static String getInsertDomainQuery(List<DomainEntity> elements, String table_name){
        String insert = "";
        for(DomainEntity element : elements){
            insert += "("
                    + putValuesInString(new Object[]{
                    element.getId(),
                    element.getDescription_id(),
                    element.getName_id()})
                    + "),";
        }
        String query = String.format(INSERT_PATTERN,table_name,
                "[id],[description_id],[name_id]",insert.substring(0,insert.length()-1));
        return query;
    }

    public static String getCreateLexiconQuery(){
        String query = String.format(CREATE_PATTERN,LEXICON_NAME,
                "  [id] bigint(20) NOT NULL ,\n" +
                "  [identifier] varchar(255) NOT NULL,\n" +
                "  [language_name] varchar(255) NOT NULL,\n" +
                "  [name] varchar(255) NOT NULL,\n" +
                "  [lexicon_version] varchar(255) NOT NULL,\n" +
                "  PRIMARY KEY ([id])",
                ""
        );
        return query;
    }

    public static String getInsertLexiconQuery(List<LexiconEntity> elements, String table_name){
        String insert = "";
        for(LexiconEntity element : elements){
            insert += "("
                    + putValuesInString(new Object[]{
                    element.getId(),
                    element.getIdentifier(),
                    element.getLanguage_name(),
                    element.getName(),
                    element.getLexicon_version()})
                    + "),";
        }
        String query = String.format(INSERT_PATTERN,table_name,
                "[id],[identifier],[language_name],[name],[lexicon_version]",insert.substring(0,insert.length()-1));
        return query;
    }

    public static String getCreateEmotionalAnnotationQuery(){
        String query = String.format(CREATE_PATTERN,EMOTIONAL_ANNOTATION_NAME,
                "  [id] bigint(20) NOT NULL ,\n" +
                "  [sense_id] bigint(20) NOT NULL,\n" +
                "  [has_emotional_characteristic] bit(1) NOT NULL DEFAULT 0,\n" +
                "  [super_anotation] bit(1) NOT NULL DEFAULT 0,\n" +
                "  [emotions] varchar(255) DEFAULT NULL,\n" +
                "  [valuations] varchar(255) DEFAULT NULL,\n" +
                "  [markedness] varchar(255) DEFAULT NULL,\n" +
                "  [example1] varchar(512) DEFAULT NULL,\n" +
                "  [example2] varchar(512) DEFAULT NULL,\n" +
                "  PRIMARY KEY ([id]),\n" +
                //"  KEY [FKj3d2urv1wi643wzxcvefrewfdsvc] ([sense_id]),\n" +
                "  CONSTRAINT [FKj3d2urv1wi643wzxcvefrewfdsvc] FOREIGN KEY ([sense_id]) REFERENCES [sense] ([id])",
                ""
        );
        return query;
    }

    public static String getInsertEmotionalAnnotationQuery(List<EmotionalAnnotationEntity> elements, String table_name){
        String insert = "";
        for(EmotionalAnnotationEntity element : elements){
            insert += "("
                    + putValuesInString(new Object[]{
                    element.getId(),
                    element.getSense_id(),
                    element.isHas_emotional_characteristic(),
                    element.isSuper_anotation(),
                    element.getEmotions(),
                    element.getValuations(),
                    element.getMarkedness(),
                    element.getExample1(),
                    element.getExample2()})
                    + "),";
        }
        String query = String.format(INSERT_PATTERN,table_name,
                "[id],[sense_id],[has_emotional_characteristic],[super_anotation],[emotions],[valuations],[markedness],[example1],[example2]",insert.substring(0,insert.length()-1));
        return query;
    }

    public static String getCreatePartOfSpeechQuery(){
        String query = String.format(CREATE_PATTERN,PART_OF_SPEECH_NAME,
                "  [id] bigint(20) NOT NULL ,\n" +
                "  [name_id] bigint(20) DEFAULT NULL,\n" +
                "  [color] varchar(255) DEFAULT NULL,\n" +
                "  PRIMARY KEY ([id]),\n" +
                //"  KEY [FKqgj4aq3ne5hjb61eo7gagdngw] ([name_id]),\n" +
                "  CONSTRAINT [FKqgj4aq3ne5hjb61eo7gagdngw] FOREIGN KEY ([name_id]) REFERENCES [application_localised_string] ([id])",
                ""
        );
        return query;
    }

    public static String getInsertPartOfSpeechQuery(List<PartOfSpeechEntity> elements, String table_name){
        String insert = "";
        for(PartOfSpeechEntity element : elements){
            insert += "("
                    + putValuesInString(new Object[]{
                    element.getId(),
                    element.getName_id(),
                    element.getColor()})
                    + "),";
        }
        String query = String.format(INSERT_PATTERN,table_name,
                "[id],[name_id],[color]",insert.substring(0,insert.length()-1));
        return query;
    }

    public static String getCreateWordQuery(){
        String query = String.format(CREATE_PATTERN,WORD_NAME,
                "  [id] bigint(20) NOT NULL ,\n" +
                "  [word] varchar(255) NOT NULL,\n" +
                "  PRIMARY KEY ([id])",
                //"  KEY [word_index] ([word])",
                ""
        );
        return query;
    }

    public static String getInsertWordQuery(List<WordEntity> elements, String table_name){
        String insert = "";
        for(WordEntity element : elements){
            insert += "("
                    + putValuesInString(new Object[]{
                    element.getId(),
                    element.getWord()})
                    + "),";
        }
        String query = String.format(INSERT_PATTERN,table_name,
                "[id],[word]",insert.substring(0,insert.length()-1));
        return query;
    }

    public static String getCreateRelationTypeAllowedLexiconQuery(){
        String query = String.format(CREATE_PATTERN,RELATION_TYPE_ALLOWED_LEXICON_NAME,
                "  [relation_type_id] bigint(20) NOT NULL,\n" +
                "  [lexicon_id] bigint(20) NOT NULL,\n" +
                "  PRIMARY KEY ([relation_type_id],[lexicon_id]),\n" +
                //"  KEY [FK5ynuaw5d0qyhywfxj0u8vxuyl] ([lexicon_id]),\n" +
                "  CONSTRAINT [FK1te1f64fg0gdrsp8whnxsk5ux] FOREIGN KEY ([relation_type_id]) REFERENCES [relation_type] ([id]),\n" +
                "  CONSTRAINT [FK5ynuaw5d0qyhywfxj0u8vxuyl] FOREIGN KEY ([lexicon_id]) REFERENCES [lexicon] ([id])",
                ""
        );
        return query;
    }

    public static String getInsertRelationTypeAllowedLexiconQuery(List<RelationTypeAllowedLexiconEntity> elements, String table_name){
        String insert = "";
        for(RelationTypeAllowedLexiconEntity element : elements){
            insert += "("
                    + putValuesInString(new Object[]{
                    element.getRelation_type_id(),
                    element.getLexicon_id()})
                    + "),";
        }
        String query = String.format(INSERT_PATTERN,table_name,
                "[relation_type_id],[lexicon_id]",insert.substring(0,insert.length()-1));
        return query;
    }

    public static String getCreateRelationTypeAllowedPartOfSpeechQuery(){
        String query = String.format(CREATE_PATTERN,RELATION_TYPE_ALLOWED_PART_OF_SPEECH_NAME,
                "  [relation_type_id] bigint(20) NOT NULL,\n" +
                "  [part_of_speech_id] bigint(20) NOT NULL,\n" +
                "  PRIMARY KEY ([relation_type_id],[part_of_speech_id]),\n" +
                //"  KEY [FK5ynuaw5d0qyhywfxj0u8vxuylzxc] ([part_of_speech_id]),\n" +
                "  CONSTRAINT [FK5ynuaw5d0qyhywfxj0u8vxuylzxc] FOREIGN KEY ([part_of_speech_id]) REFERENCES [part_of_speech] ([id]),\n" +
                "  CONSTRAINT [FK5ynuaw5d0qyhywfxj0u8vxuylzxd] FOREIGN KEY ([relation_type_id]) REFERENCES [relation_type] ([id])",
                ""
        );
        return query;
    }

    public static String getInsertRelationTypeAllowedPartOfSpeechQuery(List<RelationTypeAllowedPartOfSpeechEntity> elements, String table_name){
        String insert = "";
        for(RelationTypeAllowedPartOfSpeechEntity element : elements){
            insert += "("
                    + putValuesInString(new Object[]{
                    element.getRelation_type_id(),
                    element.getPart_of_speech_id()})
                    + "),";
        }
        String query = String.format(INSERT_PATTERN,table_name,
                "[relation_type_id],[part_of_speech_id]",insert.substring(0,insert.length()-1));
        return query;
    }

    public static String getCreateRelationTypeQuery(){
        String query = String.format(CREATE_PATTERN,RELATION_TYPE_NAME,
                "  [id] bigint(20) NOT NULL ,\n" +
                "  [auto_reverse] bit(1) NOT NULL DEFAULT 0,\n" +
                "  [multilingual] bit(1) NOT NULL DEFAULT 0,\n" +
                "  [description_id] bigint(20) DEFAULT NULL,\n" +
                "  [display_text_id] bigint(20) DEFAULT NULL,\n" +
                "  [name_id] bigint(20) DEFAULT NULL,\n" +
                "  [parent_relation_type_id] bigint(20) DEFAULT NULL,\n" +
                "  [relation_argument] varchar(255) DEFAULT NULL ,\n" +
                "  [reverse_relation_type_id] bigint(20) DEFAULT NULL,\n" +
                "  [short_display_text_id] bigint(20) DEFAULT NULL ,\n" +
                "  [color] varchar(255) DEFAULT NULL ,\n" +
                "  [node_position] varchar(255) DEFAULT NULL ,\n" +
                "  [priority] int(11) DEFAULT NULL,\n" +
                "  PRIMARY KEY ([id]),\n" +
                //"  KEY [FK3qs6td1pvv97n4834gc95s1w] ([description_id]),\n" +
                //"  KEY [FK7nfuf14f6hfcb6goi3bqbqgms] ([display_text_id]),\n" +
                //"  KEY [FKk1msw7t7lxfr5ciyfqpvdncip] ([name_id]),\n" +
                //"  KEY [FK8k2lma1x3l6nm7rm7rjxjx3a9] ([parent_relation_type_id]),\n" +
                //"  KEY [FK6bdgdngxm2rl0vium1q98i9c1] ([reverse_relation_type_id]),\n" +
                //"  KEY [FKkd3s4gwfo72pasivl4jvtqnr9] ([short_display_text_id]),\n" +
                "  CONSTRAINT [FK3qs6td1pvv97n4834gc95s1w] FOREIGN KEY ([description_id]) REFERENCES [application_localised_string] ([id]),\n" +
                "  CONSTRAINT [FK6bdgdngxm2rl0vium1q98i9c1] FOREIGN KEY ([reverse_relation_type_id]) REFERENCES [relation_type] ([id]),\n" +
                "  CONSTRAINT [FK7nfuf14f6hfcb6goi3bqbqgms] FOREIGN KEY ([display_text_id]) REFERENCES [application_localised_string] ([id]),\n" +
                "  CONSTRAINT [FK8k2lma1x3l6nm7rm7rjxjx3a9] FOREIGN KEY ([parent_relation_type_id]) REFERENCES [relation_type] ([id]),\n" +
                "  CONSTRAINT [FKk1msw7t7lxfr5ciyfqpvdncip] FOREIGN KEY ([name_id]) REFERENCES [application_localised_string] ([id]),\n" +
                "  CONSTRAINT [FKkd3s4gwfo72pasivl4jvtqnr9] FOREIGN KEY ([short_display_text_id]) REFERENCES [application_localised_string] ([id])",
                ""
        );
        return query;
    }

    public static String getInsertRelationTypeQuery(List<RelationTypeEntity> elements, String table_name){
        String insert = "";
        for(RelationTypeEntity element : elements){
            insert += "("
                    + putValuesInString(new Object[]{
                    element.getId(),
                    element.isAuto_reverse(),
                    element.isMultilingual(),
                    element.getDescription_id(),
                    element.getDisplay_text_id(),
                    element.getName_id(),
                    element.getParent_relation_type_id(),
                    element.getRelation_argument(),
                    element.getReverse_relation_type_id(),
                    element.getShort_display_text_id(),
                    element.getColor(),
                    element.getNode_position(),
                    element.getPriority()})
                    + "),";
        }
        String query = String.format(INSERT_PATTERN,table_name,
                "[id],[auto_reverse],[multilingual],[description_id],[display_text_id],[name_id],[parent_relation_type_id],[relation_argument],[reverse_relation_type_id],[short_display_text_id],[color],[node_position],[priority]",insert.substring(0,insert.length()-1));
        return query;
    }

    public static String getCreateSenseAttributeQuery(){
        String query = String.format(CREATE_PATTERN,SENSE_ATTRIBUTE_NAME,
                "  [sense_id] bigint(20) NOT NULL,\n" +
                "  [comment] text,\n" +
                "  [definition] text,\n" +
                "  [link] varchar(255) DEFAULT NULL,\n" +
                "  [register_id] bigint(20) DEFAULT NULL,\n" +
                "  [aspect_id] bigint(20) DEFAULT NULL,\n" +
                "  [user_id] bigint(20) DEFAULT NULL,\n" +
                "  [error_comment] text,\n" +
                "  [proper_name] bit(1) NOT NULL DEFAULT 0,\n" +
                "  PRIMARY KEY ([sense_id]),\n" +
                "  CONSTRAINT [FKjevbefuvttet3sb4u1h8h4gys] FOREIGN KEY ([sense_id]) REFERENCES [sense] ([id])",
                ""
        );
        return query;
    }

    public static String getInsertSenseAttributeQuery(List<SenseAttributeEntity> elements, String table_name){
        String insert = "";
        for(SenseAttributeEntity element : elements){
            insert += "("
                    + putValuesInString(new Object[]{
                    element.getSense_id(),
                    element.getComment(),
                    element.getDefinition(),
                    element.getLink(),
                    element.getRegister_id(),
                    element.getAspect_id(),
                    element.getUser_id(),
                    element.getError_comment(),
                    element.isProper_name()})
                    + "),";
        }
        String query = String.format(INSERT_PATTERN,table_name,
                "[sense_id],[comment],[definition],[link],[register_id],[aspect_id],[user_id],[error_comment],[proper_name]",insert.substring(0,insert.length()-1));
        return query;
    }

    public static String getCreateSenseQuery(){
        String query = String.format(CREATE_PATTERN,SENSE_NAME,
                "  [id] bigint(20) NOT NULL ,\n" +
                "  [synset_position] int(11) DEFAULT NULL,\n" +
                "  [variant] int(11) NOT NULL DEFAULT 1,\n" +
                "  [domain_id] bigint(20) NOT NULL ,\n" +
                "  [lexicon_id] bigint(20) NOT NULL ,\n" +
                "  [part_of_speech_id] bigint(20) NOT NULL ,\n" +
                "  [synset_id] bigint(20) DEFAULT NULL ,\n" +
                "  [word_id] bigint(20) NOT NULL,\n" +
                "  [status_id] bigint(20) DEFAULT NULL,\n" +
                "  PRIMARY KEY ([id]),\n" +
                //"  KEY [FKeuhrtymboieklw932horawdvk] ([domain_id]),\n" +
                //"  KEY [FKa45bf1te6qdk0wu7441xrdhvv] ([lexicon_id]),\n" +
                //"  KEY [FKjvdptha3oq3lsr3kt3f04lo5u] ([part_of_speech_id]),\n" +
                //"  KEY [FKk1w1bikgc6pcqsm4v5jbnahdq] ([synset_id]),\n" +
                //"  KEY [FK98i2qhqmrcfki79ul7ua8tup7] ([word_id]),\n" +
                "  CONSTRAINT [FK98i2qhqmrcfki79ul7ua8tup7] FOREIGN KEY ([word_id]) REFERENCES [word] ([id]),\n" +
                "  CONSTRAINT [FKa45bf1te6qdk0wu7441xrdhvv] FOREIGN KEY ([lexicon_id]) REFERENCES [lexicon] ([id]),\n" +
                "  CONSTRAINT [FKeuhrtymboieklw932horawdvk] FOREIGN KEY ([domain_id]) REFERENCES [domain] ([id]),\n" +
                "  CONSTRAINT [FKjvdptha3oq3lsr3kt3f04lo5u] FOREIGN KEY ([part_of_speech_id]) REFERENCES [part_of_speech] ([id]),\n" +
                "  CONSTRAINT [FKk1w1bikgc6pcqsm4v5jbnahdq] FOREIGN KEY ([synset_id]) REFERENCES [synset] ([id])",
                ""
        );
        return query;
    }

    public static String getInsertSenseQuery(List<SenseEntity> elements, String table_name){
        String insert = "";
        for(SenseEntity element : elements){
            insert += "("
                    + putValuesInString(new Object[]{
                    element.getId(),
                    element.getSynset_position(),
                    element.getVariant(),
                    element.getDomain_id(),
                    element.getLexicon_id(),
                    element.getPart_of_speech_id(),
                    element.getSynset_id(),
                    element.getWord_id(),
                    element.getStatus_id()})
                    + "),";
        }
        String query = String.format(INSERT_PATTERN,table_name,
                "[id],[synset_position],[variant],[domain_id],[lexicon_id],[part_of_speech_id],[synset_id],[word_id],[status_id]",insert.substring(0,insert.length()-1));
        return query;
    }

    public static String getCreateSenseExampleQuery(){
        String query = String.format(CREATE_PATTERN,SENSE_EXAMPLE_NAME,
                "  [id] bigint(20) NOT NULL ,\n" +
                "  [sense_attribute_id] bigint(20) NOT NULL,\n" +
                "  [example] text,\n" +
                "  [type] varchar(30) NOT NULL,\n" +
                "  PRIMARY KEY ([id]),\n" +
                //"  KEY [FK8vf5o4pb6dmm3jmy1npt7snxe] ([sense_attribute_id]),\n" +
                "  CONSTRAINT [FK8vf5o4pb6dmm3jmy1npt7snxe] FOREIGN KEY ([sense_attribute_id]) REFERENCES [sense_attributes] ([sense_id])",
                ""
        );
        return query;
    }

    public static String getInsertSenseExampleQuery(List<SenseExampleEntity> elements, String table_name){
        String insert = "";
        for(SenseExampleEntity element : elements){
            insert += "("
                    + putValuesInString(new Object[]{
                    element.getId(),
                    element.getSense_attribute_id(),
                    element.getExample(),
                    element.getType()})
                    + "),";
        }
        String query = String.format(INSERT_PATTERN,table_name,
                "[id],[sense_attribute_id],[example],[type]",insert.substring(0,insert.length()-1));
        return query;
    }

    public static String getCreateSenseRelationQuery(){
        String query = String.format(CREATE_PATTERN,SENSE_RELATION_NAME,
                "  [id] bigint(20) NOT NULL ,\n" +
                "  [child_sense_id] bigint(20) NOT NULL,\n" +
                "  [parent_sense_id] bigint(20) NOT NULL,\n" +
                "  [relation_type_id] bigint(20) NOT NULL,\n" +
                "  PRIMARY KEY ([id]),\n" +
                //"  KEY [FKk682ashm51g6a7u4unytrt1ic] ([child_sense_id]),\n" +
                //"  KEY [FKprx8p7wb6h19eavxc1wjvnbhf] ([parent_sense_id]),\n" +
                //"  KEY [FKddrqi5c2vnofp8wdrbsgcw3ct] ([relation_type_id]),\n" +
                "  CONSTRAINT [FKddrqi5c2vnofp8wdrbsgcw3ct] FOREIGN KEY ([relation_type_id]) REFERENCES [relation_type] ([id]),\n" +
                "  CONSTRAINT [FKk682ashm51g6a7u4unytrt1ic] FOREIGN KEY ([child_sense_id]) REFERENCES [sense] ([id]),\n" +
                "  CONSTRAINT [FKprx8p7wb6h19eavxc1wjvnbhf] FOREIGN KEY ([parent_sense_id]) REFERENCES [sense] ([id])",
                ""
        );
        return query;
    }

    public static String getInsertSenseRelationQuery(List<SenseRelationEntity> elements, String table_name){
        String insert = "";
        for(SenseRelationEntity element : elements){
            insert += "("
                    + putValuesInString(new Object[]{
                    element.getId(),
                    element.getChild_sense_id(),
                    element.getParent_sense_id(),
                    element.getRelation_type_id()})
                    + "),";
        }
        String query = String.format(INSERT_PATTERN,table_name,
                "[id],[child_sense_id],[parent_sense_id],[relation_type_id]",insert.substring(0,insert.length()-1));
        return query;
    }

    public static String getCreateSynsetAttributeQuery(){
        String query = String.format(CREATE_PATTERN,SYNSET_ATTRIBUTE_NAME,
                "  [synset_id] bigint(20) NOT NULL,\n" +
                "  [comment] text,\n" +
                "  [definition] text,\n" +
                "  [princeton_id] varchar(255) DEFAULT NULL,\n" +
                "  [owner_id] bigint(20) DEFAULT NULL,\n" +
                "  [error_comment] text,\n" +
                "  [ili_id] varchar(255) DEFAULT NULL,\n" +
                "  PRIMARY KEY ([synset_id]),\n" +
                //"  KEY [FKd4daq7s6mjs49n2flpjndk0ob] ([owner_id]),\n" +
                "  CONSTRAINT [FKd4daq7s6mjs49n2flpjndk0ob] FOREIGN KEY ([owner_id]) REFERENCES [users] ([id]),\n" +
                "  CONSTRAINT [FKlru0bqxvyea356fr15w2wdu7i] FOREIGN KEY ([synset_id]) REFERENCES [synset] ([id])",
                ""
        );
        return query;
    }

    public static String getInsertSynsetAttributeQuery(List<SynsetAttributeEntity> elements, String table_name){
        String insert = "";
        for(SynsetAttributeEntity element : elements){
            insert += "("
                    + putValuesInString(new Object[]{
                    element.getSynset_id(),
                    element.getComment(),
                    element.getDefinition(),
                    element.getPrinceton_id(),
                    element.getOwner_id(),
                    element.getError_comment(),
                    element.getIli_id()})
                    + "),";
        }
        String query = String.format(INSERT_PATTERN,table_name,
                "[synset_id],[comment],[definition],[princeton_id],[owner_id],[error_comment],[ili_id]",insert.substring(0,insert.length()-1));
        return query;
    }

    public static String getCreateSynsetQuery(){
        String query = String.format(CREATE_PATTERN,SYNSET_NAME,
                "  [id] bigint(20) NOT NULL ,\n" +
                "  [split] int(11) DEFAULT NULL,\n" +
                "  [lexicon_id] bigint(20) NOT NULL,\n" +
                "  [status_id] bigint(20) DEFAULT NULL,\n" +
                "  [abstract] tinyint(1) DEFAULT NULL,\n" +
                "  PRIMARY KEY ([id]),\n" +
                //"  KEY [FKfxflmrbnq64hax2r7gs1gbeuj] ([lexicon_id]),\n" +
                "  CONSTRAINT [FKfxflmrbnq64hax2r7gs1gbeuj] FOREIGN KEY ([lexicon_id]) REFERENCES [lexicon] ([id])",
                ""
        );
        return query;
    }

    public static String getInsertSynsetQuery(List<SynsetEntity> elements, String table_name){
        String insert = "";
        for(SynsetEntity element : elements){
            insert += "("
                    + putValuesInString(new Object[]{
                    element.getId(),
                    element.getSplit(),
                    element.getLexicon_id(),
                    element.getStatus_id(),
                    element.getAbstract()})
                    + "),";
        }
        String query = String.format(INSERT_PATTERN,table_name,
                "[id],[split],[lexicon_id],[status_id],[abstract]",insert.substring(0,insert.length()-1));
        return query;
    }

    public static String getCreateSynsetExampleQuery(){
        String query = String.format(CREATE_PATTERN,SYNSET_EXAMPLE_NAME,
                "  [id] bigint(20) NOT NULL ,\n" +
                "  [synset_attributes_id] bigint(20) NOT NULL,\n" +
                "  [example] text,\n" +
                "  [type] varchar(30) DEFAULT NULL,\n" +
                "  PRIMARY KEY ([id]),\n" +
                //"  KEY [FK3po12pm1bqwwgq9ejvlrvg4sx] ([synset_attributes_id]),\n" +
                "  CONSTRAINT [FK3po12pm1bqwwgq9ejvlrvg4sx] FOREIGN KEY ([synset_attributes_id]) REFERENCES [synset_attributes] ([synset_id])",
                ""
        );
        return query;
    }

    public static String getInsertSynsetExampleQuery(List<SynsetExampleEntity> elements, String table_name){
        String insert = "";
        for(SynsetExampleEntity element : elements){
            insert += "("
                    + putValuesInString(new Object[]{
                    element.getId(),
                    element.getSynset_attributes_id(),
                    element.getExample(),
                    element.getType()})
                    + "),";
        }
        String query = String.format(INSERT_PATTERN,table_name,
                "[id],[synset_attributes_id],[example],[type]",insert.substring(0,insert.length()-1));
        return query;
    }

    public static String getCreateSynsetRelationQuery(){
        String query = String.format(CREATE_PATTERN,SYNSET_RELATION_NAME,
                "  [id] bigint(20) NOT NULL ,\n" +
                "  [child_synset_id] bigint(20) NOT NULL,\n" +
                "  [parent_synset_id] bigint(20) NOT NULL,\n" +
                "  [synset_relation_type_id] bigint(20) NOT NULL,\n" +
                "  PRIMARY KEY ([id]),\n" +
                //"  KEY [FK4q4yini7xmac0dojilalv3l6j] ([child_synset_id]),\n" +
                //"  KEY [FKhcndh5xtn9k4pcrjb8ur9e1oy] ([parent_synset_id]),\n" +
                //"  KEY [FKj3d2urv1wi643w7y6ovlei5q] ([synset_relation_type_id]),\n" +
                "  CONSTRAINT [FK4q4yini7xmac0dojilalv3l6j] FOREIGN KEY ([child_synset_id]) REFERENCES [synset] ([id]),\n" +
                "  CONSTRAINT [FKhcndh5xtn9k4pcrjb8ur9e1oy] FOREIGN KEY ([parent_synset_id]) REFERENCES [synset] ([id]),\n" +
                "  CONSTRAINT [FKj3d2urv1wi643w7y6ovlei5q] FOREIGN KEY ([synset_relation_type_id]) REFERENCES [relation_type] ([id])",
                ""
        );
        return query;
    }

    public static String getInsertSynsetRelationQuery(List<SynsetRelationEntity> elements, String table_name){
        String insert = "";
        for(SynsetRelationEntity element : elements){
            insert += "("
                    + putValuesInString(new Object[]{
                    element.getId(),
                    element.getChild_synset_id(),
                    element.getParent_synset_id(),
                    element.getSynset_relation_type_id()})
                    + "),";
        }
        String query = String.format(INSERT_PATTERN,table_name,
                "[id],[child_synset_id],[parent_synset_id],[synset_relation_type_id]",insert.substring(0,insert.length()-1));
        return query;
    }




    private static String putValuesInString(Object[] values){
        String result = "";
        for(int i=0; i<values.length; i++){
            if(values[i]==null){
                result+="NULL,";
            }
            else if(values[i] instanceof String){
                if(((String)values[i]).contains("'"))
                    ((String)values[i]).replaceAll("'","\'");
                result+="'"+values[i]+"',";
            }
            else{
                result+=values[i]+",";
            }

        }
        result = (result.length()>0 ? result.substring(0,result.length()-1) : result);
        return result;
    }

    public static <T> String getTableNameForEntity(Class<T> clazz){
        switch(clazz.getSimpleName()){
            case "ApplicationLocalisedStringEntity":
                return APPLICATION_LOCALISED_STRING_NAME;
            case "DictionaryEntity":
                return DICTIONARY_NAME;
            case "DomainEntity":
                return DOMAIN_NAME;
            case "LexiconEntity":
                return LEXICON_NAME;
            case "EmotionalAnnotationEntity":
                return EMOTIONAL_ANNOTATION_NAME;
            case "PartOfSpeechEntity":
                return PART_OF_SPEECH_NAME;
            case "WordEntity":
                return WORD_NAME;
            case "RelationTypeAllowedLexiconEntity":
                return RELATION_TYPE_ALLOWED_LEXICON_NAME;
            case "RelationTypeAllowedPartOfSpeechEntity":
                return RELATION_TYPE_ALLOWED_PART_OF_SPEECH_NAME;
            case "RelationTypeEntity":
                return RELATION_TYPE_NAME;
            case "SenseAttributeEntity":
                return SENSE_ATTRIBUTE_NAME;
            case "SenseEntity":
                return SENSE_NAME;
            case "SenseExampleEntity":
                return SENSE_EXAMPLE_NAME;
            case "SenseRelationEntity":
                return SENSE_RELATION_NAME;
            case "SynsetAttributeEntity":
                return SYNSET_ATTRIBUTE_NAME;
            case "SynsetEntity":
                return SYNSET_NAME;
            case "SynsetExampleEntity":
                return SYNSET_EXAMPLE_NAME;
            case "SynsetRelationEntity":
                return SYNSET_RELATION_NAME;
        }
        return null;
    }
}
