package com.pwr.bzapps.plwordnetmobile.database.access;

import android.content.Context;

import com.pwr.bzapps.plwordnetmobile.R;

import com.pwr.bzapps.plwordnetmobile.service.rest.ServiceRestTemplate;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;

public class ConnectionProvider{

    private static ConnectionProvider instance;
    private static Context context;

    private static final int CONST_LOCALDB_CHECKER_TIMEOUT = 10000;
    private static final int CONST_CONNECTING_TIMEOUT = 12000;

    private ConnectionProvider() {

    }

    public String getAllApplicationLocalisedStrings(){
        try {
            String url = context.getString(R.string.spring_interface_address) + "/ApplicationLocalisedStringsController/findAll";

            RestTemplate restTemplate = getCustomRestTemplate(CONST_CONNECTING_TIMEOUT);

            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(Exception e){
            return "ConnectionException";
        }
    }

    public String getSensesForWord(String word){
        try {
            String url = context.getString(R.string.spring_interface_address) + "/sense/findByWord?word=" + word;

            RestTemplate restTemplate = getCustomRestTemplate(CONST_CONNECTING_TIMEOUT);

            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(Exception e){
            return "ConnectionException";
        }
    }

    public String getRelatedSensesForWord(String word){
        try {
            String url = context.getString(R.string.spring_interface_address) + "/sense/findRelatedSensesByWord?word=" + word;

            RestTemplate restTemplate = getCustomRestTemplate(CONST_CONNECTING_TIMEOUT);

            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(Exception e){
            return "ConnectionException";
        }
    }

    public String getRelatedSensesForWord(String word, String language){
        try {
            String url = context.getString(R.string.spring_interface_address)
                    + "/sense/findRelatedSensesByWordAndLanguage?word=" + word + "&language=" + language;

            RestTemplate restTemplate = getCustomRestTemplate(CONST_CONNECTING_TIMEOUT);

            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(Exception e){
            return "ConnectionException";
        }
    }

    public String getRelatedSensesForWord(String word, String language, Long part_of_speech){
        try {
            String url = context.getString(R.string.spring_interface_address)
                    + "/sense/findRelatedSensesByWordLanguageAndPartOfSpeech?word=" + word + "&language=" + language + "&part_of_speech=" + part_of_speech;

            RestTemplate restTemplate = getCustomRestTemplate(CONST_CONNECTING_TIMEOUT);

            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(Exception e){
            return "ConnectionException";
        }
    }

    public String getSenseById(Long id){
        try {
            String url = context.getString(R.string.spring_interface_address) + "/sense/findById?id=" + id;

            RestTemplate restTemplate = getCustomRestTemplate(CONST_CONNECTING_TIMEOUT);

            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(Exception e){
            return "ConnectionException";
        }
    }

    public String getSensesByIds(String ids){
        try {
            String url = context.getString(R.string.spring_interface_address) + "/sense/findMultipleByIds?ids=" + ids;
            url = url.replace("[","");
            url = url.replace("]","");

            RestTemplate restTemplate = getCustomRestTemplate(CONST_CONNECTING_TIMEOUT);

            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(Exception e){
            return "ConnectionException";
        }
    }

    public String getSensesBySynsetIds(String ids){
        try {
            if("[]".equals(ids))
                return "{\"content\":[]}";
            String url = context.getString(R.string.spring_interface_address) + "/sense/findMultipleBySynsetIds?ids=" + ids;
            url = url.replace("[","");
            url = url.replace("]","");

            RestTemplate restTemplate = getCustomRestTemplate(CONST_CONNECTING_TIMEOUT);

            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(Exception e){
            return "ConnectionException";
        }
    }

    public String getSynonymsBySynsetId(String id){
        try {
            String url = context.getString(R.string.spring_interface_address) + "/sense/findSynonymsBySynsetId?id=" + id;

            RestTemplate restTemplate = getCustomRestTemplate(CONST_CONNECTING_TIMEOUT);

            
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            
            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(Exception e){
            return "ConnectionException";
        }
    }

    public Long getSQLiteLastUpdateOnServer(String db_type){
        try {
            
            String url = context.getString(R.string.spring_interface_address) + "/db_controller/get_SQLite_last_update?db_type=" + db_type;
            
            RestTemplate restTemplate = getCustomRestTemplate(CONST_LOCALDB_CHECKER_TIMEOUT);
            
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            Long result = Long.parseLong(restTemplate.getForObject(url, String.class));

            return result;
        }catch(Exception e){
            return Long.MAX_VALUE;
        }
    }

    private static RestTemplate getCustomRestTemplate(){
        return getCustomRestTemplate(12000);
    }

    private static RestTemplate getCustomRestTemplate(int timeout){
        return new ServiceRestTemplate(timeout);
    }

    public static void setContext(Context ctx){
        context = ctx;
    }

    public static ConnectionProvider getInstance() {
        return getInstance(context);
    }

    public static ConnectionProvider getInstance(Context context) {
        if (instance == null) {
            synchronized (ConnectionProvider.class) {
                if (instance == null) {
                    instance = new ConnectionProvider();
                }
            }
        }
        setContext(context);
        return instance;
    }


}
