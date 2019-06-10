package com.pwr.bzapps.plwordnetmobile.service.database.repository.sense;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.sense.SenseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SenseRepository extends CrudRepository<SenseEntity, Long> {

    @Query("SELECT s FROM SenseEntity s WHERE s.id=:id")
    public SenseEntity findSenseById(@Param("id") Long id);

    @Query("SELECT s FROM SenseEntity s WHERE LOWER(wordId.word) LIKE CONCAT('%',LOWER(:word),'%')")
    public List<SenseEntity> findByWord(@Param("word") String word);

    @Query("SELECT s FROM SenseEntity s WHERE LOWER(wordId.word) LIKE CONCAT('%',LOWER(:word),'%')" +
            "ORDER BY LENGTH(s.wordId.word), s.wordId.word, s.variant, s.lexiconId.id, s.partOfSpeechId.id")
    public List<SenseEntity> findByWord(@Param("word") String word, Pageable pageable);

    @Query("SELECT s FROM SenseEntity s WHERE synsetId.id = :id")
    public List<SenseEntity> findSynonymsBySynsetId(@Param("id") Long id);

    @Query("SELECT s FROM SenseEntity s WHERE id IN (:ids)")
    public List<SenseEntity> findMultipleByIds(@Param("ids") Long[] ids);

    @Query("SELECT s FROM SenseEntity s WHERE synsetId.id IN (:ids)")
    public List<SenseEntity> findMultipleBySynsetIds(@Param("ids") Long[] ids);

    @Query("SELECT s FROM SenseEntity s WHERE LOWER(wordId.word) = LOWER(:word)")
    public List<SenseEntity> findRelatedSensesByWord(@Param("word") String word);

    @Query("SELECT s FROM SenseEntity s WHERE LOWER(wordId.word) = LOWER(:word) " +
            "AND LOWER(lexiconId.languageName) LIKE LOWER(:language)")
    public List<SenseEntity> findRelatedSensesByWord(@Param("word") String word, @Param("language")String language);

    @Query("SELECT s FROM SenseEntity s WHERE LOWER(wordId.word) = LOWER(:word) " +
            "AND LOWER(lexiconId.languageName) LIKE LOWER(:language) " +
            "AND partOfSpeechId.id = :partOfSpeech")
    public List<SenseEntity> findRelatedSensesByWord(@Param("word") String word, @Param("language")String language,
                                                     @Param("partOfSpeech") Long partOfSpeech);

    @Query("SELECT s FROM SenseEntity s WHERE s.lexiconId.id IN (:lexiconIds)")
    public List<SenseEntity> findAllForLanguage(@Param("lexiconIds") Long[] lexiconIds);

    @Query("SELECT s.id FROM SenseEntity s WHERE s.lexiconId.id IN (:lexiconIds)")
    public List<Long> findIdsForLanguage(@Param("lexiconIds") Long[] lexiconIds);

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
            ")FROM sense s ", nativeQuery = true)
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
            ")FROM sense s WHERE s.lexicon_id  IN (:lexiconIds)", nativeQuery = true)
    public List<String> findAllForLexiconsAndParseString(@Param("lexiconIds") Long[] lexiconIds);

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
    public List<String> findAllAndParseStringBatch(@Param("begin") Long begin, @Param("end") Long end);
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
            ")FROM sense s WHERE s.lexicon_id  IN (:lexiconIds)" +
            " AND s.id>=:begin AND s.id<:end", nativeQuery = true)
    public List<String> findAllForLexiconsAndParseStringBatch(@Param("lexiconIds") Long[] lexiconIds,
                                                              @Param("begin") Long begin, @Param("end") Long end);

    @Query(value = "SELECT MAX(id) FROM sense", nativeQuery = true)
    public Long getMaxIndex();
}