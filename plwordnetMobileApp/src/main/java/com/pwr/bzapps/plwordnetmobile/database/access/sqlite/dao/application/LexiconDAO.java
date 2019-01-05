package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.LexiconEntity;

import java.util.Collection;

public class LexiconDAO {
    static final String HEADER = "SELECT id, name, identifier, language_name, lexicon_version FROM " + SQLiteTablesConstNames.LEXICON_NAME;

    public Collection<LexiconEntity> getAll(){
        String query = HEADER;
        Collection<LexiconEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,LexiconEntity.class);
        return results;
    }

    public LexiconEntity findById(Integer id){
        if(EntityManager.contains("Le:"+id)){
            return (LexiconEntity) EntityManager.getEntity("Le:"+id);
        }
        String query = HEADER
                + " WHERE id = " + id;
        LexiconEntity result = SQLiteConnector.getInstance()
                .getResultForQuery(query,LexiconEntity.class);
        return result;
    }
}
