package com.pwr.bzapps.plwordnetmobile.service.database.repository.sense;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.sense.SenseRelationEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.synset.SynsetRelationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SenseRelationRepository extends CrudRepository<SenseRelationEntity, Long> {
    @Query("SELECT sr FROM SenseRelationEntity sr WHERE sr.childSenseId IN (:sense_ids) AND sr.parentSenseId IN (:sense_ids)")
    public List<SenseRelationEntity> findMultipleBySenseId(@Param("sense_ids") Long[] sense_ids);

    @Query("SELECT sr FROM SenseRelationEntity sr WHERE sr.relationTypeId IN (:relation_type_ids)")
    public List<SenseRelationEntity> findByRelationTypes(@Param("relation_type_ids") Long[] relation_type_ids);

    @Query("SELECT sr FROM SenseRelationEntity sr WHERE sr.relationTypeId NOT IN (:relation_type_ids)")
    public List<SenseRelationEntity> findExcludingRelationTypes(@Param("relation_type_ids") Long[] relation_type_ids);

    @Query(value = "SELECT CONCAT(" +
            "sr.id,','," +
            "sr.child_sense_id,','," +
            "sr.parent_sense_id,','," +
            "sr.relation_type_id" +
            ")FROM sense_relation sr", nativeQuery = true)
    public List<String> findAllAndParseString();
    @Query(value = "SELECT CONCAT(" +
            "sr.id,','," +
            "sr.child_sense_id,','," +
            "sr.parent_sense_id,','," +
            "sr.relation_type_id" +
            ")FROM sense_relation sr " +
            "JOIN sense sec ON sr.child_sense_id = sec.id " +
            "JOIN sense sep ON sr.parent_sense_id = sep.id " +
            "WHERE sec.lexicon_id IN (:lexicon_ids) OR sep.lexicon_id IN (:lexicon_ids)", nativeQuery = true)
    public List<String> findAllForSensesAndParseString(@Param("lexicon_ids") Long[] lexicon_ids);

    @Query(value = "SELECT CONCAT(" +
            "sr.id,','," +
            "sr.child_sense_id,','," +
            "sr.parent_sense_id,','," +
            "sr.relation_type_id" +
            ")FROM sense_relation sr" +
            " WHERE sr.id>=:begin AND sr.id<:end", nativeQuery = true)
    public List<String> findAllAndParseStringBatch(@Param("begin") Long begin, @Param("end") Long end);
    @Query(value = "SELECT CONCAT(" +
            "sr.id,','," +
            "sr.child_sense_id,','," +
            "sr.parent_sense_id,','," +
            "sr.relation_type_id" +
            ")FROM sense_relation sr " +
            "JOIN sense sec ON sr.child_sense_id = sec.id " +
            "JOIN sense sep ON sr.parent_sense_id = sep.id " +
            "WHERE sr.id>=:begin AND sr.id<:end " +
            "AND (sec.lexicon_id IN (:lexicon_ids) OR sep.lexicon_id IN (:lexicon_ids))", nativeQuery = true)
    public List<String> findAllForSensesAndParseStringBatch(@Param("lexicon_ids") Long[] lexicon_ids,
                                                            @Param("begin") Long begin, @Param("end") Long end);

    @Query(value = "SELECT MAX(id) FROM sense_relation", nativeQuery = true)
    public Long getMaxIndex();
}
