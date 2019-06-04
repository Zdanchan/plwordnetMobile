package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.grammar;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.application.LexiconEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.EmotionalAnnotationEntity;

import java.util.Collection;
import java.util.List;

@Dao
public interface EmotionalAnnotationDAO {

    @Query("SELECT * FROM emotional_annotations ea LIMIT 1")
    public EmotionalAnnotationEntity checkTable();

    @Query("SELECT * FROM emotional_annotations ea")
    public List<EmotionalAnnotationEntity> getAll();

    @Query("SELECT * FROM emotional_annotations ea WHERE ea.id = :id")
    public EmotionalAnnotationEntity findById(Long id);

    @Query("SELECT * FROM emotional_annotations ea WHERE ea.sense_id = :sense_id")
    public List<EmotionalAnnotationEntity> findAllForSenseId(Long sense_id);
}
