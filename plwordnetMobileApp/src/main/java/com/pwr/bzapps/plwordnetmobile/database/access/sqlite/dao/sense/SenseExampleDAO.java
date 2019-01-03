package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseExampleEntity;

import java.util.Collection;

public class SenseExampleDAO {
    static final String HEADER = "SELECT * FROM " + SQLiteTablesConstNames.SENSE_EXAMPLE_NAME;

    public Collection<SenseExampleEntity> getAll(){
        String query = HEADER;
        Collection<SenseExampleEntity> results = SQLiteConnector.getResultListForQuery(query,SenseExampleEntity.class);
        return results;
    }

    public SenseExampleEntity findById(Integer id){
        String query = HEADER
                + " WHERE id = " + id;
        SenseExampleEntity result = SQLiteConnector.getResultForQuery(query,SenseExampleEntity.class);
        return result;
    }

    public Collection<SenseExampleEntity> findAllForSenseAttribute(Integer sense_attribute_id){
        String query = HEADER
                + " WHERE sense_attribute_id = " + sense_attribute_id;
        Collection<SenseExampleEntity> results = SQLiteConnector.getResultListForQuery(query,SenseExampleEntity.class);
        return results;
    }
}
