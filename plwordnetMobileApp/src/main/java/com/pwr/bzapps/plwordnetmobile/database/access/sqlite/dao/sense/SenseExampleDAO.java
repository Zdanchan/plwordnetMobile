package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseExampleEntity;

import java.util.Collection;
import java.util.List;

@Dao
public interface SenseExampleDAO {

    @Query("SELECT * FROM sense_examples AS se LIMIT 1")
    public SenseExampleEntity checkTable();

    @Query("SELECT * FROM sense_examples AS se")
    public List<SenseExampleEntity> getAll();

    @Query("SELECT * FROM sense_examples AS se WHERE se.id = :id")
    public SenseExampleEntity findById(Long id);

    @Query("SELECT * FROM sense_examples AS se WHERE se.sense_attribute_id = :sense_attribute_id")
    public List<SenseExampleEntity> findAllForSenseAttribute(Long sense_attribute_id);
}
