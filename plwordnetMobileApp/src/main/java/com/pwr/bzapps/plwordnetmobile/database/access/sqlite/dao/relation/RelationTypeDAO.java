package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.relation;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeEntity;

import java.util.Collection;

public class RelationTypeDAO {
    static final String HEADER = "SELECT * FROM " + SQLiteTablesConstNames.RELATION_TYPE_NAME;

    public Collection<RelationTypeEntity> getAll(){
        String query = HEADER;
        Collection<RelationTypeEntity> results = SQLiteConnector.getResultListForQuery(query,RelationTypeEntity.class);
        return results;
    }

    public RelationTypeEntity findById(Integer id){
        String query = HEADER
                + " WHERE id = " + id;
        RelationTypeEntity result = SQLiteConnector.getResultForQuery(query,RelationTypeEntity.class);
        return result;
    }
}
