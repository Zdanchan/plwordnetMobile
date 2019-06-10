package com.pwr.bzapps.plwordnetmobile.service.component;

import com.pwr.bzapps.plwordnetmobile.service.configuration.ConfigurationReader;
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
            if("all".equals(languages[i])){
                dumpFullSQLiteDB();
                files[files.length-1]= FILENAME_BASE + ".db";
            }
            else {
                dumpLanuagePackSQLiteDB(languages[i]);
                files[i] = FILENAME_BASE + "_" + languages[i] + ".db";
            }
        }

        migrateDBFilesFromTMP(files);
    }

    public void dumpSQLDBContentIntoSQLiteDBInBatches(String[] languages, int batch_size){
        String[] files = new String[languages.length+1];
        for(int i=0; i<languages.length; i++){
            if("all".equals(languages[i])){
                dumpFullSQLiteDB(batch_size);
                files[files.length-1]= FILENAME_BASE + ".db";
            }
            else{
                dumpLanuagePackSQLiteDB(languages[i], batch_size);
                files[i]= FILENAME_BASE + "_" + languages[i] + ".db";
            }
        }

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
                    Long max_index = helper.getMaxIndexForEntity(classes[i]);
                    if(batch_size == -1 || max_index==Long.MAX_VALUE)
                        executeQuery(db_path, helper.generateSQLInsertForStrings(helper.findAllForEntityAndParseString(classes[i]), classes[i]));
                    else {
                        for(long ind=0; ind<max_index; ind+=batch_size) {
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
                    Long max_index = helper.getMaxIndexForEntity(classes[i]);
                    if(batch_size == -1 || max_index==Long.MAX_VALUE)
                        executeQuery(db_path, helper.generateSQLInsertForStrings(helper.findAllForEntityAndParseString(classes[i]), classes[i]));
                    else {
                        for(long ind=0; ind<max_index; ind+=batch_size) {
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

            Long[] lexicons = getLexiconsIds(lexiconRepository.getAllLexiconsForLanguage(language));

            for(int i=0; i<classes.length; i++){
                if(isTableEmpty(db_path,classes[i])){
                    log.info("Inserting data for " + classes[i].getSimpleName());
                    if(lexicons.length>0) {
                        if (batch_size == -1)
                            executeQuery(db_path, helper.generateSQLInsertForStrings(helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[i], lexicons), classes[i]));
                        else {
                            Long max_index = helper.getMaxIndexForEntity(classes[i]);
                            for (long ind = 0; ind <= max_index; ind += batch_size) {
                                executeQuery(db_path, helper.generateSQLInsertForStrings(helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[i],
                                        lexicons, ind, ind + batch_size), classes[i]));
                            }
                        }
                    }
                }
                else{
                    log.info("Table already filled, skipping insert for " + classes[i].getSimpleName());
                }
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
        String[] files = ConfigurationReader.readAvailableLanguagePacks();
        for(int i=0; i<files.length; i++){
            if("all".equals(files[i]))
                files[i] = FILENAME_BASE + ".db";
            else
                files[i] = FILENAME_BASE + "_" + files[i] + ".db";
        }
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

    private Long[] getLexiconsIds(List<LexiconEntity> lexicons){
        LinkedList<Long> ids = new LinkedList<Long>();
        for (LexiconEntity lexicon : lexicons){
            ids.add(lexicon.getId());
        }
        return ids.toArray(new Long[0]);
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
