package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application;

import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.ApplicationLocalisedStringEntity;
import com.pwr.bzapps.plwordnetmobile.utils.StringUtil;

import java.util.Collection;

public class ApplicationLocalisedStringDAO {
    private static final String HEADER = "SELECT id, language, value FROM " + SQLiteTablesConstNames.APPLICATION_LOCALISED_STRING_NAME;

    public Collection<ApplicationLocalisedStringEntity> getAll(){
        String query = HEADER;
        Collection<ApplicationLocalisedStringEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,ApplicationLocalisedStringEntity.class);
        return results;
    }

    public ApplicationLocalisedStringEntity findById(Integer id){
        if(EntityManager.contains("ALS:"+id)){
            return (ApplicationLocalisedStringEntity)EntityManager.getEntity("ALS:"+id);
        }
        String query = HEADER
                + " WHERE id = " + id;
        ApplicationLocalisedStringEntity result = SQLiteConnector.getInstance()
                .getResultForQuery(query,ApplicationLocalisedStringEntity.class);
        return result;
    }

    public Collection<ApplicationLocalisedStringEntity> findByMultipleIds(Integer[] ids){
        String query = HEADER
                + " WHERE id IN (" + StringUtil.parseIntegerArrayToString(ids) + ")";
        Collection<ApplicationLocalisedStringEntity> results = SQLiteConnector.getInstance()
                .getResultListForQuery(query,ApplicationLocalisedStringEntity.class);
        return results;
    }
}
