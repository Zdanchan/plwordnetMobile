package com.pwr.bzapps.plwordnetmobile.service.controller;

import com.pwr.bzapps.plwordnetmobile.service.advisor.Advisor;
import com.pwr.bzapps.plwordnetmobile.service.cache.StringCache;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.ApplicationLocalisedStringEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.DictionaryEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.DomainEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.application.LexiconEntity;
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
import com.pwr.bzapps.plwordnetmobile.service.database.repository.application.LexiconRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.properties.TablesRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.sense.SenseAttributeRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.sense.SenseRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.synset.SynsetAttributeRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.synset.SynsetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

@Controller
@RequestMapping(path="/db_controller")
public class DBController {

    private Logger log = LoggerFactory.getLogger(DBController.class);
    private final String QUERY_DIRECTORY = "/downloads/queries/";
    private final String TMP_DIRECTORY = "/downloads/tmp/";
    private final String[] languages = {"polish", "english"};

    @Autowired
    private TablesRepository tablesRepository;
    @Autowired
    private LexiconRepository lexiconRepository;
    @Autowired
    private SenseRepository senseRepository;
    @Autowired
    private SynsetRepository synsetRepository;
    @Autowired
    private SenseAttributeRepository senseAttributeRepository;
    @Autowired
    private SynsetAttributeRepository synsetAttributeRepository;
    @Autowired
    private DBHelperComponent helper;

    @GetMapping(path="/print_generated_files")
    public @ResponseBody String printGeneratedFiles(){
        String content = "generated files:\n";
        File[] files = (new File(new File("").getAbsolutePath() + TMP_DIRECTORY)).listFiles();
        for (File file : files){
            content+=file.getAbsolutePath()+"\n";
        }
        return content;
    }

