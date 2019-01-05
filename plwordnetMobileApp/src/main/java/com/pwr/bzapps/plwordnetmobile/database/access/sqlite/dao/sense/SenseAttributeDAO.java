package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseAttributeEntity;

import java.util.Collection;

public class SenseAttributeDAO {
    static final String HEADER = "SELECT sense_id, proper_name, error_comment, user_id, aspect_id, register_id, " +
            "link, definition, comment FROM " + SQLiteTablesConstNames.SENSE_ATTRIBUTE_NAME;

    public Collection<SenseAttributeEntity> getAll(){
        String query = HEADER;
        Collection<SenseAttributeEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,SenseAttributeEntity.class);
        return results;
    }

    public SenseAttributeEntity findById(Integer id){
        if(EntityManager.contains("SeA:"+id)){
            return (SenseAttributeEntity) EntityManager.getEntity("SeA:"+id);
        }
        String query = HEADER
                + " WHERE id = " + id;
        SenseAttributeEntity result = SQLiteConnector.getInstance()
                .getResultForQuery(query,SenseAttributeEntity.class);
        return result;
    }

    public Collection<SenseAttributeEntity> findAllForSenseId(Integer sense_id){
        String query = HEADER
                + " WHERE sense_id = " + sense_id;
        Collection<SenseAttributeEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,SenseAttributeEntity.class);
        return results;
    }
}
