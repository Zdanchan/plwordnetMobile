package com.pwr.bzapps.plwordnetmobile.service.component;

import com.pwr.bzapps.plwordnetmobile.service.advisor.Advisor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledComponent {
    private static final Logger log = LoggerFactory.getLogger(ScheduledComponent.class);
    private final String[] languages = {"polish", "english"};

    @Autowired
    private SQLiteComponent sqLiteComponent;

    @Scheduled(cron = "0 0 0 10 * *")
    public void regenerateSQLiteDatabases() {
        if(System.currentTimeMillis() - sqLiteComponent.getFileFor("all").lastModified() < 1209600000l ){
            log.info("SQLite files were updated at least 14 days ago, skipping update");
            return;
        }
        if(!Advisor.isQuery_generator_processing()){
            ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Config.xml");
            ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
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
            log.info("Starting SQLite file databases generation");
        }
        else{
            log.info("SQLite generator is already running, skipping initialization");
        }
    }
}
