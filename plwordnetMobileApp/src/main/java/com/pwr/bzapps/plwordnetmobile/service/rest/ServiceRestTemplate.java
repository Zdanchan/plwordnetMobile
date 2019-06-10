package com.pwr.bzapps.plwordnetmobile.service.rest;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class ServiceRestTemplate extends RestTemplate{

    public ServiceRestTemplate(int connectionTimeout) {
        if (getRequestFactory() instanceof SimpleClientHttpRequestFactory) {
            ((SimpleClientHttpRequestFactory) getRequestFactory()).setConnectTimeout(connectionTimeout);
        } else if (getRequestFactory() instanceof HttpComponentsClientHttpRequestFactory) {
            ((HttpComponentsClientHttpRequestFactory) getRequestFactory()).setConnectTimeout(connectionTimeout);
        }
    }
}
