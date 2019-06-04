package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseAttributeEntity;

import java.util.Collection;
import java.util.List;

@Dao
public interface SenseAttributeDAO {

    @Transaction
    @Query("SELECT * FROM sense_attributes AS sa LIMIT 1")
    public SenseAttributeEntity checkTable();

    @Transaction
    @Query("SELECT * FROM sense_attributes AS sa")
    public List<SenseAttributeEntity> getAll();

    @Transaction
    @Query("SELECT * FROM sense_attributes AS sa WHERE sa.sense_id = :id")
    public SenseAttributeEntity findById(Integer id);

    @Transaction
    @Query("SELECT * FROM sense_attributes AS sa WHERE sa.sense_id = :sense_id")
    public List<SenseAttributeEntity> findAllForSenseId(Long sense_id);
}
