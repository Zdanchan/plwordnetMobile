package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.ApplicationLocalisedStringEntity;
import com.pwr.bzapps.plwordnetmobile.utils.StringUtil;

import java.util.Collection;
import java.util.List;

@Dao
public interface ApplicationLocalisedStringDAO {

    @Query("SELECT * FROM application_localised_string AS als LIMIT 1")
    public ApplicationLocalisedStringEntity checkTable();

    @Query("SELECT * FROM application_localised_string AS als")
    public List<ApplicationLocalisedStringEntity> getAll();

    @Query("SELECT * FROM application_localised_string AS als WHERE als.id = :id")
    public ApplicationLocalisedStringEntity findById(Long id);

    @Query("SELECT * FROM application_localised_string AS als WHERE als.id IN (:ids)")
    public List<ApplicationLocalisedStringEntity> findByMultipleIds(Long[] ids);
}
