package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetAttributeEntity;

import java.util.Collection;

public class SynsetAttributeDAO {
    static final String HEADER = "SELECT * FROM " + SQLiteTablesConstNames.SYNSET_ATTRIBUTE_NAME;

    public Collection<SynsetAttributeEntity> getAll(){
        String query = HEADER;
        Collection<SynsetAttributeEntity> results = SQLiteConnector.getResultListForQuery(query,SynsetAttributeEntity.class);
        return results;
    }

    public SynsetAttributeEntity findById(Integer id){
        String query = HEADER
                + " WHERE id = " + id;
        SynsetAttributeEntity result = SQLiteConnector.getResultForQuery(query,SynsetAttributeEntity.class);
        return result;
    }

    public Collection<SynsetAttributeEntity> findAllForSynsetId(Integer synset_id){
        String query = HEADER
                + " WHERE synset_id = " + synset_id;
        Collection<SynsetAttributeEntity> results = SQLiteConnector.getResultListForQuery(query,SynsetAttributeEntity.class);
        return results;
    }
}
