package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.grammar;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.PartOfSpeechEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.WordEntity;

import java.util.Collection;
import java.util.List;

@Dao
public interface WordDAO {

    @Query("SELECT * FROM word w LIMIT 1")
    public WordEntity checkTable();

    @Query("SELECT * FROM word w")
    public List<WordEntity> getAll();

    @Query("SELECT * FROM word w WHERE w.id = :id")
    public WordEntity findById(Long id);
}
