package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.application;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.DomainEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.LexiconEntity;

import java.util.Collection;
import java.util.List;

@Dao
public interface LexiconDAO {

    @Query("SELECT * FROM lexicon AS l LIMIT 1")
    public LexiconEntity checkTable();

    @Query("SELECT * FROM lexicon AS l")
    public List<LexiconEntity> getAll();

    @Query("SELECT * FROM lexicon AS l WHERE l.id = :id")
    public LexiconEntity findById(Long id);
}
