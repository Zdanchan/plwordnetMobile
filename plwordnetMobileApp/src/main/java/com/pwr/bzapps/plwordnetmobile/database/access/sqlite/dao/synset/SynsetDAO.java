package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetAttributeEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetEntity;

import java.util.Collection;
import java.util.List;

@Dao
public interface SynsetDAO {

    @Transaction
    @Query("SELECT * FROM synset AS s LIMIT 1")
    public SynsetEntity checkTable();

    @Transaction
    @Query("SELECT * FROM synset AS s")
    public List<SynsetEntity> getAll();

    @Transaction
    @Query("SELECT * FROM synset AS s WHERE s.id = :id")
    public SynsetEntity findById(Long id);
}
