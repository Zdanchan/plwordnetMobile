package com.pwr.bzapps.plwordnetmobile.service.database.repository.synset;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.synset.SynsetRelationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SynsetRelationRepository extends CrudRepository<SynsetRelationEntity, Long> {
    @Query("SELECT sr FROM SynsetRelationEntity sr WHERE sr.childSynsetId IN (:synset_ids) AND sr.parentSynsetId IN (:synsetIds)")
    public List<SynsetRelationEntity> findMultipleBySynsetId(@Param("synsetIds") Long[] synsetIds);

    @Query("SELECT sr FROM SynsetRelationEntity sr WHERE sr.synsetRelationTypeId IN (:relationTypeIds)")
    public List<SynsetRelationEntity> findByRelationTypes(@Param("relationTypeIds") Long[] relationTypeIds);

    @Query("SELECT sr FROM SynsetRelationEntity sr WHERE sr.synsetRelationTypeId NOT IN (:relationTypeIds)")
    public List<SynsetRelationEntity> findExcludingRelationTypes(@Param("relationTypeIds") Long[] relationTypeIds);
    @Query(value = "SELECT CONCAT(" +
            "sr.id,','," +
            "sr.child_synset_id,','," +
            "sr.parent_synset_id,','," +
            "sr.synset_relation_type_id" +
            ")FROM synset_relation sr", nativeQuery = true)
    public List<String> findAllAndParseString();
    @Query(value = "SELECT CONCAT(" +
            "sr.id,','," +
            "sr.child_synset_id,','," +
            "sr.parent_synset_id,','," +
            "sr.synset_relation_type_id" +
            ")FROM synset_relation sr " +
            "JOIN synset sec ON sr.child_synset_id = sec.id " +
            "JOIN synset sep ON sr.parent_synset_id = sep.id " +
            "WHERE sec.lexicon_id IN (:lexicon_ids) OR sep.lexicon_id IN (:lexiconIds)", nativeQuery = true)
    public List<String> findAllForSynsetsAndParseString(@Param("lexiconIds") Long[] lexiconIds);
    @Query(value = "SELECT CONCAT(" +
            "sr.id,','," +
            "sr.child_synset_id,','," +
            "sr.parent_synset_id,','," +
            "sr.synset_relation_type_id" +
            ")FROM synset_relation sr" +
            " WHERE sr.id>=:begin AND sr.id<:end", nativeQuery = true)
    public List<String> findAllAndParseStringBatch(@Param("begin") Long begin, @Param("end") Long end);
    @Query(value = "SELECT CONCAT(" +
            "sr.id,','," +
            "sr.child_synset_id,','," +
            "sr.parent_synset_id,','," +
            "sr.synset_relation_type_id" +
            ")FROM synset_relation sr " +
            "JOIN synset sec ON sr.child_synset_id = sec.id " +
            "JOIN synset sep ON sr.parent_synset_id = sep.id " +
            "WHERE sr.id>=:begin AND sr.id<:end " +
            "AND (sec.lexicon_id IN (:lexicon_ids) OR sep.lexicon_id IN (:lexiconIds))", nativeQuery = true)
    public List<String> findAllForSynsetsAndParseStringBatch(@Param("lexiconIds") Long[] lexiconIds,
                                                             @Param("begin") Long begin, @Param("end") Long end);

    @Query(value = "SELECT MAX(id) FROM synset_relation", nativeQuery = true)
    public Long getMaxIndex();
}