package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetRelationEntity;

import java.util.Collection;

public class SynsetRelationDAO {
    static final String HEADER = "SELECT * FROM " + SQLiteTablesConstNames.SYNSET_RELATION_NAME;

    public Collection<SynsetRelationEntity> getAll(){
        String query = HEADER;
        Collection<SynsetRelationEntity> results = SQLiteConnector.getResultListForQuery(query,SynsetRelationEntity.class);
        return results;
    }

    public SynsetRelationEntity findById(Integer id){
        String query = HEADER
                + " WHERE id = " + id;
        SynsetRelationEntity result = SQLiteConnector.getResultForQuery(query,SynsetRelationEntity.class);
        return result;
    }

    public Collection<SynsetRelationEntity> findChildrenByParentId(Integer parent_synset_id){
        String query = HEADER
                + " WHERE parent_synset_id = " + parent_synset_id;
        Collection<SynsetRelationEntity> results = SQLiteConnector.getResultListForQuery(query,SynsetRelationEntity.class);
        return results;
    }
    public Collection<SynsetRelationEntity> findParentsByChildId(Integer child_synset_id){
        String query = HEADER
                + " WHERE child_synset_id = " + child_synset_id;
        Collection<SynsetRelationEntity> results = SQLiteConnector.getResultListForQuery(query,SynsetRelationEntity.class);
        return results;
    }
}
