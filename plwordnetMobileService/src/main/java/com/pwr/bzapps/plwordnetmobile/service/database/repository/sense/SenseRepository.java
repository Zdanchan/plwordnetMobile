package com.pwr.bzapps.plwordnetmobile.service.database.repository.sense;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.sense.SenseEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SenseRepository extends CrudRepository<SenseEntity, Integer> {

    @Query("SELECT s FROM SenseEntity s WHERE LOWER(word_id.word) LIKE CONCAT('%',LOWER(:word),'%')")
    public List<SenseEntity> findByWord(@Param("word") String word);

    @Query("SELECT s FROM SenseEntity s WHERE synset_id.id = :id")
    public List<SenseEntity> findSynonymsBySynsetId(@Param("id") Integer id);

    @Query("SELECT s FROM SenseEntity s WHERE id IN (:ids)")
    public List<SenseEntity> findMultipleByIds(@Param("ids") Integer[] ids);

    @Query("SELECT s FROM SenseEntity s WHERE synset_id.id IN (:ids)")
    public List<SenseEntity> findMultipleBySynsetIds(@Param("ids") Integer[] ids);

    @Query("SELECT s FROM SenseEntity s WHERE LOWER(word_id.word) = LOWER(:word)")
    public List<SenseEntity> findRelatedSensesByWord(@Param("word") String word);

    @Query("SELECT s FROM SenseEntity s WHERE LOWER(word_id.word) = LOWER(:word) " +
            "AND LOWER(lexicon_id.language_name) = LOWER(:language)")
    public List<SenseEntity> findRelatedSensesByWord(@Param("word") String word, @Param("language")String language);

    @Query("SELECT s FROM SenseEntity s WHERE s.lexicon_id.id IN (:lexicon_ids)")
    public List<SenseEntity> findAllForLanguage(@Param("lexicon_ids") Integer[] lexicon_ids);

    @Query("SELECT s.id FROM SenseEntity s WHERE s.lexicon_id.id IN (:lexicon_ids)")
    public List<Integer> findIdsForLanguage(@Param("lexicon_ids") Integer[] lexicon_ids);

    @Query(value = "SELECT CONCAT(" +
            "s.id,','," +
            "IF(s.synset_position IS NULL,'null',s.synset_position),','," +
            "s.variant,','," +
            "s.domain_id,','," +
            "s.lexicon_id,','," +
            "s.part_of_speech_id,','," +
            "IF(s.synset_id IS NULL,'null',s.synset_id),','," +
            "s.word_id,','," +
            "IF(s.status_id IS NULL,'null',s.status_id)" +
            ")FROM sense s", nativeQuery = true)
    public List<String> findAllAndParseString();
    @Query(value = "SELECT CONCAT(" +
            "s.id,','," +
            "IF(s.synset_position IS NULL,'null',s.synset_position),','," +
            "s.variant,','," +
            "s.domain_id,','," +
            "s.lexicon_id,','," +
            "s.part_of_speech_id,','," +
            "IF(s.synset_id IS NULL,'null',s.synset_id),','," +
            "s.word_id,','," +
            "IF(s.status_id IS NULL,'null',s.status_id)" +
            ")FROM sense s WHERE s.lexicon_id  IN (:lexicon_ids)", nativeQuery = true)
    public List<String> findAllForLexiconsAndParseString(@Param("lexicon_ids") Integer[] lexicon_ids);

    @Query(value = "SELECT CONCAT(" +
            "s.id,','," +
            "IF(s.synset_position IS NULL,'null',s.synset_position),','," +
            "s.variant,','," +
            "s.domain_id,','," +
            "s.lexicon_id,','," +
            "s.part_of_speech_id,','," +
            "IF(s.synset_id IS NULL,'null',s.synset_id),','," +
            "s.word_id,','," +
            "IF(s.status_id IS NULL,'null',s.status_id)" +
            ")FROM sense s" +
            " WHERE s.id>=:begin AND s.id<:end", nativeQuery = true)
    public List<String> findAllAndParseStringBatch(@Param("begin") Integer begin, @Param("end") Integer end);
    @Query(value = "SELECT CONCAT(" +
            "s.id,','," +
            "IF(s.synset_position IS NULL,'null',s.synset_position),','," +
            "s.variant,','," +
            "s.domain_id,','," +
            "s.lexicon_id,','," +
            "s.part_of_speech_id,','," +
            "IF(s.synset_id IS NULL,'null',s.synset_id),','," +
            "s.word_id,','," +
            "IF(s.status_id IS NULL,'null',s.status_id)" +
            ")FROM sense s WHERE s.lexicon_id  IN (:lexicon_ids)" +
            " AND s.id>=:begin AND s.id<:end", nativeQuery = true)
    public List<String> findAllForLexiconsAndParseStringBatch(@Param("lexicon_ids") Integer[] lexicon_ids, @Param("begin") Integer begin, @Param("end") Integer end);
    @Query(value = "SELECT MAX(id) FROM sense", nativeQuery = true)
    public Integer getMaxIndex();
}