    @GetMapping(path="/generate_query_files")
    private @ResponseBody String generateQueryFiles(){
        String response = "";

        //Date last_db_update = tablesRepository.getTablesOrdered().get(0).getUpdate_time();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.HOUR,-1);
        Date last_db_update = c.getTime();

        if(!Advisor.isQuery_generator_processing()){
            Advisor.setQuery_generator_processing(true);
            Thread generator = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        File theDir = new File(new File("").getAbsolutePath() + QUERY_DIRECTORY + "all_in/");
                        if (!theDir.exists()) {
                            log.info("Creating directory for queries: " + theDir.getName());
                            boolean result = false;

                            try{
                                theDir.mkdir();
                                result = true;
                            }
                            catch(SecurityException se){
                                log.error("Cannot create directory for queries ", se);
                            }
                            if(result) {
                                log.info("Directory created: " + theDir.getName());
                            }
                        }

                        //Create tables part
                        if (!(new File(theDir.getAbsolutePath() + "/db_create_query.q")).exists()) {
                            log.info("Initialazed Create query generation");
                            String create = "";
                            create += SQLExporter.getCreateApplicationLocalisedQuery();
                            create += SQLExporter.getCreateDictionaryQuery();
                            create += SQLExporter.getCreateDomainQuery();
                            create += SQLExporter.getCreateLexiconQuery();
                            create += SQLExporter.getCreatePartOfSpeechQuery();
                            create += SQLExporter.getCreateWordQuery();
                            create += SQLExporter.getCreateRelationTypeQuery();
                            create += SQLExporter.getCreateRelationTypeAllowedLexiconQuery();
                            create += SQLExporter.getCreateRelationTypeAllowedPartOfSpeechQuery();
                            create += SQLExporter.getCreateSynsetQuery();
                            create += SQLExporter.getCreateSynsetAttributeQuery();
                            create += SQLExporter.getCreateSynsetExampleQuery();
                            create += SQLExporter.getCreateSynsetRelationQuery();
                            create += SQLExporter.getCreateSenseQuery();
                            create += SQLExporter.getCreateSenseAttributeQuery();
                            create += SQLExporter.getCreateSenseExampleQuery();
                            create += SQLExporter.getCreateSenseRelationQuery();
                            create += SQLExporter.getCreateEmotionalAnnotationQuery();

                            StringCache.putString("db_create", create);
                            log.info("Create query ready and stored in file");
                            storeQuery(create,theDir.getAbsolutePath() + "/db_create_query.q");
                        }
                        else {
                            log.info("Create query file is up to date");
                        }


                        //Insert values part
                        log.info("Initialazed Insert query generation");
                        String[] file_names = new String[]{
                                "db_insert_application_localised_strings.q",
                                "db_insert_dictionaries.q",
                                "db_insert_domains.q",
                                "db_insert_lexicons.q",
                                "db_insert_parts_of_speech.q",
                                "db_insert_word.q",
                                "db_insert_relation_types.q",
                                "db_insert_relation_type_allowed_lexicons.q",
                                "db_insert_relation_type_allowed_parts_of_speech.q",
                                "db_insert_synsets.q",
                                "db_insert_synset_attributes.q",
                                "db_insert_synset_examples.q",
                                "db_insert_synset_relations.q",
                                "db_insert_senses.q",
                                "db_insert_sense_attributes.q",
                                "db_insert_sense_examples.q",
                                "db_insert_sense_relations.q"
                        };

                        Class[] classes = {
                                ApplicationLocalisedStringEntity.class,
                                DictionaryEntity.class,
                                DomainEntity.class,
                                LexiconEntity.class,
                                PartOfSpeechEntity.class,
                                WordEntity.class,
                                RelationTypeEntity.class,
                                RelationTypeAllowedLexiconEntity.class,
                                RelationTypeAllowedPartOfSpeechEntity.class,
                                SynsetEntity.class,
                                SynsetAttributeEntity.class,
                                SynsetExampleEntity.class,
                                SynsetRelationEntity.class,
                                SenseEntity.class,
                                SenseAttributeEntity.class,
                                SenseExampleEntity.class,
                                SenseRelationEntity.class
                        };


                        for (int i = 0; i < file_names.length; i++) {
                            if (!(new File(theDir.getAbsolutePath() + "/" + file_names[i])).exists()) {
                                log.info("Generating insert query for: " + classes[i].getSimpleName());
                                String insert = "";
                                List entities = helper.findAllForEntity(classes[i]);
                                insert += helper.generateSQLInsertForEntity(entities, classes[i]);
                                entities.clear();
                                log.info("Successfully generated insert query for: " + classes[i].getSimpleName());
                                storeQuery(insert,theDir.getAbsolutePath() + "/" + file_names[i]);
                                log.info("Stored insert query for: " + classes[i].getSimpleName());
                            }
                            else {
                                log.info(classes[i].getSimpleName() + " - insert query file is up to date");
                            }
                        }
                        log.info("Insert queries are stored and up to date");
                    }catch (Exception e){
                        log.error("Exception during query generation: ", e);
                    } finally {
                        Advisor.setQuery_generator_processing(false);
                    }
                }
            });
            generator.start();
            response = "Query generator started";
        }
        else{
            response = "Query generator is already running";
        }

        return response;
    }

    @GetMapping(path="/generate_language_query_files")
    private @ResponseBody String generateLanguageQueryFiles(){
        String response = "";

        //Date last_db_update = tablesRepository.getTablesOrdered().get(0).getUpdate_time();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.HOUR,-1);
        Date last_db_update = c.getTime();

        if(!Advisor.isQuery_generator_processing()){
            Advisor.setQuery_generator_processing(true);
            Thread generator = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        File theDir = new File(new File("").getAbsolutePath() + TMP_DIRECTORY);
                        if (!theDir.exists()) {
                            log.info("Creating directory for queries: " + theDir.getName());
                            boolean result = false;

                            try{
                                theDir.mkdir();
                                result = true;
                            }
                            catch(SecurityException se){
                                log.error("Cannot create directory for queries ", se);
                            }
                            if(result) {
                                log.info("Directory created: " + theDir.getName());
                            }
                        }

                        //Create tables part
                        if (!(new File(theDir.getAbsolutePath() + "/db_create_query.q")).exists()) {
                            log.info("Initialazed Create query generation");
                            String create = "";
                            create += SQLExporter.getCreateApplicationLocalisedQuery();
                            create += SQLExporter.getCreateDictionaryQuery();
                            create += SQLExporter.getCreateDomainQuery();
                            create += SQLExporter.getCreateLexiconQuery();
                            create += SQLExporter.getCreatePartOfSpeechQuery();
                            create += SQLExporter.getCreateWordQuery();
                            create += SQLExporter.getCreateRelationTypeQuery();
                            create += SQLExporter.getCreateRelationTypeAllowedLexiconQuery();
                            create += SQLExporter.getCreateRelationTypeAllowedPartOfSpeechQuery();
                            create += SQLExporter.getCreateSynsetQuery();
                            create += SQLExporter.getCreateSynsetAttributeQuery();
                            create += SQLExporter.getCreateSynsetExampleQuery();
                            create += SQLExporter.getCreateSynsetRelationQuery();
                            create += SQLExporter.getCreateSenseQuery();
                            create += SQLExporter.getCreateSenseAttributeQuery();
                            create += SQLExporter.getCreateSenseExampleQuery();
                            create += SQLExporter.getCreateSenseRelationQuery();
                            create += SQLExporter.getCreateEmotionalAnnotationQuery();

                            StringCache.putString("db_create", create);
                            log.info("Create query ready and stored in file");
                            storeQuery(create,theDir.getAbsolutePath() + "/db_create_query.q");
                        }
                        else {
                            log.info("Create query file is up to date");
                        }


                        //Insert values part
                        log.info("Initialazed Insert query generation");
                        String[] file_names = new String[]{
                                "db_insert_application_localised_strings.q",
                                "db_insert_dictionaries.q",
                                "db_insert_domains.q",
                                "db_insert_lexicons.q",
                                "db_insert_parts_of_speech.q",
                                "db_insert_word.q",
                                "db_insert_relation_types.q",
                                "db_insert_relation_type_allowed_lexicons.q",
                                "db_insert_relation_type_allowed_parts_of_speech.q",
                        };

                        Class[] classes = {
                                ApplicationLocalisedStringEntity.class,
                                DictionaryEntity.class,
                                DomainEntity.class,
                                LexiconEntity.class,
                                PartOfSpeechEntity.class,
                                WordEntity.class,
                                RelationTypeEntity.class,
                                RelationTypeAllowedLexiconEntity.class,
                                RelationTypeAllowedPartOfSpeechEntity.class,
                        };

                        for (int i = 0; i < file_names.length; i++) {
                            if (!(new File(theDir.getAbsolutePath() + "/" + file_names[i])).exists()) {
                                log.info("Generating insert query for: " + classes[i].getSimpleName());
                                String insert = "";
                                List entities = helper.findAllForEntity(classes[i]);
                                insert += helper.generateSQLInsertForEntity(entities, classes[i]);
                                entities.clear();
                                log.info("Successfully generated insert query for: " + classes[i].getSimpleName());
                                storeQuery(insert,theDir.getAbsolutePath() + "/" + file_names[i]);
                            }
                            else {
                                log.info(classes[i].getSimpleName() + " - insert query file is up to date");
                            }
                        }

                        file_names = new String[] {
                                "db_insert_synsets.q",
                                "db_insert_synset_attributes.q",
                                "db_insert_synset_examples.q",
                                "db_insert_synset_relations.q",
                                "db_insert_senses.q",
                                "db_insert_sense_attributes.q",
                                "db_insert_sense_examples.q",
                                "db_insert_sense_relations.q"
                        };


                        for(int i=0; i<languages.length; i++) {
                            Integer[] lexicons = getLexiconsIds(lexiconRepository.getAllLexiconsForLanguage(languages[i]));
                            Integer[] synsets = helper.getIdsOfSynsetsByLanguage(lexicons);
                            Integer[] synset_attributes = helper.getIdsOfSynsetsByLanguage(synsets);
                            Integer[] senses = helper.getIdsOfSensesByLanguage(lexicons);
                            Integer[] senses_attributes = helper.getIdsOfSensesByLanguage(senses);

                            String insert = "";
                            //Synsets
                            if (!(new File(getFileLocalisedName(theDir.getAbsolutePath() + "/" + file_names[0], languages[i])).exists())) {
                                log.info("Generating insert query for: " + SynsetEntity.class.getSimpleName() + " -" + languages[i]);
                                insert = generateInsertQueryForEntities(helper.findSynsetAndSensesAllByRelatedIds(SynsetEntity.class, lexicons), SynsetEntity.class);
                                log.info("Successfully generated insert query for: " + SynsetEntity.class.getSimpleName() + " -" + languages[i]);
                                storeQuery(insert, getFileLocalisedName(theDir.getAbsolutePath() + "/" + file_names[0], languages[i]));
                            }
                            else{
                                log.info(SynsetEntity.class.getSimpleName() + " -" + languages[i] + " - insert query file is up to date");
                            }
                            if (!(new File(getFileLocalisedName(theDir.getAbsolutePath() + "/" + file_names[1], languages[i]))).exists()) {
                                log.info("Generating insert query for: " + SynsetAttributeEntity.class.getSimpleName() + " -" + languages[i]);
                                insert = generateInsertQueryForEntities(helper.findSynsetAndSensesAllByRelatedIds(SynsetAttributeEntity.class, synsets), SynsetAttributeEntity.class);
                                log.info("Successfully generated insert query for: " + SynsetAttributeEntity.class.getSimpleName() + " -" + languages[i]);
                                storeQuery(insert, getFileLocalisedName(theDir.getAbsolutePath() + "/" + file_names[1], languages[i]));
                            }
                            else{
                                log.info(SynsetAttributeEntity.class.getSimpleName() + " -" + languages[i] + " - insert query file is up to date");
                            }
                            if (!(new File(getFileLocalisedName(theDir.getAbsolutePath() + "/" + file_names[2], languages[i]))).exists() && synset_attributes.length!=0) {
                                log.info("Generating insert query for: " + SynsetExampleEntity.class.getSimpleName() + " -" + languages[i]);
                                insert = generateInsertQueryForEntities(helper.findSynsetAndSensesAllByRelatedIds(SynsetExampleEntity.class, synset_attributes), SynsetExampleEntity.class);
                                log.info("Successfully generated insert query for: " + SynsetExampleEntity.class.getSimpleName() + " -" + languages[i]);
                                storeQuery(insert, getFileLocalisedName(theDir.getAbsolutePath() + "/" + file_names[2], languages[i]));
                            }
                            else{
                                log.info(SynsetExampleEntity.class.getSimpleName() + " -" + languages[i] + " - insert query file is up to date");
                            }
                            if (!(new File(getFileLocalisedName(theDir.getAbsolutePath() + "/" + file_names[3], languages[i]))).exists()) {
                                log.info("Generating insert query for: " + SynsetRelationEntity.class.getSimpleName() + " -" + languages[i]);
                                insert = generateInsertQueryForEntities(helper.findSynsetAndSensesAllByRelatedIds(SynsetRelationEntity.class, synsets), SynsetRelationEntity.class);
                                log.info("Successfully generated insert query for: " + SynsetRelationEntity.class.getSimpleName() + " -" + languages[i]);
                                storeQuery(insert, getFileLocalisedName(theDir.getAbsolutePath() + "/" + file_names[3], languages[i]));
                            }
                            else{
                                log.info(SynsetRelationEntity.class.getSimpleName() + " -" + languages[i] + " - insert query file is up to date");
                            }

                            //Senses
                            if (!(new File(getFileLocalisedName(theDir.getAbsolutePath() + "/" + file_names[4], languages[i]))).exists()){
                                log.info("Generating insert query for: " + SenseEntity.class.getSimpleName() + " -" + languages[i]);
                                insert = generateInsertQueryForEntities(helper.findSynsetAndSensesAllByRelatedIds(SenseEntity.class, lexicons), SenseEntity.class);
                                log.info("Successfully generated insert query for: " + SenseEntity.class.getSimpleName() + " -" + languages[i]);
                                storeQuery(insert, getFileLocalisedName(theDir.getAbsolutePath() + "/" + file_names[4], languages[i]));
                            }
                            else{
                                log.info(SenseEntity.class.getSimpleName() + " -" + languages[i] + " - insert query file is up to date");
                            }
                            if (!(new File(getFileLocalisedName(theDir.getAbsolutePath() + "/" + file_names[5], languages[i]))).exists()) {
                                log.info("Generating insert query for: " + SenseAttributeEntity.class.getSimpleName() + " -" + languages[i]);
                                insert = generateInsertQueryForEntities(helper.findSynsetAndSensesAllByRelatedIds(SenseAttributeEntity.class, senses), SenseAttributeEntity.class);
                                log.info("Successfully generated insert query for: " + SenseAttributeEntity.class.getSimpleName() + " -" + languages[i]);
                                storeQuery(insert, getFileLocalisedName(theDir.getAbsolutePath() + "/" + file_names[5], languages[i]));
                            }
                            else{
                                log.info(SenseAttributeEntity.class.getSimpleName() + " -" + languages[i] + " - insert query file is up to date");
                            }
                            if (!(new File(getFileLocalisedName(theDir.getAbsolutePath() + "/" + file_names[6], languages[i]))).exists() && senses_attributes.length!=0) {
                                log.info("Generating insert query for: " + SenseExampleEntity.class.getSimpleName() + " -" + languages[i]);
                                insert = generateInsertQueryForEntities(helper.findSynsetAndSensesAllByRelatedIds(SenseExampleEntity.class, senses_attributes), SenseExampleEntity.class);
                                log.info("Successfully generated insert query for: " + SenseExampleEntity.class.getSimpleName() + " -" + languages[i]);
                                storeQuery(insert, getFileLocalisedName(theDir.getAbsolutePath() + "/" + file_names[6], languages[i]));
                            }
                            else{
                                log.info(SenseExampleEntity.class.getSimpleName() + " -" + languages[i] + " - insert query file is up to date");
                            }
                            if (!(new File(getFileLocalisedName(theDir.getAbsolutePath() + "/" + file_names[7], languages[i]))).exists()) {
                                log.info("Generating insert query for: " + SenseRelationEntity.class.getSimpleName() + " -" + languages[i]);
                                insert = generateInsertQueryForEntities(helper.findSynsetAndSensesAllByRelatedIds(SenseRelationEntity.class, senses), SenseRelationEntity.class);
                                log.info("Successfully generated insert query for: " + SenseRelationEntity.class.getSimpleName() + " -" + languages[i]);
                                storeQuery(insert, getFileLocalisedName(theDir.getAbsolutePath() + "/" + file_names[7], languages[i]));
                            }
                            else{
                                log.info(SenseRelationEntity.class.getSimpleName() + " -" + languages[i] + " - insert query file is up to date");
                            }
                        }

                        //generate connectors
                        HashMap<String,Integer[]> map = getInterLingualRelationsIds();
                        for(String key : map.keySet()){
                            String insert = "";
                            if (!(new File(getFileLocalisedName(theDir.getAbsolutePath() + "/" + file_names[3], key))).exists()) {
                                log.info("Generating insert query for: " + SynsetRelationEntity.class.getSimpleName() + " -" + key + " connector");
                                insert = generateInsertQueryForEntities(helper.getSynsetRelationsByRelationIds(map.get(key)), SynsetRelationEntity.class);
                                storeQuery(insert, getFileLocalisedName(theDir.getAbsolutePath() + "/" + file_names[3], key));
                                log.info("Successfully generated insert query for: " + SynsetRelationEntity.class.getSimpleName() + " -" + key + " connector");
                            }
                            else{
                                log.info(SynsetRelationEntity.class.getSimpleName()  + " -" + key + " connector" + " - insert query file is up to date");
                            }
                            if (!(new File(getFileLocalisedName(theDir.getAbsolutePath() + "/" + file_names[7], key))).exists()) {
                                log.info("Generating insert query for: " + SenseRelationEntity.class.getSimpleName() + " -" + key + " connector");
                                insert = generateInsertQueryForEntities(helper.getSenseRelationsByRelationIds(map.get(key)), SenseRelationEntity.class);
                                storeQuery(insert, getFileLocalisedName(theDir.getAbsolutePath() + "/" + file_names[7], key));
                                log.info("Successfully generated insert query for: " + SenseRelationEntity.class.getSimpleName() + " -" + key + " connector");
                            }
                            else{
                                log.info(SenseRelationEntity.class.getSimpleName()  + " -" + key + " connector" + " - insert query file is up to date");
                            }
                        }

                        moveFromTMP();

                        log.info("Insert queries are stored and up to date");
                    }catch (Exception e){
                        log.error("Exception during query generation: ", e);
                    } finally {
                        Advisor.setQuery_generator_processing(false);
                    }
                }
            });
            generator.start();
            response = "Query generator started";
        }
        else{
            response = "Query generator is already running";
        }

        return response;
    }

    private void moveFromTMP(){
        String[] file_names = new String[]{
                "db_insert_application_localised_strings.q",
                "db_insert_dictionaries.q",
                "db_insert_domains.q",
                "db_insert_lexicons.q",
                "db_insert_parts_of_speech.q",
                "db_insert_word.q",
                "db_insert_relation_types.q",
                "db_insert_relation_type_allowed_lexicons.q",
                "db_insert_relation_type_allowed_parts_of_speech.q",
        };
        String[] localised_file_names = new String[]{
                "db_insert_synsets.q",
                "db_insert_synset_attributes.q",
                "db_insert_synset_examples.q",
                "db_insert_synset_relations.q",
                "db_insert_senses.q",
                "db_insert_sense_attributes.q",
                "db_insert_sense_examples.q",
                "db_insert_sense_relations.q"
        };
        File tmpDir = new File(new File("").getAbsolutePath() + TMP_DIRECTORY);
        File targetDir = new File(new File("").getAbsolutePath() + QUERY_DIRECTORY);
        for(int i=0; i<file_names.length; i++){
            if(new File(tmpDir.getAbsolutePath() + file_names[i]).exists()) {
                File oldFile = new File(targetDir.getAbsolutePath() + file_names[i]);
                if (oldFile.exists())
                    oldFile.delete();
                File newFile = new File(tmpDir.getAbsolutePath() + file_names[i]);
                newFile.renameTo(new File(targetDir.getAbsolutePath() + file_names[i]));
            }
        }

        for(int i=0; i<localised_file_names.length; i++){
            for(int l=0; l<languages.length; l++) {
                if(new File(tmpDir.getAbsolutePath() + file_names[i]).exists()) {
                    String file_name = getFileLocalisedName(localised_file_names[i], languages[l]);
                    File oldFile = new File(targetDir.getAbsolutePath() + file_name);
                    if (oldFile.exists())
                        oldFile.delete();
                    File newFile = new File(tmpDir.getAbsolutePath() + file_name);
                    newFile.renameTo(new File(targetDir.getAbsolutePath() + file_name));
                }
            }
        }
    }

    private HashMap<String,Integer[]> getInterLingualRelationsIds(){
        HashMap<String,Integer[]> map = new HashMap<String,Integer[]>();
        try {
            File file = new File(getClass().getClassLoader().getResource("inter_lingual_relations.r").getFile());
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line!=null){
                String languages = line.substring(0, line.indexOf("="));

                map.put(languages,parseStringList((line.substring(line.indexOf("{")+1,line.indexOf("}")).split(","))));

                line = reader.readLine();
            }

        }catch (IOException ex){

        }
        return map;
    }

    private Integer[] parseStringList(String[] strings){
        Integer[] integers = new Integer[strings.length];
        for(int i=0; i<integers.length; i++){
            integers[i] = Integer.parseInt(strings[i]);
        }
        return  integers;
    }

    private String getFileLocalisedName(String file_name, String language_name){
        return file_name.substring(0,file_name.indexOf(".q")) + "_" + language_name + ".q";
    }

    private String generateInsertQueryForEntities(List entities, Class clazz){
        String insert = "";
        insert += helper.generateSQLInsertForEntity(entities, clazz);
        entities.clear();
        return insert;
    }

    private void storeQuery(String query, String file_name){
        try {
            OutputStreamWriter  outputStreamWriter
                    = new OutputStreamWriter (new FileOutputStream(file_name), StandardCharsets.UTF_8);
            outputStreamWriter.write(query);
            outputStreamWriter.flush();
            outputStreamWriter.close();
        } catch (IOException e) {
            log.error("Exception during create query storing: ", e);
        }
        log.info("Stored insert query for: " + file_name);
    }

    private Integer[] getLexiconsIds(List <LexiconEntity> lexicons){
        LinkedList<Integer> ids = new LinkedList<Integer>();
        for (LexiconEntity lexicon : lexicons){
            ids.add(lexicon.getId());
        }
        return ids.toArray(new Integer[0]);
    }

    @GetMapping(path="/clear_cache")
    private @ResponseBody String clearCache(){
        StringCache.clearCache();
        return "done";
    }
}
