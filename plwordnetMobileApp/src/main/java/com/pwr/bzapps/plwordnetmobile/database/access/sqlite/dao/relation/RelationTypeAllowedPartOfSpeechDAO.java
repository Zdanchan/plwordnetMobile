package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.relation;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.WordEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeAllowedPartOfSpeechEntity;

import java.util.Collection;
import java.util.List;

@Dao
public interface RelationTypeAllowedPartOfSpeechDAO {

    @Query("SELECT * FROM relation_type_allowed_parts_of_speech AS rtapos LIMIT 1")
    public RelationTypeAllowedPartOfSpeechEntity checkTable();

    @Query("SELECT * FROM relation_type_allowed_parts_of_speech AS rtapos")
    public List<RelationTypeAllowedPartOfSpeechEntity> getAll();

    @Query("SELECT * FROM relation_type_allowed_parts_of_speech AS rtapos " +
            "WHERE rtapos.relation_type_id = :relation_type_id " +
            "AND rtapos.part_of_speech_id = :part_of_speech_id")
    public RelationTypeAllowedPartOfSpeechEntity findById(Long relation_type_id, Long part_of_speech_id);
}
