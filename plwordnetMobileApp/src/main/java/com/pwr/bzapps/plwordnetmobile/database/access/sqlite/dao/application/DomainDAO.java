package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.DictionaryEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.DomainEntity;

import java.util.Collection;
import java.util.List;

@Dao
public interface DomainDAO {

    @Query("SELECT * FROM domain AS d LIMIT 1")
    public DomainEntity checkTable();

    @Query("SELECT * FROM domain AS d")
    public List<DomainEntity> getAll();

    @Query("SELECT * FROM domain AS d WHERE d.id = :id")
    public DomainEntity findById(Long id);
}
