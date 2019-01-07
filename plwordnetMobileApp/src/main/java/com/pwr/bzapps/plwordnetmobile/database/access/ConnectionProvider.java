package com.pwr.bzapps.plwordnetmobile.database.access;

import android.content.Context;

import com.pwr.bzapps.plwordnetmobile.R;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;

public class ConnectionProvider{

    private static ConnectionProvider instance;
    private static Context context;


    private ConnectionProvider() {

    }

    public String getAllApplicationLocalisedStrings(){
        try {
            String url = context.getString(R.string.spring_interface_address) + "/ApplicationLocalisedStringsController/findAll";

            RestTemplate restTemplate = new RestTemplate();

            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(ResourceAccessException e){
            return "ConnectionException";
        }
    }

    public String getSensesForWord(String word){
        try {
            String url = context.getString(R.string.spring_interface_address) + "/sense/findByWord?word=" + word;

            RestTemplate restTemplate = new RestTemplate();

            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(ResourceAccessException e){
            return "ConnectionException";
        }
    }

    public String getRelatedSensesForWord(String word){
        try {
            String url = context.getString(R.string.spring_interface_address) + "/sense/findRelatedSensesByWord?word=" + word;

            RestTemplate restTemplate = new RestTemplate();

            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(ResourceAccessException e){
            return "ConnectionException";
        }
    }

    public String getRelatedSensesForWord(String word, String language){
        try {
            String url = context.getString(R.string.spring_interface_address)
                    + "/sense/findRelatedSensesByWordAndLanguage?word=" + word + "&language=" + language;

            RestTemplate restTemplate = new RestTemplate();

            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(ResourceAccessException e){
            return "ConnectionException";
        }
    }

    public String getSenseById(int id){
        try {
            String url = context.getString(R.string.spring_interface_address) + "/sense/findById?id=" + id;

            RestTemplate restTemplate = new RestTemplate();

            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(ResourceAccessException e){
            return "ConnectionException";
        }
    }

    public String getSensesByIds(String ids){
        try {
            String url = context.getString(R.string.spring_interface_address) + "/sense/findMultipleByIds?ids=" + ids;
            url = url.replace("[","");
            url = url.replace("]","");

            RestTemplate restTemplate = new RestTemplate();

            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(ResourceAccessException e){
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

            RestTemplate restTemplate = new RestTemplate();

            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(ResourceAccessException e){
            return "ConnectionException";
        }
    }

    public String getSynonymsBySynsetId(String id){
        try {
            String url = context.getString(R.string.spring_interface_address) + "/sense/findSynonymsBySynsetId?id=" + id;

            RestTemplate restTemplate = new RestTemplate();

            
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            
            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(ResourceAccessException e){
            return "ConnectionException";
        }
    }

    public Long getSQLiteLastUpdateOnServer(String db_type){
        try {
            
            String url = context.getString(R.string.spring_interface_address) + "/db_controller/get_SQLite_last_update?db_type=" + db_type;
            
            RestTemplate restTemplate = new RestTemplate();
            
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            Long result = Long.parseLong(restTemplate.getForObject(url, String.class));

            return result;
        }catch(ResourceAccessException e){
            return Long.MAX_VALUE;
        }
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
