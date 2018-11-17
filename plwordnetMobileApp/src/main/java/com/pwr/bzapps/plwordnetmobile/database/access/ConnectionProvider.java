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
            // The connection URL
            String url = context.getString(R.string.spring_interface_address) + "/ApplicationLocalisedStringsController/findAll";

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();

            // Add the String message converter
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            // Make the HTTP GET request, marshaling the response to a String
            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(ResourceAccessException e){
            return "ConnectionException";
        }
    }

    public String getSensesForWord(String word){
        try {
            // The connection URL
            String url = context.getString(R.string.spring_interface_address) + "/sense/findByWord?word=" + word;

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();

            // Add the String message converter
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            // Make the HTTP GET request, marshaling the response to a String
            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(ResourceAccessException e){
            return "ConnectionException";
        }
    }

    public String getRelatedSensesForWord(String word){
        try {
            // The connection URL
            String url = context.getString(R.string.spring_interface_address) + "/sense/findRelatedSensesByWord?word=" + word;

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();

            // Add the String message converter
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            // Make the HTTP GET request, marshaling the response to a String
            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(ResourceAccessException e){
            return "ConnectionException";
        }
    }

    public String getSenseById(int id){
        try {
            // The connection URL
            String url = context.getString(R.string.spring_interface_address) + "/sense/findById?id=" + id;

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();

            // Add the String message converter
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            // Make the HTTP GET request, marshaling the response to a String
            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(ResourceAccessException e){
            return "ConnectionException";
        }
    }

    public String getSensesByIds(String ids){
        try {
            // The connection URL
            String url = context.getString(R.string.spring_interface_address) + "/sense/findMultipleByIds?ids=" + ids;
            url = url.replace("[","");
            url = url.replace("]","");

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();

            // Add the String message converter
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            // Make the HTTP GET request, marshaling the response to a String
            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(ResourceAccessException e){
            return "ConnectionException";
        }
    }

    public String getSensesBySynsetIds(String ids){
        try {
            // The connection URL
            String url = context.getString(R.string.spring_interface_address) + "/sense/findMultipleBySynsetIds?ids=" + ids;
            url = url.replace("[","");
            url = url.replace("]","");

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();

            // Add the String message converter
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            // Make the HTTP GET request, marshaling the response to a String
            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(ResourceAccessException e){
            return "ConnectionException";
        }
    }

    public String getSynonymsBySynsetId(String id){
        try {
            // The connection URL
            String url = context.getString(R.string.spring_interface_address) + "/sense/findSynonymsBySynsetId?id=" + id;

            // Create a new RestTemplate instance
            RestTemplate restTemplate = new RestTemplate();

            // Add the String message converter
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

            // Make the HTTP GET request, marshaling the response to a String
            String result = restTemplate.getForObject(url, String.class);

            return result;
        }catch(ResourceAccessException e){
            return "ConnectionException";
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
