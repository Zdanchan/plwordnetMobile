package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.grammar;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.EmotionalAnnotationEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.PartOfSpeechEntity;

import java.util.Collection;
import java.util.List;

@Dao
public interface PartOfSpeechDAO {

    @Query("SELECT * FROM part_of_speech pos LIMIT 1")
    public PartOfSpeechEntity checkTable();

    @Query("SELECT * FROM part_of_speech pos")
    public List<PartOfSpeechEntity> getAll();

    @Query("SELECT * FROM part_of_speech pos WHERE pos.id = :id")
    public PartOfSpeechEntity findById(Long id);
}
