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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.*;

@Component
public class SQLiteComponent {

    @Autowired
    private static DBHelperComponent helper;

    private Logger log = LoggerFactory.getLogger(SQLiteComponent.class);
    private static final String TMP_DIRECTORY = new File("").getAbsolutePath() +"/downloads/tmp/";
    private static final String SQLITE_DBS_DIRECTORY =  new File("").getAbsolutePath() + "/downloads/sqlite_dbs/";
    private static final String URL_TMP = "jdbc:sqlite:" + TMP_DIRECTORY;
    private static final String FILENAME_BASE = "plwordnet";

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
        Connection conn = connectToDatabaseFile(FILENAME_BASE + ".db");
        executeQuery(conn,getCreateTables()); //create tables

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
            executeQuery(conn,helper.generateSQLInsertForStrings(helper.findAllForEntityAndParseString(classes[i]), classes[i]));
        }
        log.info("Finished SQLite db full file generation");
    }

    private void dumpLanuagePackSQLiteDB(String language){
        log.info("Started SQLite db " + language + " file generation");
        Connection conn = connectToDatabaseFile(FILENAME_BASE +  "_" + language + ".db");
        executeQuery(conn,getCreateTables()); //create tables

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
            executeQuery(conn,helper.generateSQLInsertForStrings(helper.findAllForEntityAndParseString(classes[i]), classes[i]));
        }

        classes = new Class[]{
                SynsetAttributeEntity.class,
                SynsetExampleEntity.class,
                SynsetRelationEntity.class,
                SenseAttributeEntity.class,
                SenseExampleEntity.class,
                SenseRelationEntity.class
        };

        for (int i = 0; i < classes.length; i++) {

        }
        log.info("Finished SQLite db " + language + " file generation");
    }

    private Connection connectToDatabaseFile(String fileName) {
        if(!new File(TMP_DIRECTORY).exists()){
            new File(TMP_DIRECTORY).mkdirs();
        }

        if (!new File(URL_TMP + fileName).exists())
        {
            //TODO: create SQL db file if not exists
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

    private String getCreateTables(){
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

        return create;
    }

    public void executeQuery(Connection conn, String query) {
        try {
            Statement statement = conn.createStatement();
            statement.executeQuery(query);
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
}
