package com.pwr.bzapps.plwordnetmobile.service.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class ConfigurationReader {
    static final private String CONF_PATH = new File("").getAbsolutePath() +"/service_configuration.cfg";

    static private Logger log = LoggerFactory.getLogger(ConfigurationReader.class);


    static private String readConfigurationFile(){
        File conf_file = new File(CONF_PATH);
        String conf = null;
        if(!conf_file.exists()){
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(CONF_PATH));
                bw.write("conf.max_batch_size=1000;");
                bw.newLine();
                bw.write("conf.language_packs={all,polish,english};");
                bw.newLine();
                bw.flush();
                bw.close();
            } catch (IOException e) {
                log.error("Exception during config file creation: ", e);
                return null;
            }
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(CONF_PATH));
            String line = null;
            line = br.readLine();
            while(line != null){
                conf+=line;
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            log.error("Configuration file not found: ", e);
            return null;
        } catch (IOException e) {
            log.error("Exception during config file reading: ", e);
            return null;
        }
        return conf;
    }

    static public int getMaxBatchSize(){
        String conf = readConfigurationFile();
        if(conf != null) {
            String line = conf.substring(conf.indexOf("conf.max_batch_size"));
            String value = line.substring(line.indexOf("=")+1, line.indexOf(";")).trim();
            return Integer.parseInt(value);
        }
        return 1000;
    }

    static public String[] readAvailableLanguagePacks(){
        String conf = readConfigurationFile();
        if(conf != null) {
            String line = conf.substring(conf.indexOf("conf.language_packs"));
            line = line.substring(0, line.indexOf(";"));
            String values = line.substring(line.indexOf("{")+1, line.indexOf("}"));
            String[] languages = values.split(",");
            return languages;
        }
        return new String[0];
    }
}
