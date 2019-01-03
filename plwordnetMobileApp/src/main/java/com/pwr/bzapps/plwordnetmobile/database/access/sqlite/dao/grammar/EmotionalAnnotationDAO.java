package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.grammar;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.EmotionalAnnotationEntity;

import java.util.Collection;

public class EmotionalAnnotationDAO {
    static final String HEADER = "SELECT * FROM " + SQLiteTablesConstNames.EMOTIONAL_ANNOTATION_NAME;

    public Collection<EmotionalAnnotationEntity> getAll(){
        String query = HEADER;
        Collection<EmotionalAnnotationEntity> results = SQLiteConnector.getResultListForQuery(query,EmotionalAnnotationEntity.class);
        return results;
    }

    public EmotionalAnnotationEntity findById(Integer id){
        String query = HEADER
                + " WHERE id = " + id;
        EmotionalAnnotationEntity result = SQLiteConnector.getResultForQuery(query,EmotionalAnnotationEntity.class);
        return result;
    }

    public Collection<EmotionalAnnotationEntity> findAllForSenseId(Integer sense_id){
        String query = HEADER
                + " WHERE sense_id = " + sense_id;
        Collection<EmotionalAnnotationEntity> results = SQLiteConnector.getResultListForQuery(query,EmotionalAnnotationEntity.class);
        return results;
    }
}
