package com.pwr.bzapps.plwordnetmobile.service.database.repository.synset;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.synset.SynsetEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *   `id` bigint(20) NOT NULL AUTO_INCREMENT,
 *   `split` int(11) DEFAULT NULL COMMENT 'Position of line splitting synset head',
 *   `lexicon_id` bigint(20) NOT NULL,
 *   `status_id` bigint(20) DEFAULT NULL,
 *   `abstract` tinyint(1) DEFAULT NULL COMMENT 'is synset abstract',
 * */

public interface SynsetRepository extends CrudRepository<SynsetEntity, Integer> {

    @Query("SELECT s FROM SynsetEntity s WHERE id IN (:ids)")
    public List<SynsetEntity> findMultipleByIds(@Param("ids") Integer[] ids);

    @Query("SELECT s FROM SynsetEntity s WHERE s.lexicon_id.id IN (:lexicon_ids)")
    public List<SynsetEntity> findAllForLanguage(@Param("lexicon_ids") Integer[] lexicon_ids);

    @Query("SELECT s.id FROM SynsetEntity s WHERE s.lexicon_id.id IN (:lexicon_ids)")
    public List<Integer> findIdsForLanguage(@Param("lexicon_ids") Integer[] lexicon_ids);

    @Query(value = "SELECT CONCAT(" +
            "s.id,','," +
            "IF(s.split IS NULL, 'null', s.split),','," +
            "s.lexicon_id,','," +
            "IF(s.status_id IS NULL, 'null', s.status_id),','," +
            "IF(s.abstract IS NULL, 'null', s.abstract)" +
            ")FROM synset s", nativeQuery = true)
    public List<String> findAllAndParseString();
    @Query(value = "SELECT CONCAT(" +
            "s.id,','," +
            "IF(s.split IS NULL, 'null', s.split),','," +
            "s.lexicon_id,','," +
            "IF(s.status_id IS NULL, 'null', s.status_id),','," +
            "IF(s.abstract IS NULL, 'null', s.abstract)" +
            ")FROM synset s WHERE s.lexicon_id  IN (:lexicon_ids)", nativeQuery = true)
    public List<String> findAllForLexiconsAndParseString(@Param("lexicon_ids") Integer[] lexicon_ids);
}