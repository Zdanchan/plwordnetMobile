package com.pwr.bzapps.plwordnetmobile.service.component;

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
import com.pwr.bzapps.plwordnetmobile.service.database.repository.application.LexiconRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
public class SQLiteComponent {

    @Autowired
    private DBHelperComponent helper;

    @Autowired
    private LexiconRepository lexiconRepository;

    private Logger log = LoggerFactory.getLogger(SQLiteComponent.class);
    private static final String TMP_DIRECTORY = new File("").getAbsolutePath() +"/downloads/tmp/";
    public static final String SQLITE_DBS_DIRECTORY =  new File("").getAbsolutePath() + "/downloads/sqlite_dbs/";
    private static final String URL_TMP = "jdbc:sqlite:" + TMP_DIRECTORY;
    public static final String FILENAME_BASE = "plwordnet";

    public void dumpSQLDBContentIntoSQLiteDB(String[] languages){
        String[] files = new String[languages.length+1];
        for(int i=0; i<languages.length; i++){
            dumpLanuagePackSQLiteDB(languages[i]);
            files[i]= FILENAME_BASE + "_" + languages[i] + ".db";
        }
        dumpFullSQLiteDB();
        files[files.length-1]= FILENAME_BASE + ".db";
        migrateDBFilesFromTMP(files);
    }

    public void dumpSQLDBContentIntoSQLiteDBInBatches(String[] languages, int batch_size){
        String[] files = new String[languages.length+1];
        for(int i=0; i<languages.length; i++){
            dumpLanuagePackSQLiteDB(languages[i], batch_size);
            files[i]= FILENAME_BASE + "_" + languages[i] + ".db";
        }
        dumpFullSQLiteDB(batch_size);
        files[files.length-1]= FILENAME_BASE + ".db";
        migrateDBFilesFromTMP(files);
    }

    private void dumpFullSQLiteDB(){
        dumpFullSQLiteDB(-1);
    }

