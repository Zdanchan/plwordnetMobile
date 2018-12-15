package com.pwr.bzapps.plwordnetmobile.service.database.repository.sense;

import com.pwr.bzapps.plwordnetmobile.service.database.entity.sense.SenseRelationEntity;
import com.pwr.bzapps.plwordnetmobile.service.database.entity.synset.SynsetRelationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SenseRelationRepository extends CrudRepository<SenseRelationEntity, Integer> {
    @Query("SELECT sr FROM SenseRelationEntity sr WHERE sr.child_sense_id IN (:sense_ids) AND sr.parent_sense_id IN (:sense_ids)")
    public List<SenseRelationEntity> findMultipleBySenseId(@Param("sense_ids") Integer[] sense_ids);

    @Query("SELECT sr FROM SenseRelationEntity sr WHERE sr.relation_type_id IN (:relation_type_ids)")
    public List<SenseRelationEntity> findByRelationTypes(@Param("relation_type_ids") Integer[] relation_type_ids);

    @Query("SELECT sr FROM SenseRelationEntity sr WHERE sr.relation_type_id NOT IN (:relation_type_ids)")
    public List<SenseRelationEntity> findExcludingRelationTypes(@Param("relation_type_ids") Integer[] relation_type_ids);

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
            ")FROM sense_relation sr WHERE sr.child_sense_id IN (:sense_ids) AND sr.parent_sense_id IN (:sense_ids)", nativeQuery = true)
    public List<String> findAllForSensesAndParseString(@Param("sense_ids") Integer[] sense_ids);
}
