package com.pwr.bzapps.plwordnetmobile.service.component;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
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
    private static final String SQLITE_DBS_DIRECTORY =  new File("").getAbsolutePath() + "/downloads/sqlite_dbs/";
    private static final String URL_TMP = "jdbc:sqlite:" + TMP_DIRECTORY;
    public static final String FILENAME_BASE = "plwordnet";

    public void dumpSQLDBContentIntoSQLiteDB(String[] languages){
        String[] files = new String[languages.length+1];
        for(int i=0; i<languages.length; i++){
            dumpLanuagePackSQLiteDB(languages[i]);
            files[i]= TMP_DIRECTORY + FILENAME_BASE + "_" + languages[i] + ".db";
        }
        dumpFullSQLiteDB();
        files[files.length-1]= TMP_DIRECTORY + FILENAME_BASE + ".db";
        migrateDBFilesFromTMP(files);
    }

    private void dumpFullSQLiteDB(){
        log.info("Started SQLite db full file generation");
        if (new File(URL_TMP + FILENAME_BASE + ".db").exists())
        {
            new File(URL_TMP + FILENAME_BASE +  ".db").delete();
        }
        Connection conn = connectToDatabaseFile(FILENAME_BASE + ".db");
        try {
            createTables(conn); //create tables

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

            for (int i = 0; i < classes.length; i++) {
                if(isTableEmpty(conn,classes[i])) {
                log.info("Inserting data for " + classes[i].getSimpleName());
                executeQuery(conn, helper.generateSQLInsertForStrings(helper.findAllForEntityAndParseString(classes[i]), classes[i]));
                }
                else{
                    log.info("Table already filled, skipping insert for " + classes[i].getSimpleName());
                }
            }
        }
        catch (Exception e){
            throw e;
        }
        finally {
            closeConnection(conn);
        }
        log.info("Finished SQLite db full file generation");
    }

    private void dumpLanuagePackSQLiteDB(String language){
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
        Connection conn = connectToDatabaseFile(FILENAME_BASE + "_" + language + ".db");
        try {
            createTables(conn); //create tables

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
                    SenseEntity.class
            };

            for (int i = 0; i < classes.length; i++) {
                if(isTableEmpty(conn,classes[i])) {
                    log.info("Inserting data for " + classes[i].getSimpleName());
                    String query = helper.generateSQLInsertForStrings(helper.findAllForEntityAndParseString(classes[i]), classes[i]);
                    //bw.append("\n\n\n\n"+ classes[i].getSimpleName() + "\n" + query);
                    //bw.flush();
                    executeQuery(conn, query);
                }
                else{
                    log.info("Table already filled, skipping insert for " + classes[i].getSimpleName());
                }
            }

            classes = new Class[]{
                    SynsetAttributeEntity.class,
                    SynsetExampleEntity.class,
                    SynsetRelationEntity.class,
                    SenseAttributeEntity.class,
                    SenseExampleEntity.class,
                    SenseRelationEntity.class
            };
            Integer[] lexicons = getLexiconsIds(lexiconRepository.getAllLexiconsForLanguage(language));
            Integer[] senses = helper.getIdsOfSensesByLanguage(lexicons);
            Integer[] sense_attributes = helper.getIdsOfSenseAttributesByLanguage(senses);
            Integer[] synsets = helper.getIdsOfSynsetsByLanguage(lexicons);
            Integer[] synset_attributes = helper.getIdsOfSynsetAttributesByLanguage(synsets);

            if(isTableEmpty(conn,classes[0])){
                log.info("Inserting data for " + classes[0].getSimpleName());
                String query = helper.generateSQLInsertForStrings(
                    helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[0], synsets), classes[0]);
                //bw.append("\n\n\n\n"+ classes[0].getSimpleName() + "\n" + query);
                //bw.flush();
                executeQuery(conn,query);
            }
            else{
                log.info("Table already filled, skipping insert for " + classes[0].getSimpleName());
            }
            if(isTableEmpty(conn,classes[1])){
                log.info("Inserting data for " + classes[1].getSimpleName());
                String query = helper.generateSQLInsertForStrings(
                        helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[1], synset_attributes), classes[1]);
                //bw.append("\n\n\n\n"+ classes[1].getSimpleName() + "\n" + query);
                //bw.flush();
                executeQuery(conn,query);
            }
            else{
                log.info("Table already filled, skipping insert for " + classes[1].getSimpleName());
            }
            if(isTableEmpty(conn,classes[2])){
                log.info("Inserting data for " + classes[2].getSimpleName());
                String query = helper.generateSQLInsertForStrings(
                        helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[2], synsets), classes[2]);
                //bw.append("\n\n\n\n"+ classes[2].getSimpleName() + "\n" + query);
                //bw.flush();
                executeQuery(conn,query);
            }
            else{
                log.info("Table already filled, skipping insert for " + classes[2].getSimpleName());
            }
            if(isTableEmpty(conn,classes[3])){
                log.info("Inserting data for " + classes[3].getSimpleName());
                String query = helper.generateSQLInsertForStrings(
                        helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[3], senses), classes[3]);
                //bw.append("\n\n\n\n"+ classes[3].getSimpleName() + "\n" + query);
                //bw.flush();
                executeQuery(conn,query);
            }
            else{
                log.info("Table already filled, skipping insert for " + classes[3].getSimpleName());
            }
            if(isTableEmpty(conn,classes[4])){
                log.info("Inserting data for " + classes[4].getSimpleName());
                String query = helper.generateSQLInsertForStrings(
                        helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[4], sense_attributes), classes[4]);
                //bw.append("\n\n\n\n"+ classes[4].getSimpleName() + "\n" + query);
                //bw.flush();
                executeQuery(conn,query);
            }
            else{
                log.info("Table already filled, skipping insert for " + classes[4].getSimpleName());
            }
            if(isTableEmpty(conn,classes[5])){
                log.info("Inserting data for " + classes[5].getSimpleName());
                String query = helper.generateSQLInsertForStrings(
                        helper.findSynsetAndSensesAllByRelatedIdsAndParseString(classes[5], senses), classes[5]);
                //bw.append("\n\n\n\n"+ classes[5].getSimpleName() + "\n" + query);
                //bw.flush();
                executeQuery(conn,query);
            }
            else{
                log.info("Table already filled, skipping insert for " + classes[5].getSimpleName());
            }
        }catch (Exception e){
            //try {
              throw e;
            //} catch (IOException e1) {
            //    e1.printStackTrace();
            //}
        }
        finally {
            closeConnection(conn);
        }
        log.info("Finished SQLite db " + language + " file generation");
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
                log.info("The driver name is " + meta.getDriverName());
                log.info("Established connection to SQLite database file: " + URL_TMP + fileName);
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

    private void createTables(Connection conn){
        log.info("Creating table: " + SQLExporter.APPLICATION_LOCALISED_STRING_NAME);
        executeQuery(conn, SQLExporter.getCreateApplicationLocalisedQuery());
        log.info("Creating table: " + SQLExporter.DICTIONARY_NAME);
        executeQuery(conn, SQLExporter.getCreateDictionaryQuery());
        log.info("Creating table: " + SQLExporter.DOMAIN_NAME);
        executeQuery(conn, SQLExporter.getCreateDomainQuery());
        log.info("Creating table: " + SQLExporter.LEXICON_NAME);
        executeQuery(conn, SQLExporter.getCreateLexiconQuery());
        log.info("Creating table: " + SQLExporter.PART_OF_SPEECH_NAME);
        executeQuery(conn, SQLExporter.getCreatePartOfSpeechQuery());
        log.info("Creating table: " + SQLExporter.WORD_NAME);
        executeQuery(conn, SQLExporter.getCreateWordQuery());
        log.info("Creating table: " + SQLExporter.RELATION_TYPE_NAME);
        executeQuery(conn, SQLExporter.getCreateRelationTypeQuery());
        log.info("Creating table: " + SQLExporter.RELATION_TYPE_ALLOWED_LEXICON_NAME);
        executeQuery(conn, SQLExporter.getCreateRelationTypeAllowedLexiconQuery());
        log.info("Creating table: " + SQLExporter.RELATION_TYPE_ALLOWED_PART_OF_SPEECH_NAME);
        executeQuery(conn, SQLExporter.getCreateRelationTypeAllowedPartOfSpeechQuery());
        log.info("Creating table: " + SQLExporter.SYNSET_NAME);
        executeQuery(conn, SQLExporter.getCreateSynsetQuery());
        log.info("Creating table: " + SQLExporter.SYNSET_ATTRIBUTE_NAME);
        executeQuery(conn, SQLExporter.getCreateSynsetAttributeQuery());
        log.info("Creating table: " + SQLExporter.SYNSET_EXAMPLE_NAME);
        executeQuery(conn, SQLExporter.getCreateSynsetExampleQuery());
        log.info("Creating table: " + SQLExporter.SYNSET_RELATION_NAME);
        executeQuery(conn, SQLExporter.getCreateSynsetRelationQuery());
        log.info("Creating table: " + SQLExporter.SENSE_NAME);
        executeQuery(conn, SQLExporter.getCreateSenseQuery());
        log.info("Creating table: " + SQLExporter.SENSE_ATTRIBUTE_NAME);
        executeQuery(conn, SQLExporter.getCreateSenseAttributeQuery());
        log.info("Creating table: " + SQLExporter.SENSE_EXAMPLE_NAME);
        executeQuery(conn, SQLExporter.getCreateSenseExampleQuery());
        log.info("Creating table: " + SQLExporter.SENSE_RELATION_NAME);
        executeQuery(conn, SQLExporter.getCreateSenseRelationQuery());
        log.info("Creating table: " + SQLExporter.EMOTIONAL_ANNOTATION_NAME);
        executeQuery(conn, SQLExporter.getCreateEmotionalAnnotationQuery());
    }

    private boolean isTableEmpty(Connection conn, Class clazz){
        String table_name = SQLExporter.getTableNameForEntity(clazz);
        String query = "SELECT COUNT(*) FROM " + table_name + ";";
        Integer count = (Integer)executeQueryForResult(conn,query);
        if(count==null)
            return true;
        return (count.intValue()==0);
    }

    public Object executeQueryForResult(Connection conn, String query) {
        Object result = null;
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            result = resultSet.getInt(1);
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return result;
    }

    public void executeQuery(Connection conn, String query) {
        try {
            Statement statement = conn.createStatement();
            statement.execute(query);
        } catch (SQLException e) {
            log.error(e.getMessage());
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
}
