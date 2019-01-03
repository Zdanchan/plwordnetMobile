package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseRelationEntity;

import java.util.Collection;

public class SenseRelationDAO {
    static final String HEADER = "SELECT * FROM " + SQLiteTablesConstNames.SENSE_RELATION_NAME;

    public Collection<SenseRelationEntity> getAll(){
        String query = HEADER;
        Collection<SenseRelationEntity> results = SQLiteConnector.getResultListForQuery(query,SenseRelationEntity.class);
        return results;
    }

    public SenseRelationEntity findById(Integer id){
        String query = HEADER
                + " WHERE id = " + id;
        SenseRelationEntity result = SQLiteConnector.getResultForQuery(query,SenseRelationEntity.class);
        return result;
    }
}
