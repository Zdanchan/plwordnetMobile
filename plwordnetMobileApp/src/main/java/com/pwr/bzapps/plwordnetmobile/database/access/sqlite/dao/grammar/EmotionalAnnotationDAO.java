package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.grammar;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.EmotionalAnnotationEntity;

import java.util.Collection;

public class EmotionalAnnotationDAO {
    static final String HEADER = "SELECT id, emotions, has_emotional_characteristic, markedness," +
            "super_anotation, valuations, example1, example2, sense_id FROM " + SQLiteTablesConstNames.EMOTIONAL_ANNOTATION_NAME;

    public Collection<EmotionalAnnotationEntity> getAll(){
        String query = HEADER;
        Collection<EmotionalAnnotationEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,EmotionalAnnotationEntity.class);
        return results;
    }

    public EmotionalAnnotationEntity findById(Integer id){
        if(EntityManager.contains("EA:"+id)){
            return (EmotionalAnnotationEntity) EntityManager.getEntity("EA:"+id);
        }
        String query = HEADER
                + " WHERE id = " + id;
        EmotionalAnnotationEntity result = SQLiteConnector.getInstance()
                .getResultForQuery(query,EmotionalAnnotationEntity.class);
        return result;
    }

    public Collection<EmotionalAnnotationEntity> findAllForSenseId(Integer sense_id){
        String query = HEADER
                + " WHERE sense_id = " + sense_id;
        Collection<EmotionalAnnotationEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,EmotionalAnnotationEntity.class);
        return results;
    }
}
