package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.synset;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.WordEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.synset.SynsetAttributeEntity;

import java.util.Collection;
import java.util.List;

@Dao
public interface SynsetAttributeDAO {

    @Transaction
    @Query("SELECT * FROM synset_attributes AS sa LIMIT 1")
    public SynsetAttributeEntity checkTable();

    @Transaction
    @Query("SELECT * FROM synset_attributes AS sa")
    public List<SynsetAttributeEntity> getAll();

    @Transaction
    @Query("SELECT * FROM synset_attributes AS sa WHERE sa.synset_id = :id")
    public SynsetAttributeEntity findById(Long id);

    @Transaction
    @Query("SELECT * FROM synset_attributes AS sa WHERE sa.synset_id = :synset_id")
    public List<SynsetAttributeEntity> findAllForSynsetId(Long synset_id);
}
