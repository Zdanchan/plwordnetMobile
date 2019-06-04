package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.relation;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeAllowedPartOfSpeechEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeEntity;

import java.util.Collection;
import java.util.List;

@Dao
public interface RelationTypeDAO {

    @Query("SELECT * FROM relation_type AS rt LIMIT 1")
    public RelationTypeEntity checkTable();

    @Query("SELECT * FROM relation_type AS rt")
    public List<RelationTypeEntity> getAll();

    @Query("SELECT * FROM relation_type AS rt WHERE rt.id = :id")
    public RelationTypeEntity findById(Long id);
}
