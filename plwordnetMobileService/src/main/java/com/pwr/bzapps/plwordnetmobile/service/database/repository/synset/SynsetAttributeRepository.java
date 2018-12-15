package com.pwr.bzapps.plwordnetmobile.service.database.repository.synset;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.synset.SynsetAttributeEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *   `synset_id` bigint(20) NOT NULL,
 *   `comment` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `definition` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `princeton_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL COMMENT 'External original Princeton Id',
 *   `owner_id` bigint(20) DEFAULT NULL COMMENT 'Synset owner',
 *   `error_comment` text CHARACTER SET utf8 COLLATE utf8_polish_ci,
 *   `ili_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_polish_ci DEFAULT NULL COMMENT 'OMW id',
 * */

public interface SynsetAttributeRepository extends CrudRepository<SynsetAttributeEntity, Integer> {
    @Query("SELECT sa FROM SynsetAttributeEntity sa WHERE sa.synset_id IN (:synset_ids)")
    public List<SynsetAttributeEntity> findMultipleBySynsetId(@Param("synset_ids") Integer[] synset_ids);
    @Query("SELECT sa.id FROM SynsetAttributeEntity sa WHERE sa.synset_id IN (:synset_ids)")
    public List<Integer> findIdsMultipleBySynsetId(@Param("synset_ids") Integer[] synset_ids);

    @Query(value = "SELECT CONCAT(" +
            "sa.synset_id,','," +
            "IF(sa.comment IS NULL, 'null','\"',sa.comment,'\"'),','," +
            "IF(sa.definition IS NULL, 'null','\"',sa.definition,'\"'),','," +
            "IF(sa.princeton_id IS NULL, 'null','\"',sa.princeton_id,'\"'),','," +
            "IF(sa.owner_id IS NULL, 'null',sa.owner_id),','," +
            "IF(sa.error_comment IS NULL, 'null','\"',sa.error_comment,'\"'),','," +
            "IF(sa.ili_id IS NULL, 'null',sa.ili_id)" +
            ")FROM synset_attributes sa", nativeQuery = true)
    public List<String> findAllAndParseString();
    @Query(value = "SELECT CONCAT(" +
            "IF(sa.comment IS NULL, 'null','\"',sa.comment,'\"'),','," +
            "IF(sa.definition IS NULL, 'null','\"',sa.definition,'\"'),','," +
            "IF(sa.princeton_id IS NULL, 'null','\"',sa.princeton_id,'\"'),','," +
            "IF(sa.owner_id IS NULL, 'null',sa.owner_id),','," +
            "IF(sa.error_comment IS NULL, 'null','\"',sa.error_comment,'\"'),','," +
            "IF(sa.ili_id IS NULL, 'null',sa.ili_id)" +
            ")FROM synset_attributes sa WHERE sa.sense_id IN (:synset_ids)", nativeQuery = true)
    public List<String> findAllForSynsetsAndParseString(@Param("synset_ids") Integer[] synset_ids);
}