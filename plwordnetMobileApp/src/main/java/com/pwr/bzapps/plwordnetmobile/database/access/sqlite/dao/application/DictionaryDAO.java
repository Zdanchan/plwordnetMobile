package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.DictionaryEntity;

import java.util.Collection;

public class DictionaryDAO {

    static final String HEADER = "SELECT * FROM " + SQLiteTablesConstNames.DICTIONARY_NAME;

    public Collection<DictionaryEntity> getAll(){
        String query = HEADER;
        Collection<DictionaryEntity> results = SQLiteConnector.getResultListForQuery(query,DictionaryEntity.class);
        return results;
    }

    public DictionaryEntity findById(Integer id){
        String query = HEADER
                + " WHERE id = " + id;
        DictionaryEntity result = SQLiteConnector.getResultForQuery(query,DictionaryEntity.class);
        return result;
    }
}
