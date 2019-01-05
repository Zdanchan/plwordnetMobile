package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.DomainEntity;

import java.util.Collection;

public class DomainDAO {
    static final String HEADER = "SELECT id, name_id, description_id FROM " + SQLiteTablesConstNames.DOMAIN_NAME;

    public Collection<DomainEntity> getAll(){
        String query = HEADER;
        Collection<DomainEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,DomainEntity.class);
        return results;
    }

    public DomainEntity findById(Integer id){
        if(EntityManager.contains("Do:"+id)){
            return (DomainEntity) EntityManager.getEntity("Do:"+id);
        }
        String query = HEADER
                + " WHERE id = " + id;
        DomainEntity result = SQLiteConnector.getInstance()
                .getResultForQuery(query,DomainEntity.class);
        return result;
    }
}
