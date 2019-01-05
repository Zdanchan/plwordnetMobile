package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetExampleEntity;
import com.pwr.bzapps.plwordnetmobile.utils.StringUtil;

import java.util.Collection;

public class SynsetExampleDAO {
    static final String HEADER = "SELECT id, example, type, synset_attributes_id FROM " + SQLiteTablesConstNames.SYNSET_EXAMPLE_NAME;

    public Collection<SynsetExampleEntity> getAll(){
        String query = HEADER;
        Collection<SynsetExampleEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,SynsetExampleEntity.class);
        return results;
    }

    public SynsetExampleEntity findById(Integer id){
        if(EntityManager.contains("SynEx:"+id)){
            return (SynsetExampleEntity) EntityManager.getEntity("SynEx:"+id);
        }
        String query = HEADER
                + " WHERE id = " + id;
        SynsetExampleEntity result = SQLiteConnector.getInstance()
                .getResultForQuery(query,SynsetExampleEntity.class);
        return result;
    }

    public Collection<SynsetExampleEntity> findAllForSynsetAttribute(Integer synset_attribute_id){
        String query = HEADER
                + " WHERE synset_attributes_id = " + synset_attribute_id;
        Collection<SynsetExampleEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,SynsetExampleEntity.class);
        return results;
    }
}
