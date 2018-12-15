package com.pwr.bzapps.plwordnetmobile.service.controller;

import com.pwr.bzapps.plwordnetmobile.service.advisor.Advisor;
import com.pwr.bzapps.plwordnetmobile.service.cache.StringCache;
import com.pwr.bzapps.plwordnetmobile.service.component.DBHelperComponent;
import com.pwr.bzapps.plwordnetmobile.service.component.SQLiteComponent;
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
import com.pwr.bzapps.plwordnetmobile.service.database.repository.grammar.WordRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.properties.TablesRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.sense.SenseAttributeRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.sense.SenseRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.synset.SynsetAttributeRepository;
import com.pwr.bzapps.plwordnetmobile.service.database.repository.synset.SynsetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
@RequestMapping(path="/db_controller")
public class DBController {

    private Logger log = LoggerFactory.getLogger(DBController.class);
    private final String QUERY_DIRECTORY = "/downloads/queries/";
    private final String TMP_DIRECTORY = "/downloads/tmp/";
    private final String[] languages = {"polish", "english"};

    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private SQLiteComponent sqLiteComponent;

    @GetMapping(path="/print_generated_files")
    public @ResponseBody String printGeneratedFiles(){
        String content = "generated files:\n";
        File[] files = (new File(new File("").getAbsolutePath() + TMP_DIRECTORY)).listFiles();
        for (File file : files){
            content+=file.getAbsolutePath()+"\n";
        }
        return content;
    }

    @GetMapping(path="/test_SQLite_files")
    public @ResponseBody String testSQLiteFiles(){
        return sqLiteComponent.testSQLiteDB();
    }

    @GetMapping(path="/generate_SQLite_files")
    public @ResponseBody String generateSQLiteFiles(){

        String response = "";
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Config.xml");
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        if(!Advisor.isQuery_generator_processing()){
            Advisor.setQuery_generator_processing(true);
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        sqLiteComponent.dumpSQLDBContentIntoSQLiteDB(languages);
                        log.info("SQLite databases are stored and up to date");
                    }catch (Exception e){
                        log.error("Exception during SQLite databases generation: ", e);
                    } finally {
                        Advisor.setQuery_generator_processing(false);
                    }
                }
            });
            response = "SQLite generator started";
        }
        else{
            response = "SQLite generator is already running";
        }
        return response;
    }

    private Integer[] parseStringList(String[] strings){
        Integer[] integers = new Integer[strings.length];
        for(int i=0; i<integers.length; i++){
            integers[i] = Integer.parseInt(strings[i]);
        }
        return  integers;
    }

    @GetMapping(path="/clear_cache")
    private @ResponseBody String clearCache(){
        StringCache.clearCache();
        return "done";
    }
}
