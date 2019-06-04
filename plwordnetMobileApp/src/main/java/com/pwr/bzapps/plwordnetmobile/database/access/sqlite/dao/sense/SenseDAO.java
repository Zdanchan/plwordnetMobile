package com.pwr.bzapps.plwordnetmobile.database.access.sqlite.dao.sense;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RoomWarnings;
import android.arch.persistence.room.Transaction;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteConnector;
import com.pwr.bzapps.plwordnetmobile.database.access.sqlite.SQLiteTablesConstNames;
import com.pwr.bzapps.plwordnetmobile.database.entity.EntityManager;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseAttributeEntity;
import com.pwr.bzapps.plwordnetmobile.database.entity.sense.SenseEntity;
import com.pwr.bzapps.plwordnetmobile.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Dao
public interface SenseDAO {

    @Transaction
    @Query("SELECT * FROM sense AS s LIMIT 1")
    public SenseEntity checkTable();

    @Transaction
    @Query("SELECT * FROM sense AS s")
    public List<SenseEntity> getAll();

    @Transaction
    @Query("SELECT * FROM sense AS s WHERE s.id = :id")
    public SenseEntity findById(Long id);

    @Transaction
    @Query("SELECT * FROM sense AS s WHERE s.id IN (:ids)")
    public List<SenseEntity> findMultipleByIds(Long[] ids);

    @Transaction
    @Query("SELECT * FROM sense AS s WHERE s.synset_id = :id")
    public List<SenseEntity> findBySynsetId(Long id);

    @Transaction
    @Query("SELECT * FROM sense AS s WHERE s.synset_id IN (:ids)")
    public List<SenseEntity> findMultipleBySynsetIds(Long[] ids);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Transaction
    @Query("SELECT * FROM sense AS s " +
            "JOIN word AS w ON s.word_id = w.id " +
            "WHERE LOWER(w.word) LIKE LOWER('%' + :word + '%') " +
            "ORDER BY LENGTH(w.word), w.word, variant, part_of_speech_id, lexicon_id ASC")
    public List<SenseEntity> findByWord(String word);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Transaction
    @Query("SELECT * FROM sense AS s " +
            "JOIN word AS w ON s.word_id = w.id " +
            "WHERE LOWER(w.word) LIKE LOWER('%' + :word + '%') " +
            "ORDER BY LENGTH(w.word), w.word, variant, part_of_speech_id, lexicon_id ASC " +
            "LIMIT :resultLimit")
    public List<SenseEntity> findByWord(String word, int resultLimit);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Transaction
    @Query("SELECT * FROM sense AS s " +
            "JOIN word AS w ON s.word_id = w.id " +
            "WHERE w.word = :word")
    public List<SenseEntity> findRelatedForWord(String word);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Transaction
    @Query("SELECT * FROM sense AS s " +
            "JOIN word AS w ON s.word_id = w.id " +
            "JOIN lexicon AS l ON l.id = s.lexicon_id " +
            "WHERE w.word = :word " +
            "AND LOWER(l.language_name) LIKE LOWER(:language)")
    public List<SenseEntity> findRelatedForWordAndLanguage(String word, String language);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Transaction
    @Query("SELECT * FROM sense AS s " +
            "JOIN word AS w ON s.word_id = w.id " +
            "JOIN lexicon AS l ON l.id = s.lexicon_id " +
            "WHERE w.word = :word " +
            "AND LOWER(l.language_name) LIKE LOWER(:language) " +
            "AND s.part_of_speech_id = :part_of_speech")
    public List<SenseEntity> findRelatedForWordLanguageAndPartOfSpeech(String word,
                                                                             String language,
                                                                       Long part_of_speech);
}
