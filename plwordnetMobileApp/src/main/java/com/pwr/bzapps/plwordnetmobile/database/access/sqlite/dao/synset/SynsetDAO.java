package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetEntity;

import java.util.Collection;

public class SynsetDAO {
    static final String HEADER = "SELECT id, split, status_id, abstract, lexicon_id FROM " + SQLiteTablesConstNames.SYNSET_NAME;

    public Collection<SynsetEntity> getAll(){
        String query = HEADER;
        Collection<SynsetEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,SynsetEntity.class);
        return results;
    }

    public SynsetEntity findById(Integer id){
        if(EntityManager.contains("Sy:"+id)){
            return (SynsetEntity) EntityManager.getEntity("Sy:"+id);
        }
        String query = HEADER
                + " WHERE id = " + id;
        SynsetEntity result = SQLiteConnector.getInstance()
                .getResultForQuery(query,SynsetEntity.class);
        return result;
    }
}
