package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.LexiconEntity;

import java.util.Collection;

public class LexiconDAO {
    static final String HEADER = "SELECT * FROM " + SQLiteTablesConstNames.LEXICON_NAME;

    public Collection<LexiconEntity> getAll(){
        String query = HEADER;
        Collection<LexiconEntity> results = SQLiteConnector.getResultListForQuery(query,LexiconEntity.class);
        return results;
    }

    public LexiconEntity findById(Integer id){
        String query = HEADER
                + " WHERE id = " + id;
        LexiconEntity result = SQLiteConnector.getResultForQuery(query,LexiconEntity.class);
        return result;
    }
}
