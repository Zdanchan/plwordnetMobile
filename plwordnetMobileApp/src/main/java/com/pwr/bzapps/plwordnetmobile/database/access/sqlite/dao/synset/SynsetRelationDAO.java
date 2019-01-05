package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetRelationEntity;

import java.util.Collection;

public class SynsetRelationDAO {
    static final String HEADER = "SELECT id, parent_synset_id, child_synset_id, synset_relation_type_id FROM " + SQLiteTablesConstNames.SYNSET_RELATION_NAME;

    public Collection<SynsetRelationEntity> getAll(){
        String query = HEADER;
        Collection<SynsetRelationEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,SynsetRelationEntity.class);
        return results;
    }

    public SynsetRelationEntity findById(Integer id){
        if(EntityManager.contains("SyR:"+id)){
            return (SynsetRelationEntity) EntityManager.getEntity("SyR:"+id);
        }
        String query = HEADER
                + " WHERE id = " + id;
        SynsetRelationEntity result = SQLiteConnector.getInstance()
                .getResultForQuery(query,SynsetRelationEntity.class);
        return result;
    }

    public Collection<SynsetRelationEntity> findChildrenByParentId(Integer parent_synset_id){
        String query = HEADER
                + " WHERE parent_synset_id = " + parent_synset_id;
        Collection<SynsetRelationEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,SynsetRelationEntity.class);
        return results;
    }

    public Collection<SynsetRelationEntity> findParentsByChildId(Integer child_synset_id){
        String query = HEADER
                + " WHERE child_synset_id = " + child_synset_id;
        Collection<SynsetRelationEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,SynsetRelationEntity.class);
        return results;
    }
}
