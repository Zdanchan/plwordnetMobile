package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;

import java.util.Collection;

public class SenseDAO {
    static final String HEADER = "SELECT * FROM " + SQLiteTablesConstNames.SENSE_NAME;

    public Collection<SenseEntity> getAll(){
        String query = HEADER;
        Collection<SenseEntity> results = SQLiteConnector.getResultListForQuery(query,SenseEntity.class);
        return results;
    }

    public SenseEntity findById(Integer id){
        String query = HEADER
                + " WHERE id = " + id;
        SenseEntity result = SQLiteConnector.getResultForQuery(query,SenseEntity.class);
        return result;
    }
}
