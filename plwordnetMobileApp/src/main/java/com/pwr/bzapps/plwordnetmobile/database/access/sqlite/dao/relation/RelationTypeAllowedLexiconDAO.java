package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.relation;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeAllowedLexiconEntity;

import java.util.Collection;

public class RelationTypeAllowedLexiconDAO {
    static final String HEADER = "SELECT lexicon_id, relation_type_id FROM " + SQLiteTablesConstNames.RELATION_TYPE_ALLOWED_LEXICON_NAME;

    public Collection<RelationTypeAllowedLexiconEntity> getAll(){
        String query = HEADER;
        Collection<RelationTypeAllowedLexiconEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,RelationTypeAllowedLexiconEntity.class);
        return results;
    }

    public RelationTypeAllowedLexiconEntity findById(Integer relation_type_id, Integer lexicon_id){
        String query = HEADER
                + " WHERE relation_type_id = " + relation_type_id
                + " AND lexicon_id = " + lexicon_id;
        RelationTypeAllowedLexiconEntity result = SQLiteConnector.getInstance()
                .getResultForQuery(query,RelationTypeAllowedLexiconEntity.class);
        return result;
    }
}
