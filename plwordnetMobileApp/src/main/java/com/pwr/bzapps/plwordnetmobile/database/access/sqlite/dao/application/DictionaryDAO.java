package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.ApplicationLocalisedStringEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.DictionaryEntity;

import java.util.Collection;
import java.util.List;

@Dao
public interface DictionaryDAO {

    @Query("SELECT * FROM dictionaries AS d LIMIT 1")
    public DictionaryEntity checkTable();

    @Query("SELECT * FROM dictionaries AS d")
    public List<DictionaryEntity> getAll();

    @Query("SELECT * FROM dictionaries AS d WHERE d.id = :id")
    public DictionaryEntity findById(Long id);
}
