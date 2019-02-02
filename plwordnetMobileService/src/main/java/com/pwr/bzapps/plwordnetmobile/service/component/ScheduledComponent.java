package com.pwr.bzapps.plwordnetmobile.service.component;

import com.pwr.bzapps.plwordnetmobile.service.advisor.Advisor;
import com.pwr.bzapps.plwordnetmobile.service.configuration.ConfigurationReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ScheduledComponent {
    private static final Logger log = LoggerFactory.getLogger(ScheduledComponent.class);
    private final String[] languages = {"polish", "english"};

    @Autowired
    private SQLiteComponent sqLiteComponent;

    @Scheduled(cron = "0 0 0 * * *")
    public void regenerateSQLiteDatabases() {
        String[] availablePacks = ConfigurationReader.readAvailableLanguagePacks();
        ArrayList<String> tmp = new ArrayList<String>();
        for(int i=0; i<availablePacks.length; i++){
            if(System.currentTimeMillis() - sqLiteComponent.getFileFor(availablePacks[i]).lastModified() < 2419200000l ){
                log.info("SQLite file plwordnet_" + availablePacks[i] +" was updated at least 28 days ago, skipping update.");
            }
            else{
                tmp.add(availablePacks[i]);
            }
        }
        final String[] finalList = tmp.toArray(new String[0]);
        if(!Advisor.isQuery_generator_processing() && finalList.length>0){
            sqLiteComponent.removeTMPfiles();
            ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Config.xml");
            ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
            Advisor.setQuery_generator_processing(true);
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        sqLiteComponent.dumpSQLDBContentIntoSQLiteDBInBatches(finalList,
                                ConfigurationReader.getMaxBatchSize());
                        log.info("SQLite databases are stored and up to date");
                    }catch (Exception e){
                        log.error("Exception during SQLite databases generation: ", e);
                    } finally {
                        Advisor.setQuery_generator_processing(false);
                    }
                }
            });
            log.info("Starting SQLite file databases generation");
        }
        else{
            log.info("SQLite generator is already running, skipping initialization");
        }
    }
}
