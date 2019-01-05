package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.grammar;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.WordEntity;

import java.util.Collection;

public class WordDAO {
    static final String HEADER = "SELECT id, word FROM " + SQLiteTablesConstNames.WORD_NAME;

    public Collection<WordEntity> getAll(){
        String query = HEADER;
        Collection<WordEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,WordEntity.class);
        return results;
    }

    public WordEntity findById(Integer id){
        if(EntityManager.contains("Wo:"+id)){
            return (WordEntity) EntityManager.getEntity("Wo:"+id);
        }
        String query = HEADER
                + " WHERE id = " + id;
        WordEntity result = SQLiteConnector.getInstance()
                .getResultForQuery(query,WordEntity.class);
        return result;
    }
}
