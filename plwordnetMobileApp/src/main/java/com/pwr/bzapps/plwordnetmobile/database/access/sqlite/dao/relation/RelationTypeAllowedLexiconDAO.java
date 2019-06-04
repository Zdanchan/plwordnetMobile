package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.relation;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.grammar.WordEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.relation.RelationTypeAllowedLexiconEntity;

import java.util.Collection;
import java.util.List;

@Dao
public interface RelationTypeAllowedLexiconDAO {

    @Query("SELECT * FROM relation_type_allowed_lexicons AS rtal LIMIT 1")
    public RelationTypeAllowedLexiconEntity checkTable();

    @Query("SELECT * FROM relation_type_allowed_lexicons AS rtal")
    public List<RelationTypeAllowedLexiconEntity> getAll();

    @Query("SELECT * FROM relation_type_allowed_lexicons AS rtal " +
            "WHERE rtal.relation_type_id = :relation_type_id " +
            "AND rtal.lexicon_id = :lexicon_id")
    public RelationTypeAllowedLexiconEntity findById(Long relation_type_id, Long lexicon_id);
}