    private void dumpFullSQLiteDB(int batch_size){
        log.info("Started SQLite db full file generation");
        if (new File(URL_TMP + FILENAME_BASE + ".db").exists())
        {
            new File(URL_TMP + FILENAME_BASE +  ".db").delete();
        }
        String db_path = FILENAME_BASE + ".db";
        try {
            createTables(db_path); //create tables

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
                    SenseRelationEntity.class,
                    EmotionalAnnotationEntity.class
            };

            for (int i = 0; i < classes.length; i++) {
                if(isTableEmpty(db_path,classes[i])) {
                    log.info("Inserting data for " + classes[i].getSimpleName());
                    int max_index = helper.getMaxIndexForEntity(classes[i]);
                    if(batch_size == -1 || max_index==Integer.MAX_VALUE)
                        executeQuery(db_path, helper.generateSQLInsertForStrings(helper.findAllForEntityAndParseString(classes[i]), classes[i]));
                    else {
                        for(int ind=0; ind<max_index; ind+=batch_size) {
                            executeQuery(db_path, helper.generateSQLInsertForStrings(helper.findAllForEntityAndParseString(classes[i], ind, ind+batch_size), classes[i]));
                        }
                    }
                }
                else{
                    log.info("Table already filled, skipping insert for " + classes[i].getSimpleName());
                }
            }
        }
        catch (Exception e){
            throw e;
        }
        log.info("Finished SQLite db full file generation");
    }

    private void dumpLanuagePackSQLiteDB(String language){
        dumpLanuagePackSQLiteDB(language,-1);
    }

    private void dumpLanuagePackSQLiteDB(String language, int batch_size){
        log.info("Started SQLite db " + language + " file generation");
        if (new File(URL_TMP + FILENAME_BASE + "_" + language + ".db").exists()) {
            new File(URL_TMP + FILENAME_BASE + "_" + language + ".db").delete();
        }
        //OutputStreamWriter bw = null;
        //try {
        //    File log_file = new File(TMP_DIRECTORY + FILENAME_BASE + "_" + language + "log");
        //    if(!log_file.exists()){
        //        log_file.createNewFile();
        //    }
        //    bw = new OutputStreamWriter (new FileOutputStream(log_file), StandardCharsets.UTF_8);
        //} catch (IOException e) {
        //}
        String db_path = FILENAME_BASE + "_" + language + ".db";
        try {
            createTables(db_path); //create tables

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

            for (int i = 0; i < classes.length; i++) {
                if(isTableEmpty(db_path,classes[i])) {
                    log.info("Inserting data for " + classes[i].getSimpleName());
                    int max_index = helper.getMaxIndexForEntity(classes[i]);
                    if(batch_size == -1 || max_index==Integer.MAX_VALUE)
                        executeQuery(db_path, helper.generateSQLInsertForStrings(helper.findAllForEntityAndParseString(classes[i]), classes[i]));
                    else {
                        for(int ind=0; ind<max_index; ind+=batch_size) {
                            executeQuery(db_path, helper.generateSQLInsertForStrings(helper.findAllForEntityAndParseString(classes[i], ind, ind+batch_size), classes[i]));
                        }
                    }
                }
                else{
                    log.info("Table already filled, skipping insert for " + classes[i].getSimpleName());
                }
            }

            classes = new Class[]{
                    SynsetEntity.class,
                    SynsetAttributeEntity.class,
                    SynsetExampleEntity.class,
                    SynsetRelationEntity.class,
                    SenseEntity.class,
                    SenseAttributeEntity.class,
                    SenseExampleEntity.class,
                    SenseRelationEntity.class,
                    EmotionalAnnotationEntity.class
            };

            Integer[] lexicons = getLexiconsIds(lexiconRepository.getAllLexiconsForLanguage(language));

            if(isTableEmpty(db_path,classes[0])){
                log.info("Inserting data for " + classes[0].getSimpleName());
                if(batch_size==-1)
                    executeQuery(db_path,helper.generateSQLInsertForStrings(helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[0], lexicons), classes[0]));
                else{
                    int max_index = helper.getMaxIndexForEntity(classes[0]);
                    for(int ind=0; ind<=max_index; ind+=batch_size) {
                        executeQuery(db_path,helper.generateSQLInsertForStrings(helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[0],
                                lexicons, ind, ind+batch_size), classes[0]));
                    }
                }
            }
            else{
                log.info("Table already filled, skipping insert for " + classes[0].getSimpleName());
            }
            if(isTableEmpty(db_path,classes[1])){
                log.info("Inserting data for " + classes[1].getSimpleName());
                Integer[] synsets = helper.getIdsOfSynsetsByLanguage(lexicons);
                if(batch_size==-1)
                    executeQuery(db_path,helper.generateSQLInsertForStrings(helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[1], synsets), classes[1]));
                else{
                    Arrays.sort(synsets);
                    int max_index = helper.getMaxIndexForEntity(classes[1]);
                    for(int ind=0; ind<=max_index; ind+=batch_size) {
                        Integer[] batch_synsets = getIdsInRange(synsets,ind, ind+batch_size);
                        if(batch_synsets.length>0)
                            executeQuery(db_path,helper.generateSQLInsertForStrings(helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[1],
                                    batch_synsets, ind, ind+batch_size), classes[1]));
                    }
                }
            }
            else{
                log.info("Table already filled, skipping insert for " + classes[1].getSimpleName());
            }
            if(isTableEmpty(db_path,classes[2])){
                log.info("Inserting data for " + classes[2].getSimpleName());
                Integer[] synset_attributes = helper.getIdsOfSynsetsByLanguage(lexicons);
                if(batch_size==-1)
                    executeQuery(db_path,helper.generateSQLInsertForStrings(helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[2], synset_attributes), classes[2]));
                else{
                    int max_index = helper.getMaxIndexForEntity(classes[2]);
                    for(int ind=0; ind<=max_index; ind+=batch_size) {
                        Integer[] batch_synset_attributes = getIdsInRange(synset_attributes,ind, ind+batch_size);
                        if(batch_synset_attributes.length>0)
                            executeQuery(db_path,helper.generateSQLInsertForStrings(helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[2],
                                    batch_synset_attributes, ind, ind+batch_size), classes[2]));
                    }
                }
            }
            else{
                log.info("Table already filled, skipping insert for " + classes[2].getSimpleName());
            }
            if(isTableEmpty(db_path,classes[3])){
                log.info("Inserting data for " + classes[3].getSimpleName());
                Integer[] synsets = helper.getIdsOfSynsetsByLanguage(lexicons);
                if(batch_size==-1)
                    executeQuery(db_path,helper.generateSQLInsertForStrings(helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[3], synsets), classes[3]));
                else{
                    int max_index = helper.getMaxIndexForEntity(SenseEntity.class);
                    for(int ind=0; ind<=max_index; ind+=batch_size) {
                        executeQuery(db_path,helper.generateSQLInsertForStrings(helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[3],
                                synsets, ind, ind+batch_size), classes[3]));
                    }
                }
            }
            else{
                log.info("Table already filled, skipping insert for " + classes[3].getSimpleName());
            }
            if(isTableEmpty(db_path,classes[4])){
                log.info("Inserting data for " + classes[4].getSimpleName());
                if(batch_size==-1)
                    executeQuery(db_path,helper.generateSQLInsertForStrings(helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[4], lexicons), classes[4]));
                else{
                    int max_index = helper.getMaxIndexForEntity(classes[4]);
                    for(int ind=0; ind<=max_index; ind+=batch_size) {
                        executeQuery(db_path,helper.generateSQLInsertForStrings(helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[4],
                                lexicons, ind, ind+batch_size), classes[4]));
                    }
                }
            }
            else{
                log.info("Table already filled, skipping insert for " + classes[4].getSimpleName());
            }
            if(isTableEmpty(db_path,classes[5])){
                log.info("Inserting data for " + classes[5].getSimpleName());
                Integer[] senses = helper.getIdsOfSensesByLanguage(lexicons);
                if(batch_size==-1)
                    executeQuery(db_path,helper.generateSQLInsertForStrings(helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[5], senses), classes[3]));
                else{
                    Arrays.sort(senses);
                    int max_index = helper.getMaxIndexForEntity(classes[5]);
                    for(int ind=0; ind<=max_index; ind+=batch_size) {
                        Integer[] batch_senses = getIdsInRange(senses,ind, ind+batch_size);
                        if(batch_senses.length>0)
                            executeQuery(db_path,helper.generateSQLInsertForStrings(helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[5],
                                    batch_senses, ind, ind+batch_size), classes[5]));
                    }
                }
            }
            else{
                log.info("Table already filled, skipping insert for " + classes[3].getSimpleName());
            }
            if(isTableEmpty(db_path,classes[6])){
                log.info("Inserting data for " + classes[6].getSimpleName());
                Integer[] sense_attributes = helper.getIdsOfSensesByLanguage(lexicons);
                if(batch_size==-1)
                    executeQuery(db_path,helper.generateSQLInsertForStrings(helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[6], sense_attributes), classes[6]));
                else{
                    Arrays.sort(sense_attributes);
                    int max_index = helper.getMaxIndexForEntity(classes[6]);
                    for(int ind=0; ind<=max_index; ind+=batch_size) {
                        Integer[] batch_sense_attributes = getIdsInRange(sense_attributes,ind, ind+batch_size);
                        if(batch_sense_attributes.length>0)
                            executeQuery(db_path,helper.generateSQLInsertForStrings(helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[6],
                                    batch_sense_attributes, ind, ind+batch_size), classes[6]));
                    }
                }
            }
            else{
                log.info("Table already filled, skipping insert for " + classes[6].getSimpleName());
            }
            if(isTableEmpty(db_path,classes[7])){
                log.info("Inserting data for " + classes[7].getSimpleName());
                Integer[] senses = helper.getIdsOfSensesByLanguage(lexicons);
                if(batch_size==-1)
                    executeQuery(db_path,helper.generateSQLInsertForStrings(helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[7], senses), classes[7]));
                else{
                    int max_index = helper.getMaxIndexForEntity(SynsetEntity.class);
                    for(int ind=0; ind<=max_index; ind+=batch_size) {
                        executeQuery(db_path,helper.generateSQLInsertForStrings(helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[7],
                                senses, ind, ind+batch_size), classes[7]));
                    }
                }
            }
            else{
                log.info("Table already filled, skipping insert for " + classes[7].getSimpleName());
            }
            if(isTableEmpty(db_path,classes[8])){
                log.info("Inserting data for " + classes[8].getSimpleName());
                Integer[] senses = helper.getIdsOfSensesByLanguage(lexicons);
                if(batch_size==-1)
                    executeQuery(db_path,helper.generateSQLInsertForStrings(helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[8], senses), classes[8]));
                else{
                    Arrays.sort(senses);
                    int max_index = helper.getMaxIndexForEntity(SenseEntity.class); //to fast things up ranges are calculated over sense_id
                    for(int ind=0; ind<=max_index; ind+=batch_size) {
                        Integer[] batch_senses = getIdsInRange(senses,ind, ind+batch_size);
                        if(batch_senses.length>0)
                            executeQuery(db_path,helper.generateSQLInsertForStrings(helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[8],
                                    batch_senses, ind, ind+batch_size), classes[8]));
                    }
                }
            }
            else{
                log.info("Table already filled, skipping insert for " + classes[8].getSimpleName());
            }
        }catch (Exception e){
            //try {
              throw e;
            //} catch (IOException e1) {
            //    e1.printStackTrace();
            //}
        }
        log.info("Finished SQLite db " + language + " file generation");
    }

    private Integer[] getIdsInRange(Integer[] array, int begin, int end){
        List<Integer> range = new LinkedList<Integer>();
        int iterator = 0;
        int curr_val = array[0].intValue();
        while(curr_val<end && iterator<array.length){
            curr_val = array[iterator].intValue();
            if(curr_val>=begin){
                range.add(array[iterator]);
            }
            iterator++;
        }
        return range.toArray(new Integer[0]);
    }

    private Connection connectToDatabaseFile(String fileName) {
        if(!new File(TMP_DIRECTORY).exists()){
            new File(TMP_DIRECTORY).mkdirs();
        }

        Connection conn;
        try {
            conn = DriverManager.getConnection(URL_TMP + fileName);
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                log.debug("The driver name is " + meta.getDriverName());
                log.debug("Established connection to SQLite database file: " + URL_TMP + fileName);
            }
            return conn;

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private void closeConnection(Connection conn){
        try {
            conn.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    private void commitTransaction(Connection conn){
        try {
            conn.commit();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    private void createTables(String db_path){
        log.info("Creating table: " + SQLExporter.APPLICATION_LOCALISED_STRING_NAME);
        executeQuery(db_path, SQLExporter.getCreateApplicationLocalisedQuery());
        log.info("Creating table: " + SQLExporter.DICTIONARY_NAME);
        executeQuery(db_path, SQLExporter.getCreateDictionaryQuery());
        log.info("Creating table: " + SQLExporter.DOMAIN_NAME);
        executeQuery(db_path, SQLExporter.getCreateDomainQuery());
        log.info("Creating table: " + SQLExporter.LEXICON_NAME);
        executeQuery(db_path, SQLExporter.getCreateLexiconQuery());
        log.info("Creating table: " + SQLExporter.PART_OF_SPEECH_NAME);
        executeQuery(db_path, SQLExporter.getCreatePartOfSpeechQuery());
        log.info("Creating table: " + SQLExporter.WORD_NAME);
        executeQuery(db_path, SQLExporter.getCreateWordQuery());
        log.info("Creating table: " + SQLExporter.RELATION_TYPE_NAME);
        executeQuery(db_path, SQLExporter.getCreateRelationTypeQuery());
        log.info("Creating table: " + SQLExporter.RELATION_TYPE_ALLOWED_LEXICON_NAME);
        executeQuery(db_path, SQLExporter.getCreateRelationTypeAllowedLexiconQuery());
        log.info("Creating table: " + SQLExporter.RELATION_TYPE_ALLOWED_PART_OF_SPEECH_NAME);
        executeQuery(db_path, SQLExporter.getCreateRelationTypeAllowedPartOfSpeechQuery());
        log.info("Creating table: " + SQLExporter.SYNSET_NAME);
        executeQuery(db_path, SQLExporter.getCreateSynsetQuery());
        log.info("Creating table: " + SQLExporter.SYNSET_ATTRIBUTE_NAME);
        executeQuery(db_path, SQLExporter.getCreateSynsetAttributeQuery());
        log.info("Creating table: " + SQLExporter.SYNSET_EXAMPLE_NAME);
        executeQuery(db_path, SQLExporter.getCreateSynsetExampleQuery());
        log.info("Creating table: " + SQLExporter.SYNSET_RELATION_NAME);
        executeQuery(db_path, SQLExporter.getCreateSynsetRelationQuery());
        log.info("Creating table: " + SQLExporter.SENSE_NAME);
        executeQuery(db_path, SQLExporter.getCreateSenseQuery());
        log.info("Creating table: " + SQLExporter.SENSE_ATTRIBUTE_NAME);
        executeQuery(db_path, SQLExporter.getCreateSenseAttributeQuery());
        log.info("Creating table: " + SQLExporter.SENSE_EXAMPLE_NAME);
        executeQuery(db_path, SQLExporter.getCreateSenseExampleQuery());
        log.info("Creating table: " + SQLExporter.SENSE_RELATION_NAME);
        executeQuery(db_path, SQLExporter.getCreateSenseRelationQuery());
        log.info("Creating table: " + SQLExporter.EMOTIONAL_ANNOTATION_NAME);
        executeQuery(db_path, SQLExporter.getCreateEmotionalAnnotationQuery());
    }

    private boolean isTableEmpty(String db_path, Class clazz){
        String table_name = SQLExporter.getTableNameForEntity(clazz);
        String query = "SELECT COUNT(*) FROM " + table_name + ";";
        Integer count = (Integer)executeQueryForResult(db_path,query);
        if(count==null)
            return true;
        return (count.intValue()==0);
    }

    public Object executeQueryForResult(String db_path, String query) {
        Connection conn = connectToDatabaseFile(db_path);
        Object result = null;
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            result = resultSet.getInt(1);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        closeConnection(conn);
        return result;
    }

    public void executeQuery(String db_path, String query) {
        if("".equals(query))
            return;
        Connection conn = connectToDatabaseFile(db_path);
        try {
            Statement statement = conn.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        closeConnection(conn);
    }

    public void removeTMPfiles(){
        String[] files = {
                FILENAME_BASE + ".db",
                FILENAME_BASE + "_polish.db",
                FILENAME_BASE + "_english.db"
        };
        for(String fileName : files){
            File file =  new File(TMP_DIRECTORY + fileName);
            if(file.exists()){
                file.delete();
            }
        }
    }

    private void migrateDBFilesFromTMP(String[] fileNames){
        if(!new File(TMP_DIRECTORY).exists()){
            new File(TMP_DIRECTORY).mkdirs();
        }
        if(!new File(SQLITE_DBS_DIRECTORY).exists()){
            new File(SQLITE_DBS_DIRECTORY).mkdirs();
        }

        for(String fileName : fileNames){
            File old_file =  new File(SQLITE_DBS_DIRECTORY + fileName);
            File new_file =  new File(TMP_DIRECTORY + fileName);
            if(old_file.exists() && new_file.exists()){
                old_file.delete();
            }
            if(new_file.exists()){
                new_file.renameTo(new File(SQLITE_DBS_DIRECTORY + fileName));
            }
        }
    }

    private Integer[] getLexiconsIds(List<LexiconEntity> lexicons){
        LinkedList<Integer> ids = new LinkedList<Integer>();
        for (LexiconEntity lexicon : lexicons){
            ids.add(lexicon.getId());
        }
        return ids.toArray(new Integer[0]);
    }

    public String testSQLiteDB(){
        Connection conn = connectToDatabaseFile(FILENAME_BASE + ".db");
        try {
            Statement statement = conn.createStatement();
            closeConnection(conn);
            return statement.executeQuery("SELECT * FROM application_localised_string WHERE id=2").toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(conn);
        }
        return "ERROR";
    }

    public File getFileFor(String db_type){
        String file = "err";

        if(db_type.equals("all")){
            file = SQLiteComponent.FILENAME_BASE+".db";
        }
        else {
            file = SQLiteComponent.FILENAME_BASE+"_"+db_type+".db";
        }

        return new File(SQLiteComponent.SQLITE_DBS_DIRECTORY + file);
    }
}
