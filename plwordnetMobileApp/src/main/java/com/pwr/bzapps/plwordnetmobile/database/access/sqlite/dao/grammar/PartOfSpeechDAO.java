package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.grammar;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.PartOfSpeechEntity;

import java.util.Collection;

public class PartOfSpeechDAO {
    static final String HEADER = "SELECT id, color, name_id FROM " + SQLiteTablesConstNames.PART_OF_SPEECH_NAME;

    public Collection<PartOfSpeechEntity> getAll(){
        String query = HEADER;
        Collection<PartOfSpeechEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,PartOfSpeechEntity.class);
        return results;
    }

    public PartOfSpeechEntity findById(Integer id){
        if(EntityManager.contains("POS:"+id)){
            return (PartOfSpeechEntity) EntityManager.getEntity("POS:"+id);
        }
        String query = HEADER
                + " WHERE id = " + id;
        PartOfSpeechEntity result = SQLiteConnector.getInstance()
                .getResultForQuery(query,PartOfSpeechEntity.class);
        return result;
    }
}
