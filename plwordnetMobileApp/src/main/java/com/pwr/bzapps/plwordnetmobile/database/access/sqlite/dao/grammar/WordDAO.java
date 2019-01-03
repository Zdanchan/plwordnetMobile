package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.grammar;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.WordEntity;

import java.util.Collection;

public class WordDAO {
    static final String HEADER = "SELECT * FROM " + SQLiteTablesConstNames.WORD_NAME;

    public Collection<WordEntity> getAll(){
        String query = HEADER;
        Collection<WordEntity> results = SQLiteConnector.getResultListForQuery(query,WordEntity.class);
        return results;
    }

    public WordEntity findById(Integer id){
        String query = HEADER
                + " WHERE id = " + id;
        WordEntity result = SQLiteConnector.getResultForQuery(query,WordEntity.class);
        return result;
    }
}
