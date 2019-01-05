package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.relation;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeEntity;

import java.util.Collection;

public class RelationTypeDAO {
    static final String HEADER = "SELECT id, priority, node_position, color, relation_argument, multilingual, " +
            "auto_reverse, reverse_relation_type_id, name_id, description_id, display_text_id, short_display_text_id, " +
            "parent_relation_type_id FROM " + SQLiteTablesConstNames.RELATION_TYPE_NAME;

    public Collection<RelationTypeEntity> getAll(){
        String query = HEADER;
        Collection<RelationTypeEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,RelationTypeEntity.class);
        return results;
    }

    public RelationTypeEntity findById(Integer id){
        if(EntityManager.contains("RT:"+id)){
            return (RelationTypeEntity) EntityManager.getEntity("RT:"+id);
        }
        String query = HEADER
                + " WHERE id = " + id;
        RelationTypeEntity result = SQLiteConnector.getInstance()
                .getResultForQuery(query,RelationTypeEntity.class);
        return result;
    }
}
