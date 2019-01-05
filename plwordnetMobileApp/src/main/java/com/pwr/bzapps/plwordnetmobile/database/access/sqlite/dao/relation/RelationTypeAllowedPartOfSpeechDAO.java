package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.relation;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeAllowedPartOfSpeechEntity;

import java.util.Collection;

public class RelationTypeAllowedPartOfSpeechDAO {
    static final String HEADER = "SELECT part_of_speech_id, relation_type_id FROM " + SQLiteTablesConstNames.RELATION_TYPE_ALLOWED_PART_OF_SPEECH_NAME;

    public Collection<RelationTypeAllowedPartOfSpeechEntity> getAll(){
        String query = HEADER;
        Collection<RelationTypeAllowedPartOfSpeechEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,RelationTypeAllowedPartOfSpeechEntity.class);
        return results;
    }

    public RelationTypeAllowedPartOfSpeechEntity findById(Integer relation_type_id, Integer part_of_speech_id){
        String query = HEADER
                + " WHERE relation_type_id = " + relation_type_id
                + " AND part_of_speech_id = " + part_of_speech_id;
        RelationTypeAllowedPartOfSpeechEntity result = SQLiteConnector.getInstance()
                .getResultForQuery(query,RelationTypeAllowedPartOfSpeechEntity.class);
        return result;
    }
}
