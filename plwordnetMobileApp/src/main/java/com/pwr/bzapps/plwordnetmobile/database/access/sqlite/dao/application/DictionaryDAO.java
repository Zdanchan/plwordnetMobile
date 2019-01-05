package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.DictionaryEntity;

import java.util.Collection;

public class DictionaryDAO {

    static final String HEADER = "SELECT id, dtype, tag, value, name_id, description_id FROM " + SQLiteTablesConstNames.DICTIONARY_NAME;

    public Collection<DictionaryEntity> getAll(){
        String query = HEADER;
        Collection<DictionaryEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,DictionaryEntity.class);
        return results;
    }

    public DictionaryEntity findById(Integer id){
        if(EntityManager.contains("Di:"+id)){
            return (DictionaryEntity) EntityManager.getEntity("Di:"+id);
        }
        String query = HEADER
                + " WHERE id = " + id;
        DictionaryEntity result = SQLiteConnector.getInstance()
                .getResultForQuery(query,DictionaryEntity.class);
        return result;
    }
}
