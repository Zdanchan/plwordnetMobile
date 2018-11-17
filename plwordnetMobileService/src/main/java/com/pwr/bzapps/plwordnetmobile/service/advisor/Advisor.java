package com.pwr.bzapps.plwordnetmobile.service.advisor;

public class Advisor {
    static private boolean query_generator_processing = false;

    static synchronized public boolean isQuery_generator_processing() {
        return query_generator_processing;
    }

    static synchronized public void setQuery_generator_processing(boolean query_generator_processing_val) {
        query_generator_processing = query_generator_processing_val;
    }
}
