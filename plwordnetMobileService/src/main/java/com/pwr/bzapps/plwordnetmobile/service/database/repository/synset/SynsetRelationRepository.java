package com.pwr.bzapps.plwordnetmobile.service.database.repository.synset;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.synset.SynsetRelationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SynsetRelationRepository extends CrudRepository<SynsetRelationEntity, Integer> {
    @Query("SELECT sr FROM SynsetRelationEntity sr WHERE sr.child_synset_id IN (:synset_ids) AND sr.parent_synset_id IN (:synset_ids)")
    public List<SynsetRelationEntity> findMultipleBySynsetId(@Param("synset_ids") Integer[] synset_ids);

    @Query("SELECT sr FROM SynsetRelationEntity sr WHERE sr.synset_relation_type_id IN (:relation_type_ids)")
    public List<SynsetRelationEntity> findByRelationTypes(@Param("relation_type_ids") Integer[] relation_type_ids);

    @Query("SELECT sr FROM SynsetRelationEntity sr WHERE sr.synset_relation_type_id NOT IN (:relation_type_ids)")
    public List<SynsetRelationEntity> findExcludingRelationTypes(@Param("relation_type_ids") Integer[] relation_type_ids);
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
            "WHERE sec.lexicon_id IN (:lexicon_ids) OR sep.lexicon_id IN (:lexicon_ids)", nativeQuery = true)
    public List<String> findAllForSynsetsAndParseString(@Param("lexicon_ids") Integer[] lexicon_ids);
    @Query(value = "SELECT CONCAT(" +
            "sr.id,','," +
            "sr.child_synset_id,','," +
            "sr.parent_synset_id,','," +
            "sr.synset_relation_type_id" +
            ")FROM synset_relation sr" +
            " WHERE sr.id>=:begin AND sr.id<:end", nativeQuery = true)
    public List<String> findAllAndParseStringBatch(@Param("begin") Integer begin, @Param("end") Integer end);
    @Query(value = "SELECT CONCAT(" +
            "sr.id,','," +
            "sr.child_synset_id,','," +
            "sr.parent_synset_id,','," +
            "sr.synset_relation_type_id" +
            ")FROM synset_relation sr " +
            "JOIN synset sec ON sr.child_synset_id = sec.id " +
            "JOIN synset sep ON sr.parent_synset_id = sep.id " +
            "WHERE sr.id>=:begin AND sr.id<:end " +
            "AND (sec.lexicon_id IN (:lexicon_ids) OR sep.lexicon_id IN (:lexicon_ids))", nativeQuery = true)
    public List<String> findAllForSynsetsAndParseStringBatch(@Param("lexicon_ids") Integer[] lexicon_ids, @Param("begin") Integer begin, @Param("end") Integer end);

    @Query(value = "SELECT MAX(id) FROM synset_relation", nativeQuery = true)
    public Integer getMaxIndex();
}