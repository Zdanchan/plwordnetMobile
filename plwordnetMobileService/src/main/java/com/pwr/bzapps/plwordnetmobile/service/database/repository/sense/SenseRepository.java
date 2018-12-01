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

    @Query("SELECT s FROM SenseEntity s WHERE s.lexicon_id.id IN (:lexicon_ids)")
    public List<SenseEntity> findAllForLanguage(@Param("lexicon_ids") Integer[] lexicon_ids);

    @Query("SELECT s.id FROM SenseEntity s WHERE s.lexicon_id.id IN (:lexicon_ids)")
    public List<Integer> findIdsForLanguage(@Param("lexicon_ids") Integer[] lexicon_ids);

    @Query(value = "SELECT CONCAT(" +
            "s.id,','," +
            "s.synset_position,','," +
            "s.variant,','," +
            "s.domain_id.id,','," +
            "s.lexicon_id.id,','," +
            "s.part_of_speech_id.id,','," +
            "s.synset_id.id,','," +
            "s.word_id.id,','," +
            "s.status_id,','," +
            ")FROM SenseEntity s", nativeQuery = true)
    public List<String> findAllAndParseString();
    @Query(value = "SELECT CONCAT(" +
            "s.id,','," +
            "s.synset_position,','," +
            "s.variant,','," +
            "s.domain_id.id,','," +
            "s.lexicon_id.id,','," +
            "s.part_of_speech_id.id,','," +
            "s.synset_id.id,','," +
            "s.word_id.id,','," +
            "s.status_id,','," +
            ")FROM SenseEntity s WHERE s.lexicon_id.id  IN (:lexicon_ids)", nativeQuery = true)
    public List<String> findAllForLexiconsAndParseString(@Param("lexicon_ids") Integer[] lexicon_ids);
}