package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.grammar;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.PartOfSpeechEntity;

import java.util.Collection;

public class PartOfSpeechDAO {
    static final String HEADER = "SELECT * FROM " + SQLiteTablesConstNames.PART_OF_SPEECH_NAME;

    public Collection<PartOfSpeechEntity> getAll(){
        String query = HEADER;
        Collection<PartOfSpeechEntity> results = SQLiteConnector.getResultListForQuery(query,PartOfSpeechEntity.class);
        return results;
    }

    public PartOfSpeechEntity findById(Integer id){
        String query = HEADER
                + " WHERE id = " + id;
        PartOfSpeechEntity result = SQLiteConnector.getResultForQuery(query,PartOfSpeechEntity.class);
        return result;
    }
}
