package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseExampleEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseRelationEntity;

import java.util.Collection;
import java.util.List;

@Dao
public interface SenseRelationDAO {

    @Query("SELECT * FROM sense_relation AS sr LIMIT 1")
    public SenseRelationEntity checkTable();

    @Query("SELECT * FROM sense_relation AS sr")
    public List<SenseRelationEntity> getAll();

    @Query("SELECT * FROM sense_relation AS sr WHERE sr.id = :id")
    public SenseRelationEntity findById(Long id);
}
